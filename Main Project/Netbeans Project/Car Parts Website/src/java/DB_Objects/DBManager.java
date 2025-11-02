package DB_Objects;

import jakarta.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.File;
import java.util.Arrays;

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
            String dbPath = context.getRealPath("/db/CarDB.accdb");
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
    
    public void addNewItemFromArray(String table, String[] fields, String[] rowArray, String[] fieldTypes) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            if (fields.length != rowArray.length || fields.length != fieldTypes.length) {
                System.out.println("Fields length:" + fields.length + 
                                   " rowArray length:" + rowArray.length + 
                                   " FieldTypes length:" + fieldTypes.length );
                throw new IllegalArgumentException("Fields, rowArray, and fieldTypes must have the same length (excluding PK).");
            }

            conn = DriverManager.getConnection(url);

            // Step 1: Detect primary key column automatically
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

            // Step 2: Find next primary key value
            String pkQuery = "SELECT MAX(" + primaryKeyField + ") FROM " + table;
            pstmt = conn.prepareStatement(pkQuery);
            rs = pstmt.executeQuery();

            int nextPrimaryKey = 1;
            if (rs.next()) {
                nextPrimaryKey = rs.getInt(1) + 1;
            }

            rs.close();
            pstmt.close();

            // Step 3: Build final field list (PK + user fields)
            String[] allFields = new String[fields.length + 1];
            String[] allValues = new String[fields.length + 1];
            String[] allTypes = new String[fields.length + 1];

            allFields[0] = primaryKeyField;
            allValues[0] = String.valueOf(nextPrimaryKey);
            allTypes[0] = "int"; // assume PK is int

            for (int i = 0; i < fields.length; i++) {
                allFields[i + 1] = fields[i];
                allValues[i + 1] = rowArray[i];
                allTypes[i + 1] = fieldTypes[i];
            }

            // Step 4: Build SQL
            StringBuilder fieldList = new StringBuilder();
            StringBuilder placeholders = new StringBuilder();
            for (int i = 0; i < allFields.length; i++) {
                fieldList.append(allFields[i]);
                placeholders.append("?");
                if (i < allFields.length - 1) {
                    fieldList.append(", ");
                    placeholders.append(", ");
                }
            }

            String sql = "INSERT INTO " + table + " (" + fieldList + ") VALUES (" + placeholders + ")";
            pstmt = conn.prepareStatement(sql);

            // Step 5: Bind parameters correctly
            for (int i = 0; i < allValues.length; i++) {
                String value = allValues[i];
                String type = allTypes[i].toLowerCase();
                int paramIndex = i + 1; // <-- IMPORTANT FIX

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

            // Step 6: Execute insert
            pstmt.executeUpdate();

            System.out.println("Record added to " + table + " successfully.");

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
        int quantity = Integer.parseInt(this.selectFromInventoryByProdID(ProdID)[5]);
        quantity += 1;
        this.changeQuantityOfItemInInventory(ProdID, Integer.toString(quantity));
    }
    
    public void addXOfItemToInventory(String ProdID, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.addMoreOfItemToInventory(ProdID);
        }
    }
    
    public void subtractOneOfItemFromInventory(String ProdID) {
        int quantity = Integer.parseInt(this.selectFromInventoryByProdID(ProdID)[5]);
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
    
    public String[] selectFromCartsByCartItemID(String CartItemID) {
        return this.selectFromInventory("ProdID", CartItemID, "int");
    }

    public String[][] selectAllFromCarts(String field, String value, String fieldType){
        return this.selectAllFromDB("Carts", field, value, fieldType);
    }

    public void addNewItemToCart(String[] cartRows) {
        this.addNewItemFromArray("Carts", cartsFields, cartRows, cartsTypes);
    }
    
    public void updateItemInCart(String indexField, String indexValue, String replaceField, String replaceValue) {
        this.replaceItemInRow("Carts", indexField, indexValue, replaceField, replaceValue, indexValue);
    }

    public void updateItemInCartWithCartItemID(String CartItemID, String replaceField, String replaceValue) {
        this.updateItemInCart("CartItemID", CartItemID, replaceField, replaceValue);
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
        int quantity = Integer.parseInt(this.selectFromInventoryByProdID(CartItemID)[5]);
        if (quantity > 0) {
            quantity -= 1;
            this.changeQuantityOfItemInInventory(CartItemID, Integer.toString(quantity));
        }
    }
    
    public void subtractXOfItemFromCartItemID(String ProdID, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.subtractOneOfItemFromInventory(ProdID);
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
        this.addNewItemFromArray("Users", usersFields, userRows, usersTypes);
    }
    
    public String addGuest() {
        String[] guestRows = {"", "", "", "", "", "0", "GUEST", "GUEST", "GUEST", "0", "GUEST", "GUEST", "GUEST", "0"};
        this.addNewUser(guestRows);
        return this.selectLastFromDB("Users")[0];
    }
    
    public void updateUser(String indexField, String indexValue, String replaceField, String replaceValue) {
        this.replaceItemInRow("Users", indexField, indexValue, replaceField, replaceValue, indexValue);
    }
    
    public void updateUserWithUserID(String UserID, String replaceField, String replaceValue) {
        this.updateUser("UserID", UserID, replaceField, replaceValue);
    }
    
    public void replaceUser(String indexField, String indexValue, String[] row, String indexType) {
        this.replaceRow("Users", indexField, indexValue, this.inventoryFields, row, this.inventoryTypes, indexType);
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
        this.addNewItemFromArray("Orders", ordersFields, orderRows, ordersTypes);
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
