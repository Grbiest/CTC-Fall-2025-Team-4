
public class Test {

    public static void main(String[] args) {
        DBManager dbm = new DBManager();
        
//        dbm.deleteAllRowsFromDB("Users", "Login", "JohnSmith", "text");
//        dbm.clearDBKeepFirstRow("Users");

        String table = "Inventory";
        String[] fields = {"ProdName", "Description", "Price", "Category", "Quantity", "Link"};
        String[] rows = {"Michelin Tires", "Michelin tires will tire you out", "100.00", "Tires", "3", "..."};
        String[] types = {"text", "text", "currency", "text", "int", "text"};
//
//        String table = "Carts";
//        String[] fields = {"UserID", "ProdID", "ProdName", "Price", "Category", "Quantity"};
//        String[] rows = {"10001", "10001", "Michelin Tires", "100.00", "Tires", "3"};
//        String[] types = {"int", "int", "text", "currency", "text", "int"};
        
//        String table = "Users";
//        String[] fields = {"Login", "Password", "FirstName", "LastName", "Email", "PhoneNumber", "ShippingStreet", "ShippingCity", "ShippingState", "ShippingZip", "BillingStreet", "BillingCity", "BillingState", "BillingZip"};
//        String[] rows = {"JohnSmith", "12345", "John", "Smith", "jsmith@cars.com", "5555555", "123 Main St", "CityTown", "UT", "1234567", "123 Main St", "CityTown", "UT", "1234567"};
//        String[] types = {"text", "text", "text", "text", "text", "long", "text", "text", "text", "int", "text", "text", "text", "int"};
//        
//        String table = "Orders";
//        String[] fields = {"OrderID", "ProdID", "ProdName", "Category", "Quantity", "Fulfilled"};
//        String[] rows = {"10000", "10000", " ", " ", "0", "True"};
//        String[] types = {"int", "int", "text", "text", "int", "boolean"};
//
//        dbm.addNewItemFromArray(table, fields, rows, types);
//        dbm.selectFromDB(table, "UserID", "10001", "int");

//        dbm.replaceItemInRow("Users", "UserID", "10001", "ShippingZip", "1111111", "int");
//        dbm.deleteAllRowsFromDB("Users", "PhoneNumber", "5555555", "int");



        dbm.printAllDBs();
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
