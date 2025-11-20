package DB_Objects;

import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.Types;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBManager {

    private static final String DB_RELATIVE_PATH = "web/db/CarDB.accdb";
    private static final String DRIVER_CLASS = "net.ucanaccess.jdbc.UcanaccessDriver";

    private String url;

    public DBManager() {
        try {
            Class.forName(DRIVER_CLASS);
            File dbFile = new File(DB_RELATIVE_PATH);
            String dbPath = dbFile.getAbsolutePath();
            url = "jdbc:ucanaccess://" + dbPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public DBManager(ServletContext context) {
        try {
            Class.forName(DRIVER_CLASS);
//            File dbFile = new File(DB_RELATIVE_PATH);
//            String dbPath = dbFile.getAbsolutePath();
            String dbPath = context.getRealPath("/db/CarDB.accdb");
            System.out.println("Resolved DB path: " + dbPath);

            url = "jdbc:ucanaccess://" + dbPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
}
    
    public String[] selectFromDB(String table, String field, String value, String fieldType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);

            String sql = "SELECT * FROM " + table + " WHERE " + field + " = ?";
            pstmt = conn.prepareStatement(sql);

            // Set value based on fieldType
        switch (fieldType.toLowerCase()) {
            case "int":
            case "integer":
                pstmt.setInt(1, Integer.parseInt(value));
                break;

            case "long":
            case "long number": // new case
                pstmt.setLong(1, Long.parseLong(value));
                break;

            case "double":
            case "float":
            case "currency":
                pstmt.setDouble(1, Double.parseDouble(value));
                break;

            case "boolean":
                pstmt.setBoolean(1, Boolean.parseBoolean(value));
                break;

            case "yes/no": // new case
                // Interpret "Yes" or "No" as boolean true/false
                boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                pstmt.setBoolean(1, boolValue);
                break;

            case "date":
                pstmt.setDate(1, java.sql.Date.valueOf(value)); // Format: yyyy-MM-dd
                break;

            default:
                pstmt.setString(1, value);
                break;
        }

            rs = pstmt.executeQuery();

            if (rs.next()) {
                ResultSetMetaData meta = rs.getMetaData();
                int columnCount = meta.getColumnCount();
                String[] resultRow = new String[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    resultRow[i - 1] = rs.getString(i);
                }

                return resultRow;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String[] selectLastFromDB(String table) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);

            // Step 1: Get the first column name (usually AutoNumber / primary key)
            String metaSql = "SELECT * FROM " + table;
            pstmt = conn.prepareStatement(metaSql);
            rs = pstmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();

            if (meta.getColumnCount() == 0) {
                return null; // Table has no columns
            }

            String keyColumn = meta.getColumnName(1); // assume first column is key
            rs.close();
            pstmt.close();

            // Step 2: Select the last record based on that column
            String sql = "SELECT TOP 1 * FROM " + table + " ORDER BY " + keyColumn + " DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // Step 3: Return the last record as String[]
            if (rs.next()) {
                int columnCount = meta.getColumnCount();
                String[] resultRow = new String[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    resultRow[i - 1] = rs.getString(i);
                }

                return resultRow;
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    
    public String[][] selectAllFromDB(String table, String field, String value, String fieldType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);

            String sql = "SELECT * FROM " + table + " WHERE " + field + " = ?";
            pstmt = conn.prepareStatement(sql);

            // Set value based on fieldType
            switch (fieldType.toLowerCase()) {
                case "int":
                case "integer":
                    pstmt.setInt(1, Integer.parseInt(value));
                    break;

                case "long":
                case "long number": // new case
                    pstmt.setLong(1, Long.parseLong(value));
                    break;

                case "double":
                case "float":
                case "currency":
                    pstmt.setDouble(1, Double.parseDouble(value));
                    break;

                case "boolean":
                    pstmt.setBoolean(1, Boolean.parseBoolean(value));
                    break;

                case "yes/no": // new case
                    // Interpret "Yes" or "No" as boolean true/false
                    boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                    pstmt.setBoolean(1, boolValue);
                    break;

                case "date":
                    pstmt.setDate(1, java.sql.Date.valueOf(value)); // Format: yyyy-MM-dd
                    break;

                default:
                    pstmt.setString(1, value);
                    break;
            }

            rs = pstmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Collect all rows
            java.util.List<String[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                rows.add(row);
            }

            // Convert List to String[][]
            return rows.toArray(new String[0][]);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String[][] searchDB(String table, String searchTerm) {
        List<String[]> results = new ArrayList<>();

        // Protect against SQL injection for table name (must be alphanumeric or underscore)
        if (!table.matches("[A-Za-z0-9_]+")) {
            System.out.println("Invalid table name.");
            return new String[0][0];
        }

        try (Connection conn = DriverManager.getConnection(url);
             // Query column names first
             PreparedStatement colStmt = conn.prepareStatement(
                "SELECT * FROM " + table + " WHERE 1=0"
             );
             ResultSet colRs = colStmt.executeQuery()) {

            // Get metadata to iterate all columns
            ResultSetMetaData meta = colRs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Build dynamic search query:
            // SELECT * FROM table WHERE col1 LIKE ? OR col2 LIKE ? ...
            StringBuilder query = new StringBuilder("SELECT * FROM " + table + " WHERE ");
            for (int i = 1; i <= columnCount; i++) {
                query.append(meta.getColumnName(i)).append(" LIKE ?");
                if (i < columnCount) query.append(" OR ");
            }

            try (PreparedStatement stmt = conn.prepareStatement(query.toString())) {
                // Each column gets the same search term
                for (int i = 1; i <= columnCount; i++) {
                    stmt.setString(i, "%" + searchTerm + "%");
                }

                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getString(i);
                    }
                    results.add(row);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return results.toArray(new String[0][0]);
    }



    

    public void addRowFromArray(String table, String[] fields, String[] rowArray, String[] fieldTypes) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            if (fields.length != rowArray.length || fields.length != fieldTypes.length) {
                throw new IllegalArgumentException("Fields, rowArray, and fieldTypes must have the same length.");
            }

            conn = DriverManager.getConnection(url);

            // Build field list and placeholders
            StringBuilder fieldList = new StringBuilder();
            StringBuilder placeholders = new StringBuilder();

            for (int i = 0; i < fields.length; i++) {
                fieldList.append(fields[i]);
                placeholders.append("?");
                if (i < fields.length - 1) {
                    fieldList.append(", ");
                    placeholders.append(", ");
                }
            }

            String sql = "INSERT INTO " + table + " (" + fieldList + ") VALUES (" + placeholders + ")";
            pstmt = conn.prepareStatement(sql);

            // Set values based on fieldTypes
            for (int i = 0; i < rowArray.length; i++) {
                String value = rowArray[i];
                String type = fieldTypes[i].toLowerCase();

                switch (type) {
                    case "int":
            case "integer":
                pstmt.setInt(1, Integer.parseInt(value));
                break;

            case "long":
            case "long number": // new case
                pstmt.setLong(1, Long.parseLong(value));
                break;

            case "double":
            case "float":
            case "currency":
                pstmt.setDouble(1, Double.parseDouble(value));
                break;

            case "boolean":
                pstmt.setBoolean(1, Boolean.parseBoolean(value));
                break;

            case "yes/no": // new case
                // Interpret "Yes" or "No" as boolean true/false
                boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                pstmt.setBoolean(1, boolValue);
                break;

            case "date":
                pstmt.setDate(1, java.sql.Date.valueOf(value)); // Format: yyyy-MM-dd
                break;

            default:
                pstmt.setString(1, value);
                break;
                }
            }

            int rowsInserted = pstmt.executeUpdate();
            System.out.println("Inserted " + rowsInserted + " row(s) into " + table);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
//    public void addItemToTable(String table, String[] fields, String[] values, String primaryKeyField) {
//        if (fields == null || values == null || fields.length != values.length) {
//            System.out.println("Invalid input: fields and values must be non-null and same length.");
//            return;
//        }
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//
//        try {
//            conn = DriverManager.getConnection(url);
//
//            // 1️⃣ Get the last PK value
//            int nextPK = 1; // default if table is empty
//            if (primaryKeyField != null && !primaryKeyField.isEmpty()) {
//                String lastPKSql = "SELECT MAX(" + primaryKeyField + ") FROM " + table;
//                try (PreparedStatement pkStmt = conn.prepareStatement(lastPKSql);
//                     ResultSet rs = pkStmt.executeQuery()) {
//                    if (rs.next()) {
//                        nextPK = rs.getInt(1) + 1;
//                    }
//                }
//            }
//
//            // 2️⃣ Build SQL
//            StringBuilder fieldList = new StringBuilder();
//            StringBuilder placeholders = new StringBuilder();
//            for (int i = 0; i < fields.length; i++) {
//                fieldList.append(fields[i]);
//                placeholders.append("?");
//                if (i < fields.length - 1) {
//                    fieldList.append(", ");
//                    placeholders.append(", ");
//                }
//            }
//
//            String sql = "INSERT INTO " + table + " (" + primaryKeyField + ", " + fieldList + ") VALUES (?, " + placeholders + ")";
//            pstmt = conn.prepareStatement(sql);
//
//            // 3️⃣ Bind parameters
//            pstmt.setInt(1, nextPK); // primary key first
//            for (int i = 0; i < values.length; i++) {
//                String value = values[i];
//
//                // Try to parse numeric values, else treat as string
//                if (value.matches("\\d+")) {
//                    pstmt.setInt(i + 2, Integer.parseInt(value)); // +2 because 1 is PK
//                } else {
//                    pstmt.setString(i + 2, value);
//                }
//            }
//
//            // 4️⃣ Execute
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("Record inserted successfully into " + table + ". Next PK: " + nextPK);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (pstmt != null) pstmt.close();
//                if (conn != null) conn.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
    
    public void addItemToTable(String table, String[] fields, String[] values, String primaryKeyField) {
    if (fields == null || values == null || fields.length != values.length) {
        System.out.println("Invalid input: fields and values must be non-null and same length.");
        return;
    }

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false); // start transaction

        int nextPK = 1; // default if table is empty
        if (primaryKeyField != null && !primaryKeyField.isEmpty()) {
            String lastPKSql = "SELECT MAX(" + primaryKeyField + ") FROM " + table;
            try (PreparedStatement pkStmt = conn.prepareStatement(lastPKSql);
                 ResultSet rs = pkStmt.executeQuery()) {
                if (rs.next()) {
                    nextPK = rs.getInt(1) + 1;
                }
            }
        }

        // Build SQL
        StringBuilder fieldList = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            fieldList.append(fields[i]);
            placeholders.append("?");
            if (i < fields.length - 1) {
                fieldList.append(", ");
                placeholders.append(", ");
            }
        }

        String sql = "INSERT INTO " + table + " (" + primaryKeyField + ", " + fieldList + ") VALUES (?, " + placeholders + ")";

        boolean inserted = false;
        int attempts = 0;
        while (!inserted && attempts < 5) {
            try (PreparedStatement retryPstmt = conn.prepareStatement(sql)) {
                retryPstmt.setInt(1, nextPK); // primary key first
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    if (value.matches("\\d+")) {
                        retryPstmt.setInt(i + 2, Integer.parseInt(value));
                    } else {
                        retryPstmt.setString(i + 2, value);
                    }
                }

                System.out.println("Attempting INSERT into " + table + " with PK=" + nextPK + " Values: " + Arrays.toString(values));
                retryPstmt.executeUpdate();
                inserted = true;
                conn.commit();
                System.out.println("Record inserted successfully with PK=" + nextPK);
            } catch (SQLException e) {
                if (e.getMessage().contains("unique constraint") || e.getMessage().contains("SYS_PK")) {
                    // PK collision: increment and retry
                    nextPK++;
                    attempts++;
                    System.out.println("PK collision detected. Retrying with next PK=" + nextPK);
                } else {
                    throw e;
                }
            }
        }

        if (!inserted) {
            throw new SQLException("Failed to insert record after " + attempts + " attempts due to PK collisions.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        try { if (conn != null) conn.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.setAutoCommit(true);
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


    
//    public void addItemToCarts(String[] cartRow) {
//        if (cartRow == null || cartRow.length != 6) {
//            System.out.println("Invalid cartRow input. Expected 6 elements.");
//            return;
//        }
//
//        // Extract the fields
//        String userID = cartRow[0];
//        String prodID = cartRow[1];
//        String prodName = cartRow[2];
//        String price = cartRow[3];
//        String category = cartRow[4];
//        String quantity = cartRow[5];
//
//        // Generate CartItemID by concatenating UserID and ProdID
//        String cartItemID = userID + prodID;
//
//        String sql = "INSERT INTO CARTS (CartItemID, UserID, ProdID, ProdName, Price, Category, Quantity) "
//                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = DriverManager.getConnection(url);
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setInt(1, Integer.parseInt(cartItemID));
//            pstmt.setInt(2, Integer.parseInt(userID));
//            pstmt.setInt(3, Integer.parseInt(prodID));
//            pstmt.setString(4, prodName);
//            pstmt.setBigDecimal(5, new java.math.BigDecimal(price));
//            pstmt.setString(6, category);
//            pstmt.setInt(7, Integer.parseInt(quantity));
//            String debugSQL = sql
//                .replaceFirst("\\?", cartItemID)
//                .replaceFirst("\\?", userID)
//                .replaceFirst("\\?", prodID)
//                .replaceFirst("\\?", "'" + prodName + "'")
//                .replaceFirst("\\?", price)
//                .replaceFirst("\\?", "'" + category + "'")
//                .replaceFirst("\\?", quantity);
//        this.printAllRowsOrdered("Carts");
//        System.out.println("ATTEMPTING TO EXECUTE SQL:");
//        System.out.println(debugSQL);
//
//            int rowsInserted = pstmt.executeUpdate();
//            if (rowsInserted > 0) {
//                System.out.println("Item added successfully to CARTS.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    
    public void addNewItemFromArray(String table, String[] fields, String[] rowArray, String[] fieldTypes) {
    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        if (fields.length != rowArray.length || fields.length != fieldTypes.length) {
            throw new IllegalArgumentException("Fields, rowArray, and fieldTypes must have the same length (excluding PK).");
        }

        conn = DriverManager.getConnection(url);

        // Detect AutoNumber PK dynamically
        java.sql.DatabaseMetaData meta = conn.getMetaData();
        ResultSet pkRS = meta.getPrimaryKeys(null, null, table);
        String pkField = null;
        boolean skipPK = false;
        if (pkRS.next()) {
            pkField = pkRS.getString("COLUMN_NAME");
            // Check if it’s AutoNumber
            ResultSet colRS = meta.getColumns(null, null, table, pkField);
            if (colRS.next()) {
                String typeName = colRS.getString("TYPE_NAME").toLowerCase();
                if (typeName.contains("counter") || typeName.contains("autonumber")) {
                    skipPK = true;
                }
            }
            colRS.close();
        }
        pkRS.close();

        // Build SQL
        StringBuilder fieldList = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            fieldList.append(fields[i]);
            placeholders.append("?");
            if (i < fields.length - 1) {
                fieldList.append(", ");
                placeholders.append(", ");
            }
        }

        String sql = "INSERT INTO " + table + " (" + fieldList + ") VALUES (" + placeholders + ")";
        pstmt = conn.prepareStatement(sql);

        // Bind parameters based on type
        for (int i = 0; i < rowArray.length; i++) {
            String value = rowArray[i];
            String type = fieldTypes[i].toLowerCase();
            int paramIndex = i + 1;

            switch (type) {
                case "int":
                case "integer":
                    pstmt.setInt(paramIndex, Integer.parseInt(value));
                    break;
                case "long":
                case "long number":
                    pstmt.setLong(paramIndex, Long.parseLong(value));
                    break;
                case "double":
                case "float":
                case "currency":
                    pstmt.setDouble(paramIndex, Double.parseDouble(value));
                    break;
                case "boolean":
                case "yes/no":
                    boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                    pstmt.setBoolean(paramIndex, boolValue);
                    break;
                case "date":
                    pstmt.setDate(paramIndex, java.sql.Date.valueOf(value));
                    break;
                default:
                    pstmt.setString(paramIndex, value);
                    break;
            }
        }

        // Execute insert
        pstmt.executeUpdate();
        System.out.println("Record added to " + table + " successfully.");

    } catch (Exception e) {
        System.err.println("Failed to insert into table " + table + ": " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}


    
    public void deleteFirstRowFromDB(String table, String column, String value, String dataType) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(url);

            // MS Access supports DELETE TOP 1
            String sql = "DELETE FROM " + table + " WHERE " + column + " = ?";

            // We can’t directly use DELETE TOP 1 in Access without subquery for some versions.
            // Safer approach: find the primary key of the first matching row, then delete by PK.
            String primaryKeyField = null;
            java.sql.DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet rs = dbMeta.getPrimaryKeys(null, null, table);
            if (rs.next()) {
                primaryKeyField = rs.getString("COLUMN_NAME");
            }
            rs.close();

            if (primaryKeyField != null) {
                // First get PK of first matching row
                String selectSql = "SELECT " + primaryKeyField + " FROM " + table + " WHERE " + column + " = ? ORDER BY " + primaryKeyField;
                pstmt = conn.prepareStatement(selectSql);
                switch (dataType.toLowerCase()) {
                                        case "int":
                    case "integer":
                        pstmt.setInt(1, Integer.parseInt(value));
                        break;

                    case "long":
                    case "long number": // new case
                        pstmt.setLong(1, Long.parseLong(value));
                        break;

                    case "double":
                    case "float":
                    case "currency":
                        pstmt.setDouble(1, Double.parseDouble(value));
                        break;

                    case "boolean":
                        pstmt.setBoolean(1, Boolean.parseBoolean(value));
                        break;

                    case "yes/no": // new case
                        // Interpret "Yes" or "No" as boolean true/false
                        boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                        pstmt.setBoolean(1, boolValue);
                    break;

                    case "date":
                        pstmt.setDate(1, java.sql.Date.valueOf(value)); // Format: yyyy-MM-dd
                        break;

                    default:
                        pstmt.setString(1, value);
                        break;
                }
                rs = pstmt.executeQuery();
                Integer pkValue = null;
                if (rs.next()) {
                    pkValue = rs.getInt(1);
                }
                rs.close();
                pstmt.close();

                if (pkValue != null) {
                    // Now delete using PK to ensure only one row
                    sql = "DELETE FROM " + table + " WHERE " + primaryKeyField + " = ?";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, pkValue);
                    int rowsDeleted = pstmt.executeUpdate();
                    System.out.println("Deleted " + rowsDeleted + " row(s) from " + table + " (PK=" + pkValue + ")");
                } else {
                    System.out.println("No matching row found to delete.");
                }

            } else {
                // If no PK detected, fallback to DELETE TOP 1
                sql = "DELETE FROM " + table + " WHERE " + column + " = ?";
                pstmt = conn.prepareStatement(sql);

                switch (dataType.toLowerCase()) {
                                        case "int":
                    case "integer":
                        pstmt.setInt(1, Integer.parseInt(value));
                        break;

                    case "long":
                    case "long number": // new case
                        pstmt.setLong(1, Long.parseLong(value));
                        break;

                    case "double":
                    case "float":
                    case "currency":
                        pstmt.setDouble(1, Double.parseDouble(value));
                        break;

                    case "boolean":
                        pstmt.setBoolean(1, Boolean.parseBoolean(value));
                        break;

                    case "yes/no": // new case
                        // Interpret "Yes" or "No" as boolean true/false
                        boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                        pstmt.setBoolean(1, boolValue);
                    break;

                    case "date":
                        pstmt.setDate(1, java.sql.Date.valueOf(value)); // Format: yyyy-MM-dd
                        break;

                    default:
                        pstmt.setString(1, value);
                        break;
                }

                int rowsDeleted = pstmt.executeUpdate();
                System.out.println("Deleted " + rowsDeleted + " row(s) from " + table);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean deleteLastFromDB(String table) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);

            // Step 1: Get the first column name (usually the key)
            String getColSql = "SELECT * FROM " + table;
            pstmt = conn.prepareStatement(getColSql);
            rs = pstmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();

            if (meta.getColumnCount() == 0) {
                return false; // table empty or invalid
            }

            String keyColumn = meta.getColumnName(1); // assume first column is ID-like
            rs.close();
            pstmt.close();

            // Step 2: Find the last record by ordering descending
            String findLastSql = "SELECT TOP 1 " + keyColumn + " FROM " + table + " ORDER BY " + keyColumn + " DESC";
            pstmt = conn.prepareStatement(findLastSql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String lastKeyValue = rs.getString(1);
                rs.close();
                pstmt.close();

                // Step 3: Delete that record
                String deleteSql = "DELETE FROM " + table + " WHERE " + keyColumn + " = ?";
                pstmt = conn.prepareStatement(deleteSql);
                pstmt.setString(1, lastKeyValue);

                int rowsAffected = pstmt.executeUpdate();
                return rowsAffected > 0;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }



    public void deleteAllRowsFromDB(String table, String column, String value, String dataType) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(url);

            String sql = "DELETE FROM " + table + " WHERE " + column + " = ?";
            pstmt = conn.prepareStatement(sql);

            // Bind value based on dataType
            switch (dataType.toLowerCase()) {
                                    case "int":
                    case "integer":
                        pstmt.setInt(1, Integer.parseInt(value));
                        break;

                    case "long":
                    case "long number": // new case
                        pstmt.setLong(1, Long.parseLong(value));
                        break;

                    case "double":
                    case "float":
                    case "currency":
                        pstmt.setDouble(1, Double.parseDouble(value));
                        break;

                    case "boolean":
                        pstmt.setBoolean(1, Boolean.parseBoolean(value));
                        break;

                    case "yes/no": // new case
                        // Interpret "Yes" or "No" as boolean true/false
                        boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                        pstmt.setBoolean(1, boolValue);
                    break;

                    case "date":
                        pstmt.setDate(1, java.sql.Date.valueOf(value)); // Format: yyyy-MM-dd
                        break;

                    default:
                        pstmt.setString(1, value);
                        break;
            }

            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsDeleted + " row(s) from " + table + " where " + column + " = " + value);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void clearDBKeepFirstRow(String table) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DriverManager.getConnection(url);

        // 1️⃣ Detect primary key column
        String primaryKeyField = null;
        java.sql.DatabaseMetaData dbMeta = conn.getMetaData();
        rs = dbMeta.getPrimaryKeys(null, null, table);
        if (rs.next()) {
            primaryKeyField = rs.getString("COLUMN_NAME");
        }
        rs.close();

        if (primaryKeyField == null) {
            throw new RuntimeException("Could not detect primary key column for table " + table);
        }

        // 2️⃣ Get the first row’s primary key value (lowest)
        String firstPkSql = "SELECT MIN(" + primaryKeyField + ") FROM " + table;
        pstmt = conn.prepareStatement(firstPkSql);
        rs = pstmt.executeQuery();
        Integer firstPkValue = null;
        if (rs.next()) {
            firstPkValue = rs.getInt(1);
        }
        rs.close();
        pstmt.close();

        if (firstPkValue == null) {
            System.out.println("Table " + table + " is empty. Nothing to delete.");
            return;
        }

        // 3️⃣ Delete all rows except that PK
        String deleteSql = "DELETE FROM " + table + " WHERE " + primaryKeyField + " <> ?";
        pstmt = conn.prepareStatement(deleteSql);
        pstmt.setInt(1, firstPkValue);
        int rowsDeleted = pstmt.executeUpdate();

        System.out.println("Deleted " + rowsDeleted + " row(s) from " + table + ", kept PK=" + firstPkValue);

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

    
    
    public void replaceItemInRow(String table,
                             String indexField,
                             String indexValue,
                             String replaceField,
                             String replaceValue,
                             String fieldType) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection(url);

            // Build SQL
            String sql = "UPDATE " + table + " SET " + replaceField + " = ? WHERE " + indexField + " = ?";
            pstmt = conn.prepareStatement(sql);

            // 1️⃣ Bind the new value (first ?). 
            // Currently always string; if you want typed, add another param for replaceFieldType
            pstmt.setString(1, replaceValue);

            // 2️⃣ Bind the indexValue (second ?)
            int paramIndex = 2; // <-- second placeholder
            switch (fieldType.toLowerCase()) {
                case "int":
                case "integer":
                    pstmt.setInt(paramIndex, Integer.parseInt(indexValue));
                    break;

                case "long":
                case "long number":
                    pstmt.setLong(paramIndex, Long.parseLong(indexValue));
                    break;

                case "double":
                case "float":
                case "currency":
                    pstmt.setDouble(paramIndex, Double.parseDouble(indexValue));
                    break;

                case "boolean":
                    pstmt.setBoolean(paramIndex, Boolean.parseBoolean(indexValue));
                    break;

                case "yes/no":
                    boolean boolValue = indexValue.equalsIgnoreCase("yes") || indexValue.equalsIgnoreCase("true");
                    pstmt.setBoolean(paramIndex, boolValue);
                    break;

                case "date":
                    pstmt.setDate(paramIndex, java.sql.Date.valueOf(indexValue)); // Format: yyyy-MM-dd
                    break;

                default:
                    pstmt.setString(paramIndex, indexValue);
                    break;
            }

            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Updated " + rowsUpdated + " row(s) in " + table +
                               " — set " + replaceField + "='" + replaceValue +
                               "' where " + indexField + "=" + indexValue);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void replaceRow(String table,
                       String indexField,
                       String indexValue,
                       String[] fields,
                       String[] row,
                       String[] fieldTypes,
                       String indexFieldType) {

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            if (fields.length != row.length || fields.length != fieldTypes.length) {
                System.out.println(Arrays.toString(row));
                System.out.println(Arrays.toString(fields));
                System.out.println(Arrays.toString(fieldTypes));
                throw new IllegalArgumentException("Fields, rows, and fieldTypes must have the same length.");
            }

            conn = DriverManager.getConnection(url);

            // 1️⃣ Build SQL dynamically
            StringBuilder sql = new StringBuilder("UPDATE " + table + " SET ");
            for (int i = 0; i < fields.length; i++) {
                sql.append(fields[i]).append("=?");
                if (i < fields.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append(" WHERE ").append(indexField).append("=?");

            pstmt = conn.prepareStatement(sql.toString());

            // 2️⃣ Bind the new values for each field
            for (int i = 0; i < row.length; i++) {
                String value = row[i];
                String type = fieldTypes[i].toLowerCase();
                int paramIndex = i + 1;

                switch (type) {
                    case "int":
                    case "integer":
                        pstmt.setInt(paramIndex, Integer.parseInt(value));
                        break;

                    case "long":
                    case "long number":
                        pstmt.setLong(paramIndex, Long.parseLong(value));
                        break;

                    case "double":
                    case "float":
                    case "currency":
                        pstmt.setDouble(paramIndex, Double.parseDouble(value));
                        break;

                    case "boolean":
                        pstmt.setBoolean(paramIndex, Boolean.parseBoolean(value));
                        break;

                    case "yes/no":
                        boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                        pstmt.setBoolean(paramIndex, boolValue);
                        break;

                    case "date":
                        pstmt.setDate(paramIndex, java.sql.Date.valueOf(value)); // Format: yyyy-MM-dd
                        break;

                    default:
                        pstmt.setString(paramIndex, value);
                        break;
                }
            }

            // 3️⃣ Bind the indexValue for WHERE clause (last parameter)
            int whereParamIndex = row.length + 1;
            switch (indexFieldType.toLowerCase()) {
                case "int":
                case "integer":
                    pstmt.setInt(whereParamIndex, Integer.parseInt(indexValue));
                    break;

                case "long":
                case "long number":
                    pstmt.setLong(whereParamIndex, Long.parseLong(indexValue));
                    break;

                case "double":
                case "float":
                case "currency":
                    pstmt.setDouble(whereParamIndex, Double.parseDouble(indexValue));
                    break;

                case "boolean":
                    pstmt.setBoolean(whereParamIndex, Boolean.parseBoolean(indexValue));
                    break;

                case "yes/no":
                    boolean boolValue = indexValue.equalsIgnoreCase("yes") || indexValue.equalsIgnoreCase("true");
                    pstmt.setBoolean(whereParamIndex, boolValue);
                    break;

                case "date":
                    pstmt.setDate(whereParamIndex, java.sql.Date.valueOf(indexValue)); // Format: yyyy-MM-dd
                    break;

                default:
                    pstmt.setString(whereParamIndex, indexValue);
                    break;
            }

            // 4️⃣ Execute update
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Updated " + rowsUpdated + " row(s) in " + table +
                               " — set multiple fields where " + indexField + "=" + indexValue);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public boolean checkColumnForValue(String table,
                                   String field,
                                   String value,
                                   String fieldType) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = DriverManager.getConnection(url);

            // Build SQL query
            String sql = "SELECT 1 FROM " + table + " WHERE " + field + " = ?";
            pstmt = conn.prepareStatement(sql);

            // Bind the parameter based on fieldType
            switch (fieldType.toLowerCase()) {
                case "int":
                case "integer":
                    pstmt.setInt(1, Integer.parseInt(value));
                    break;

                case "long":
                case "long number":
                    pstmt.setLong(1, Long.parseLong(value));
                    break;

                case "double":
                case "float":
                case "currency":
                    pstmt.setDouble(1, Double.parseDouble(value));
                    break;

                case "boolean":
                    pstmt.setBoolean(1, Boolean.parseBoolean(value));
                    break;

                case "yes/no":
                    boolean boolValue = value.equalsIgnoreCase("yes") || value.equalsIgnoreCase("true");
                    pstmt.setBoolean(1, boolValue);
                    break;

                case "date":
                    pstmt.setDate(1, java.sql.Date.valueOf(value)); // yyyy-MM-dd
                    break;

                default:
                    pstmt.setString(1, value);
                    break;
            }

            // Execute query
            rs = pstmt.executeQuery();

            // If any row exists, the value is present
            exists = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return exists;
    }

    
    

    
//    Table-specific commands:
//    Each table will have a specific method to avoid having to enter in each parameter every time.
//    To begin with, here are the different arrays to use in each method.
    
//    Inventory String[]s:
    String[] inventoryFields = {"ProdName", "Description", "Price", "Category", "Quantity", "Link"};
    String[] inventoryTypes = {"text", "text", "currency", "text", "int", "text"};
    
//    Carts String[]s:
    String[] cartsFields = {"UserID", "ProdID", "ProdName", "Price", "Category", "Quantity"};
    String[] cartsTypes = {"int", "int", "text", "currency", "text", "int"};
    
//    Users String[]s:
    String[] usersFields = {"Login", "Password", "FirstName", "LastName", "Email", "PhoneNumber", "ShippingStreet", "ShippingCity", "ShippingState", "ShippingZip", "BillingStreet", "BillingCity", "BillingState", "BillingZip"};
    String[] usersTypes = {"text", "text", "text", "text", "text", "long", "text", "text", "text", "int", "text", "text", "text", "int"};

//    Orders String[]s:
    String[] ordersFields = {"OrderID", "ProdID", "ProdName", "Category", "Quantity", "Fulfilled"};
    String[] ordersTypes = {"int", "int", "text", "text", "int", "boolean"};
    
//   Inventory-specific methods
    
    public String[] selectFromInventory(String field, String value, String fieldType){
        return this.selectFromDB("Inventory", field, value, fieldType);
    }
    
    public String[] selectFromInventoryByProdID(String ProdID) {
        return this.selectFromInventory("ProdID", ProdID, "int");
    }
    
    public String[][] searchInventory(String searchTerm) {
        return this.searchDB("Inventory", searchTerm);
    }
    
    public String getImageURLFromProdID(String ProdID) {
        String[] inventoryItem = this.selectFromInventoryByProdID(ProdID);
            return inventoryItem[6];
    }
    
    public String getURLFromProdID(String ProdID) {
        String[] inventoryItem = this.selectFromInventoryByProdID(ProdID);
            return inventoryItem[5];
    }

    public String[][] selectAllFromInventory(String field, String value, String fieldType){
        return this.selectAllFromDB("Inventory", field, value, fieldType);
    }

    public void addNewItemToInventory(String[] inventoryRows) {
        this.addNewItemFromArray("Inventory", inventoryFields, inventoryRows, inventoryTypes);
    }
    
    public void updateItemInInventory(String indexField, String indexValue, String replaceField, String replaceValue) {
        this.replaceItemInRow("Inventory", indexField, indexValue, replaceField, replaceValue, indexValue);
    }
    
    public void updateItemInInventoryWithProdID(String ProdID, String replaceField, String replaceValue) {
        this.updateItemInInventory("ProdID", ProdID, replaceField, replaceValue);
    }
    
    public void changeQuantityOfItemInInventory(String ProdID, String replaceValue) {
        this.updateItemInInventoryWithProdID(ProdID, "Quantity", replaceValue);
    }
    
    public void addMoreOfItemToInventory(String ProdID) {
        int quantity = Integer.parseInt(this.selectFromInventoryByProdID(ProdID)[4]);
        quantity += 1;
        this.changeQuantityOfItemInInventory(ProdID, Integer.toString(quantity));
    }
    
    public void addXOfItemToInventory(String ProdID, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.addMoreOfItemToInventory(ProdID);
        }
    }
    
    public void subtractOneOfItemFromInventory(String ProdID) {
        int quantity = Integer.parseInt(this.selectFromInventoryByProdID(ProdID)[4]);
        if (quantity > 0) {
            quantity -= 1;
            this.changeQuantityOfItemInInventory(ProdID, Integer.toString(quantity));
        }
    }
    
    public void subtractXOfItemFromInventory(String ProdID, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.subtractOneOfItemFromInventory(ProdID);
        }
    }
    public String validateSubtractXOfItemFromInventory(String ProdID, String Quantity) {

        String[] inventoryItem = this.selectFromInventoryByProdID(ProdID);

        if (inventoryItem == null) {
            return "ERROR: Item not found in inventory.";
        }

        // Extract fields
        String prodName = inventoryItem[1];
        String inventoryQuantityStr = inventoryItem[4];

        // Validate that both quantities are numeric
        int requestedQty;
        int inventoryQty;

        try {
            requestedQty = Integer.parseInt(Quantity);
            inventoryQty = Integer.parseInt(inventoryQuantityStr);
        } catch (NumberFormatException e) {
            return "ERROR: Invalid quantity format.";
        }

        System.out.println("Attempting to add " + requestedQty + 
            " × " + prodName + " from " + inventoryQty + " in inventory.");

        // Validate stock
        if (inventoryQty == 0) {
            return "ERROR: Out of stock.";
        }

        if (requestedQty > inventoryQty) {
            return "ERROR: Cannot add more than stock.";
        }

        // Subtract from inventory
        this.subtractXOfItemFromInventory(ProdID, requestedQty);

        return "Successfully added " + requestedQty + 
               " " + prodName + "(s) to cart from inventory.";
    }

//    public String validateSubtractXOfItemFromInventory(String ProdID, String Quantity) {
//        String[] inventoryItem = this.selectFromInventoryByProdID(ProdID);
//        String ProdName = inventoryItem[1];
//
//        String inventoryQuantity = inventoryItem[4];
//        System.out.println("Attempting to add " + Quantity + " " + ProdName + "(s) to cart from " + inventoryQuantity +" in inventory.");
//            if (inventoryQuantity.equals("0")) {
//            return "ERROR: Out of stock.";
//        } else if (Integer.parseInt(Quantity) > Integer.parseInt(inventoryQuantity)) {
//            return "ERROR: Cannot add more than stock.";
//        } else {
//            this.subtractXOfItemFromInventory(ProdID, Integer.parseInt(Quantity));
//            return "Successfully added " + Quantity + " " + ProdName + "(s) to cart from inventory.";
//        }
//    }
    
    public void replaceItemInInventory(String indexField, String indexValue, String[] row, String indexType) {
        this.replaceRow("Inventory", indexField, indexValue, this.inventoryFields, row, this.inventoryTypes, indexType);
    }
    
    public void replaceItemInInventoryWithProdID(String ProdID, String[]row) {
        this.replaceItemInInventory("ProdID", ProdID, row, "int");
    }

    public void deleteFirstItemFromInventory(String column, String value, String dataType) {
        this.deleteFirstRowFromDB("Inventory", column, value, dataType);
    }
    
    public void clearInventory() {
        this.clearDBKeepFirstRow("Inventory");
    }
    
//    Carts-specific methods:
    
    public String[] selectFromCarts(String field, String value, String fieldType){
        return this.selectFromDB("Carts", field, value, fieldType);
    }

    public String[][] selectCartFromUserID(String UserID) {
        return this.selectAllFromCarts("UserID", UserID, "int");
    }

    public String[][] getCartItemsPriceTotalFromUserID(String UserID) {
        // Get full cart data first
        String[][] fullCart = this.selectCartFromUserID(UserID);

        // Handle null or empty results safely
        if (fullCart == null || fullCart.length == 0) {
            return new String[0][0];
        }

        // Each row will contain 3 columns now: 4th, 5th, and 7th
        String[][] reducedCart = new String[fullCart.length][3];

        for (int i = 0; i < fullCart.length; i++) {
            reducedCart[i][0] = fullCart[i][3]; // 4th column
            reducedCart[i][1] = fullCart[i][4]; // 5th column   <-- added
            reducedCart[i][2] = fullCart[i][6]; // 7th column
        }

        return reducedCart;
    }

        
    public int getCartTotalFromUserID(String UserID) {
        int total = 0;
        String[][] cart = this.getCartItemsPriceTotalFromUserID(UserID);
        for (String[] row : cart) {
            total =+ Integer.parseInt(row[2]);
        }
        return total;
    }

    
    public String[] selectFromCartsByCartItemID(String CartItemID) {
        return this.selectFromCarts("CartItemID", CartItemID, "int");
    }

    public String[][] selectAllFromCarts(String field, String value, String fieldType){
        return this.selectAllFromDB("Carts", field, value, fieldType);
    }
    
    public void addItemToCarts(String cartRows[]) {
        this.addItemToTable("Carts", this.cartsFields, cartRows, "CartItemID");
    }

    public void addNewItemToCart(String[] cartRows) {
        this.addNewItemFromArray("Carts", cartsFields, cartRows, cartsTypes);
    }
    
    public boolean checkIfProdIDInCart(String ProdID, String UserID) {
        String[][] userCart = this.selectCartFromUserID(UserID);
        if (userCart == null) return false;

        for (int i = 0; i < userCart.length; i++) {
            // Check if the row has at least 2 columns
            if (userCart[i] != null && userCart[i].length > 1) {
                if (userCart[i][1].equals(ProdID)) {
                    return true;
                }
            }
        }
        return false;

    }
    
    public String getCartItemIDFromProdIdAndUserID(String ProdID, String UserID) {
        String[][] userCart = this.selectCartFromUserID(UserID);
        this.print2DArray(userCart);
        for (String[] order: userCart) {
            System.out.println(Arrays.toString(order));
            String userId = order[1];
            String prodId = order[2];
            System.out.println("Checking if " + UserID + " is " + userId + " and " + ProdID + " is " + prodId);
            if(userId.equals(UserID) && prodId.equals(ProdID)){
                return order[0];
            }
        }

        return "CartItemID not found";
    }
    
//public String getCartItemIDFromProdIdAndUserID(String ProdID, String UserID) {
//    String[][] userCart = this.selectCartFromUserID(UserID);
//
//    if (userCart == null || userCart.length == 0) {
//        System.out.println("Cart is empty for user: " + UserID);
//        return "CartItemID not found; no UserCart";
//    }
//
//    this.print2DArray(userCart);
//
//    for (int i = 0; i < userCart.length; i++) {
//        if (userCart[i] != null && userCart[i].length > 2) {
//            String rowProdID = userCart[i][2];
//            System.out.println("Row " + i + " -> UserID: " + userCart[i][1] + ", ProductID: " + rowProdID);
//
//            // Compare ignoring all whitespace
//            String cleanRowProdID = rowProdID.replaceAll("\\D", ""); // removes everything that's not a digit
//            String cleanProdID = ProdID.replaceAll("\\D", "");
//
//            if (cleanRowProdID.equals(cleanProdID)) {
//                return userCart[i][0].replaceAll("\\s",""); // CartItemID
//            }
//                    }
//                }
//
//    return "CartItemID not found";
//}
    




    
    public String addItemToCartFromInventory(String ProdID, String UserID, String addQuantity) {
        String[] inventoryItem = this.selectFromInventoryByProdID(ProdID);
        
        String[] cartRows;
        String ProdName = inventoryItem[1];
        String Price = inventoryItem[2];
        String Category = inventoryItem[3];
        String inventoryQuantity = inventoryItem[4];
        String cartQuantity;
        String validate = "";
        int newQuantity;
        System.out.println("Product ID:" + ProdID + " UserID:" + UserID);
        String CartItemID = this.getCartItemIDFromProdIdAndUserID(ProdID, UserID);
        System.out.println(CartItemID);
        if (CartItemID.equals("CartItemID not found")){
            newQuantity = Integer.valueOf(addQuantity);
            cartRows = new String[]{UserID, ProdID, ProdName, Price, Category, Integer.toString(newQuantity)};
            validate = this.validateSubtractXOfItemFromInventory(ProdID, addQuantity);
            if (validate.equals("ERROR: Cannot add more than stock.") || validate.equals("ERROR: Out of stock.")) {
                return validate;
            }
            System.out.println(Arrays.toString(cartRows));
            this.addItemToCarts(cartRows);
            System.out.println("Item in inventory:" + Arrays.toString(this.selectFromInventoryByProdID(ProdID)) +
                    "\nItem in cart:" + Arrays.toString(this.selectFromCartsByCartItemID(UserID + ProdID)));
            return validate;
        } else {
            cartQuantity = this.selectFromCartsByCartItemID(CartItemID)[6];
            newQuantity = Integer.valueOf(cartQuantity) + Integer.valueOf(addQuantity);
            cartRows = new String[]{UserID, ProdID, ProdName, Price, Category, Integer.toString(newQuantity)};
            System.out.println(Arrays.toString(cartRows));
            validate = this.validateSubtractXOfItemFromInventory(ProdID, addQuantity);
            if (validate.equals("ERROR: Cannot add more than stock.") || validate.equals("ERROR: Out of stock.")) {
                return validate;
            }
            
            System.out.println("Item in inventory:" + Arrays.toString(this.selectFromInventoryByProdID(ProdID)) +
                    "\nItem in cart:" + Arrays.toString(this.selectFromCartsByCartItemID(UserID + ProdID)));
            this.replaceItemInCartWithProdID(ProdID, cartRows);
            return validate;
        }
    }
    

    
    public String removeItemFromCartToInventory(String ProdID, String UserID, String putBackQuantity) {
        String[] inventoryItem = this.selectFromInventoryByProdID(ProdID);
        String cartItemID = this.getCartItemIDFromProdIdAndUserID(ProdID, UserID);
        String[] cartItem = this.selectFromCartsByCartItemID(cartItemID);
        String cartItemQuantity = cartItem[6];
        String ProdName = inventoryItem[1];
        
        if (Integer.parseInt(putBackQuantity) > Integer.parseInt(cartItemQuantity)) {
            return "Cannot return more than quantity of item in cart.";
        } else {
            this.addXOfItemToInventory(ProdID, Integer.parseInt(putBackQuantity));
            this.subtractXOfItemFromCartItemID(cartItemID, Integer.parseInt(putBackQuantity));
            if (cartItemQuantity.equals(putBackQuantity)) {
                
                this.deleteFirstItemFromCarts("CartItemID", cartItemID, "int");
                return "Successfully removed " + putBackQuantity + " " + ProdName + "(s) from cart into inventory. Item no longer in cart." ;
            }
            return "Successfully removed " + putBackQuantity + " " + ProdName + "(s) from cart into inventory." ;
        }
        
    }
    
    public void removeUserCart(String userId) {
        String[][] userCart = this.selectCartFromUserID(userId);
        for (String[] item : userCart) {
            String prodId = item[2];
            String quantity = item[6];
            this.removeItemFromCartToInventory(prodId, userId, quantity);
        }
    }
    
    public void updateItemInCart(String indexField, String indexValue, String replaceField, String replaceValue) {
        this.replaceItemInRow("Carts", indexField, indexValue, replaceField, replaceValue, indexValue);
    }

    public void updateItemInCartWithCartItemID(String CartItemID, String replaceField, String replaceValue) {
        this.updateItemInCart("CartItemID", CartItemID, replaceField, replaceValue);
    }
    
    public void updateItemRowInCartWithCartItemID(String CartItemID, String row){
        
    }
    
    public void changeQuantityOfItemInCartItemID(String CartItemID, String replaceValue) {
        this.updateItemInCartWithCartItemID(CartItemID, "Quantity", replaceValue);
    }
    
    public void addMoreOfItemToCartItemID(String CartItemID) {
        int quantity = Integer.parseInt(this.selectFromCartsByCartItemID(CartItemID)[5]);
        quantity += 1;
        this.changeQuantityOfItemInCartItemID(CartItemID, Integer.toString(quantity));
    }
    
    public void addXOfItemToCartItemID(String CartItemID, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.addMoreOfItemToInventory(CartItemID);
        }
    }
    
    public void subtractOneOfItemFromCartItemID(String CartItemID) {
        int quantity = Integer.parseInt(this.selectFromCartsByCartItemID(CartItemID)[6]);
        if (quantity > 0) {
            quantity -= 1;
            this.changeQuantityOfItemInCartItemID(CartItemID, Integer.toString(quantity));
        }
    }
    
    public void subtractXOfItemFromCartItemID(String CartItemID, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.subtractOneOfItemFromCartItemID(CartItemID);
        }
    }
    
    
    
    public void replaceItemInCart(String indexField, String indexValue, String[] row, String indexType) {
        this.replaceRow("Carts", indexField, indexValue, this.cartsFields, row, this.cartsTypes, indexType);
    }
    
    public void replaceItemInCartWithProdID(String ProdID, String[]row) {
        this.replaceItemInCart("ProdID", ProdID, row, "int");
    }
    
    public void deleteFirstItemFromCarts(String column, String value, String dataType) {
        this.deleteFirstRowFromDB("Carts", column, value, dataType);
    }
    
    public void clearCarts() {
        this.clearDBKeepFirstRow("Carts");
    }
    
