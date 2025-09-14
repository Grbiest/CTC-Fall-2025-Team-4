import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.io.File;

public class DBManager {

    private static final String DB_RELATIVE_PATH = "db/CarDB.accdb";
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
                case "double":
                case "float":
                case "currency":
                    pstmt.setDouble(1, Double.parseDouble(value));
                    break;
                case "boolean":
                    pstmt.setBoolean(1, Boolean.parseBoolean(value));
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
            case "double":
            case "float":
            case "currency":
                pstmt.setDouble(1, Double.parseDouble(value));
                break;
            case "boolean":
                pstmt.setBoolean(1, Boolean.parseBoolean(value));
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
                        pstmt.setInt(i + 1, Integer.parseInt(value));
                        break;
                    case "double":
                    case "float":
                        pstmt.setDouble(i + 1, Double.parseDouble(value));
                        break;
                    case "boolean":
                        pstmt.setBoolean(i + 1, Boolean.parseBoolean(value));
                        break;
                    case "date":
                        pstmt.setDate(i + 1, java.sql.Date.valueOf(value)); // Format: yyyy-[m]m-[d]d
                        break;
                    default: // Treat as string
                        pstmt.setString(i + 1, value);
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
    
    public void deleteFirstRowFromDB(String table, String field, String value, String fieldType) {
        Connection conn = null;
        PreparedStatement selectStmt = null;
        PreparedStatement deleteStmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url);

            // Step 1: Select the first matching row
            String selectSQL = "SELECT * FROM " + table + " WHERE " + field + " = ?";
            selectStmt = conn.prepareStatement(selectSQL);

            switch (fieldType.toLowerCase()) {
                case "int":
                case "integer":
                    selectStmt.setInt(1, Integer.parseInt(value));
                    break;
                case "double":
                case "float":
                case "currency":
                    selectStmt.setDouble(1, Double.parseDouble(value));
                    break;
                case "boolean":
                    selectStmt.setBoolean(1, Boolean.parseBoolean(value));
                    break;
                case "date":
                    selectStmt.setDate(1, java.sql.Date.valueOf(value));
                    break;
                default:
                    selectStmt.setString(1, value);
                    break;
            }

            rs = selectStmt.executeQuery();

            if (rs.next()) {
                // Assume first column is the primary key
                ResultSetMetaData meta = rs.getMetaData();
                String primaryKeyField = meta.getColumnName(1);
                String primaryKeyValue = rs.getString(1);

                // Step 2: Delete using primary key
                String deleteSQL = "DELETE FROM " + table + " WHERE " + primaryKeyField + " = ?";
                deleteStmt = conn.prepareStatement(deleteSQL);
                deleteStmt.setString(1, primaryKeyValue);

                int rowsDeleted = deleteStmt.executeUpdate();
                System.out.println("Deleted " + rowsDeleted + " row(s) from " + table);
            } else {
                System.out.println("No matching row found to delete.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (selectStmt != null) selectStmt.close();
                if (deleteStmt != null) deleteStmt.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
