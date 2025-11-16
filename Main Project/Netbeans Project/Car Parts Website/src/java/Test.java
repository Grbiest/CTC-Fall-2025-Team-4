
import DB_Objects.Customer;
import DB_Objects.DBManager;
import DB_Objects.Guest;
import java.util.Arrays;


public class Test {

    public static void main(String[] args) {
        DBManager dbm = new DBManager();
        
//        dbm.deleteAllRowsFromDB("Users", "Login", "JohnSmith", "text");
//        dbm.clearDBKeepFirstRow("Users");
//
//        String table = "Inventory";
//        String[] fields = {"ProdName", "Description", "Price", "Category", "Quantity", "Link"};
//        String[] rows = {"Michelin Tires", "Michelin tires will tire you out", "100.00", "Tires", "3", "..."};
//        String[] types = {"text", "text", "currency", "text", "int", "text"};
//
//        String table = "Carts";
//        String[] fields = {"UserID", "ProdID", "ProdName", "Price", "Category", "Quantity"};
//        String[] rows = {"10002", "10001", "Michelin Tires", "100.00", "Tires", "3"};
//        String[] types = {"int", "int", "text", "currency", "text", "int"};
        
//        String table = "Users";
//        String[] fields = {"Login", "Password", "FirstName", "LastName", "Email", "PhoneNumber", "ShippingStreet", "ShippingCity", "ShippingState", "ShippingZip", "BillingStreet", "BillingCity", "BillingState", "BillingZip"};
//        String[] rows = {"JohnSmith", "12345", "John", "Smith", "jsmith@cars.com", "5555555", "123 Main St", "CityTown", "UT", "1234567", "123 Main St", "CityTown", "UT", "1234567"};
//        String[] rows = {"GeraldSmith", "12345", "Gerald", "Smith", "gsmith@cars.com", "0", "OPP", "OPP", "OPP", "0", "OPP", "OPP", "OPP", "0"};
//        String[] types = {"text", "text", "text", "text", "text", "long", "text", "text", "text", "int", "text", "text", "text", "int"};
//        String[] rows = {null, null, null, null, null, "0", null, null, null, "0", null, null, null, "0"};
//        
//        String table = "Orders";
//        String[] fields = {"OrderID", "ProdID", "ProdName", "Category", "Quantity", "Fulfilled"};
//        String[] rows = {"10015", "10045", "Goodyear Tire", "5", "2", "False"};
//        String[] types = {"int", "int", "text", "text", "int", "boolean"};
//
//        dbm.addNewItemFromArray(table, fields, rows, types);
//        dbm.selectFromDB(table, "UserID", "10001", "int");

//        dbm.replaceItemInRow("Users", "UserID", "10001", "ShippingZip", "1111111", "int");
//        dbm.deleteAllRowsFromDB("Users", "PhoneNumber", "5555555", "int");

//        Customer cust1 = new Customer();
//        cust1.setCustomerInfoFromUserID("10001");
//        cust1.displayCustomerInfo();
        
        

        


//        dbm.updateUserWithUserID("10002", "PhoneNumber", "5555555555");
//        dbm.updateUserWithUserID("10002", "ShippingZIp", "12345");
//        dbm.updateUserWithUserID("10002", "BillingZIp", "12345");
        dbm.printAllDBs();
 //        dbm.addNewItemToCartFromInventory("10002", "10002");

        


        
        
//        User user1 = new User();
//
//        System.out.println(dbm.testLogin("JohnSmith", "12345"));
        
//        for (String value : jsmith) {
//                    System.out.println("  " + value);
//                }


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
//        String[][] results = dbm.selectAllIncompleteOrders();
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