//    Users-specific methods:
    public String[] selectFromUsers(String field, String value, String fieldType){
        return this.selectFromDB("Users", field, value, fieldType);
    }
    
    
    public String[] selectFromUsersByUserID(String UserID) {
        return this.selectFromUsers("UserID", UserID, "int");
    }
    
    public String[] selectLoginAndPasswordByUserID(String UserID){
        String[] returnArray = {this.selectFromUsersByUserID(UserID)[1],this.selectFromUsersByUserID(UserID)[2]};
        return returnArray;
    }
    
    public String[] selectUserFromLogin(String Login) {
        return this.selectFromUsers("Login", Login, "String");
    }
    
    public String[] selectUserFromID(String UserID) {
        return this.selectFromUsers("UserID", UserID, "String");
    }
    
    public boolean testLogin(String Login, String Password) {
        try {
            String[] testUser = this.selectUserFromLogin(Login);
            if (testUser[1].equals(Login)&& testUser[2].equals(Password)){
                return true;
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
       }
        return false;
    }

    public String[][] selectAllFromUsers(String field, String value, String fieldType){
        return this.selectAllFromDB("Users", field, value, fieldType);
    }

    public void addNewUser(String[] userRows) {
        this.addItemToTable("Users", this.usersFields, userRows, "UserID");
//        this.addNewItemFromArray("Users", usersFields, userRows, usersTypes);
    }
    
    public String addGuest() {
        String[] guestRows = {
            "GUEST",   // Login
            "GUEST",   // Password
            "GUEST",   // FirstName
            "GUEST",   // LastName
            "GUEST",   // Email
            "0",       // PhoneNumber (long)
            "GUEST",   // ShippingStreet
            "GUEST",   // ShippingCity
            "GUEST",   // ShippingState
            "0",       // ShippingZip (int)
            "GUEST",   // BillingStreet
            "GUEST",   // BillingCity
            "GUEST",   // BillingState
            "0"        // BillingZip (int)
        };

        this.addNewUser(guestRows);

        // Return newly generated UserID
        return this.selectLastFromDB("Users")[0];
    }

    public void removeGuests() {
        String[][] guestList = this.selectAllFromUsers("Login", "GUEST", "String");
        for (String[] guest : guestList) {
            String guestID = guest[0];
            this.removeUserCart(guestID);
        }
        this.deleteAllRowsFromDB("Users", "Login", "GUEST", "String");
        
    }
    
    public void updateUser(String indexField, String indexValue, String replaceField, String replaceValue) {
        this.replaceItemInRow("Users", indexField, indexValue, replaceField, replaceValue, indexValue);
    }
    
    public void updateUserWithUserID(String UserID, String replaceField, String replaceValue) {
        this.updateUser("UserID", UserID, replaceField, replaceValue);
    }
    
    public void replaceUser(String indexField, String indexValue, String[] row, String indexType) {
        this.replaceRow("Users", indexField, indexValue, this.usersFields, row, this.usersTypes, indexType);
    }
    
    public void replaceUserWithUserWithUserID(String UserID, String[]row) {
        this.replaceUser("UserID", UserID, row, "int");
    }

    public void deleteFirstUser(String column, String value, String dataType) {
        this.deleteFirstRowFromDB("Users", column, value, dataType);
    }
    
    public void deleteFirstUserWithUserID(String UserID) {
        this.deleteFirstUser("UserID", UserID, "int");
    }
    
    public void deleteLastUser() {
        this.deleteLastFromDB("Users");
    }
    
    
    public void clearUsers() {
        this.clearDBKeepFirstRow("Users");
    }
    
    public boolean checkForUserData(String field, String value, String fieldType) {
        if (this.checkColumnForValue("Users", field, value, fieldType)) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean checkForLogin (String value) {
        if (this.checkForUserData("Login", value, "String")) {
            return true;
        } else {
            return false;
        }
    }
    
        public boolean checkForEmail (String value) {
        if (this.checkForUserData("Email", value, "String")) {
            return true;
        } else {
            return false;
        }
    }
        

    
//    Orders-specific methods:
    public String[] selectFromOrders(String field, String value, String fieldType){
        return this.selectFromDB("Orders", field, value, fieldType);
    }
    
    public String[] selectOrderByProdOrderID(String ProdOrderID) {
        return this.selectFromOrders("ProdOrderID", ProdOrderID, "int");
    }
    
    public String[][] selectAllFromOrders(String field, String value, String fieldType){
        return this.selectAllFromDB("Orders", field, value, fieldType);
    }
    
    public String[][] selectAllCompleteOrders() {
        return this.selectAllFromOrders("Fulfilled", "TRUE", "boolean");
    }
    
    public String[][] trimmedMatrix(String[][] matrix) {
        try {
            int rows = matrix.length;
            int cols = matrix[0].length - 1; // exclude last column
            String[][] trimmed = new String[rows][cols];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    trimmed[i][j] = matrix[i][j];
                }
            }
            return trimmed;
        } catch (ArrayIndexOutOfBoundsException e) {
            return new String[0][0]; // return empty matrix
        }
    }

    
    public String[][] selectAllCompleteOrdersClean() {
        String[][] orders = this.selectAllCompleteOrders();
        return trimmedMatrix(orders);
    }
    
    public String[][] selectAllIncompleteOrders() {
        String[][] completeMatrix = this.selectAllFromOrders("Fulfilled", "FALSE", "boolean");
        return Arrays.copyOfRange(completeMatrix, 1, completeMatrix.length);
    }
    
    public String[][] selectAllIncompleteOrdersClean() {
        String[][] orders = this.selectAllIncompleteOrders();
        return trimmedMatrix(orders);
    }
    
    public void addNewOrder(String[] orderRows) {
        this.addItemToTable("Orders", this.ordersFields, orderRows, "ProdOrderID");
    }
    


    public void updateOrder(String indexField, String indexValue, String replaceField, String replaceValue) {
        this.replaceItemInRow("Orders", indexField, indexValue, replaceField, replaceValue, indexValue);
    }
    
    public void updateOrderWithProdOrderID(String ProdOrderID, String replaceField, String replaceValue) {
        this.updateOrder("ProdOrderID", ProdOrderID, replaceField, replaceValue);
    }
    
    public void toggleFulfilledOfOrder(String ProdOrderID) {
        
        String[] order = this.selectOrderByProdOrderID(ProdOrderID);
        if (order[6].equals("FALSE")) {
            this.updateOrderWithProdOrderID(ProdOrderID, "Fulfilled", "TRUE");
        } else {
            this.updateOrderWithProdOrderID(ProdOrderID, "Fulfilled", "FALSE");
        }
        
    }
    
    public void clearOrders() {
        this.clearDBKeepFirstRow("Orders");
    }
    
    public void deleteFirstItemFromOrders(String column, String value, String dataType) {
        this.deleteFirstRowFromDB("Orders", column, value, dataType);
    }
    
    public void orderCart(String UserID) {
        String[][] userCart = this.selectCartFromUserID(UserID);
        for (String[] cartItem : userCart) {
            String orderId = cartItem[0];
            String prodId = cartItem[2];
            String prodName = cartItem[3];
            String category = cartItem[5];
            String quantity = cartItem[6];
            String[] orderRows = {orderId, prodId, prodName, category, quantity, "FALSE"};
            System.out.println("Ordering " + orderRows);
            this.addNewOrder(orderRows);
        }
        this.removeUserCart(UserID);
    }
    
    
    public String[][] selectAllOfDB(String table) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String[][] resultArray = new String[0][0]; // default empty

        try {
            conn = DriverManager.getConnection(url);

            // Detect primary key column
            String primaryKeyField = null;
            java.sql.DatabaseMetaData dbMeta = conn.getMetaData();
            rs = dbMeta.getPrimaryKeys(null, null, table);
            if (rs.next()) {
                primaryKeyField = rs.getString("COLUMN_NAME");
            }
            rs.close();

            // Build SQL with ORDER BY primary key if found
            String sql = "SELECT * FROM " + table;
            if (primaryKeyField != null) {
                sql += " ORDER BY " + primaryKeyField;
            }

            pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = pstmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Move to last row to get total row count
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst(); // Reset cursor

            resultArray = new String[rowCount][columnCount];

            int rowIndex = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    resultArray[rowIndex][i - 1] = rs.getString(i);
                }
                rowIndex++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return resultArray;
    }
    
    public String[][] selectAllOfDBExceptFirst(String table) {
        // Get all rows first
        String[][] allRows = selectAllOfDB(table);

        // If there are 0 or 1 rows, return an empty array
        if (allRows.length <= 1) {
            return new String[0][0];
        }

        int rowCount = allRows.length - 1;
        int colCount = allRows[0].length;
        String[][] resultArray = new String[rowCount][colCount];

        // Copy all rows except the first
        for (int i = 1; i < allRows.length; i++) {
            System.arraycopy(allRows[i], 0, resultArray[i - 1], 0, colCount);
        }

        return resultArray;
    }



    public void print2DArray(String[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + "\t"); // tab-separated
            }
            System.out.println(); // new line for each row
        }
    }
    
    public void printAllRowsOrdered(String table) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);

            // Detect primary key column
            String primaryKeyField = null;
            java.sql.DatabaseMetaData dbMeta = conn.getMetaData();
            rs = dbMeta.getPrimaryKeys(null, null, table);
            if (rs.next()) {
                primaryKeyField = rs.getString("COLUMN_NAME");
            }
            rs.close();

            // Build SQL with ORDER BY primary key if found
            String sql = "SELECT * FROM " + table;
            if (primaryKeyField != null) {
                sql += " ORDER BY " + primaryKeyField;
            }

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // ✅ Print the table name first
            System.out.println("\n=== Table: " + table + " ===");

            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(meta.getColumnName(i) + "\t");
            }
            System.out.println();
            System.out.println("--------------------------------------------------");

            // Print each row
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    
    public void printAllDBs() {
        printAllRowsOrdered("Inventory");
        printAllRowsOrdered("Carts");
        printAllRowsOrdered("Users");
        printAllRowsOrdered("Orders");
    }



}
