
public class Test {

    public static void main(String[] args) {
        DBManager dbm = new DBManager();
        
//        dbm.deleteFirstRowFromDB("Inventory", "ProdID", "10001", "integer");

        String table = "Inventory";
        String[] fields = {"ProdID", "ProdName", "Description", "Price", "Category", "Quantity"};
        String[] rows = {"10001", "Michelin Tires", "Michelin tires will tire you out", "100.00", "Tires", "3"};
        String[] types = {"int", "text", "text", "currency", "text", "int"};
        dbm.addRowFromArray(table, fields, rows, types);
//        String[] result = dbm.selectFromDB("Inventory", "ProdID", "10001", "integer");
//        String[] result = dbm.selectFromDB("Inventory", "Quantity", "3", "int");
//        if (result != null) {
//            System.out.println("First matching row:");
//            for (String value : result) {
//                System.out.println(value);
//            }
//        } else {
//            System.out.println("No matching row found.");
//        }
//        
//        String[][] results = dbm.selectAllFromDB("Inventory", "Quantity", "3", "integer");
//
//        if (results != null && results.length > 0) {
//            for (String[] row : results) {
//                System.out.println("Row:");
//                for (String value : row) {
//                    System.out.println("  " + value);
//                }
//            }
//        } else {
//            System.out.println("No matching rows found.");
//        }
//    }
    }
}
