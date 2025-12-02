
import DB_Objects.Customer;
import DB_Objects.DBManager;
import DB_Objects.Guest;
import DB_Objects.ProductList;
import java.util.Arrays;


public class Test {



    public static void main(String[] args) {
        DBManager dbm = new DBManager();
        
//        dbm.deleteAllRowsFromDB("Users", "Login", "JohnSmith", "text");
//        dbm.clearDBKeepFirstRow("Users");
//
//        String table = "Inventory";
//        String[] fields = {"ProdName", "Description", "Price", "Category", "Quantity", "Link"};
//        String[] rows = {"Michelin Tires", "Michelin tires will tire you out", "100.00", "Tires", "3", "...", "..."};
//        String[] types = {"text", "text", "currency", "text", "int", "text"};
//
//        String table = "Carts";
//        String[] fields = {"UserID", "ProdID", "ProdName", "Price", "Category", "Quantity"};
//        String[] rows = {"10000", "10000", null, "0.00", null, "0"};
        String[] rows = {"10002", "10001", "Michelin Tires", "100.00", "Tires", "3"};
//        String[] types = {"int", "int", "text", "currency", "text", "int"};
        
//        String table = "Users";
//        String[] fields = {"Login", "Password", "FirstName", "LastName", "Email", "PhoneNumber", "ShippingStreet", "ShippingCity", "ShippingState", "ShippingZip", "BillingStreet", "BillingCity", "BillingState", "BillingZip"};
//        String[] rows = {"JohnSmith", "12345", "John", "Smith", "jsmith@cars.com", "5555555", "123 Main St", "CityTown", "UT", "1234567", "123 Main St", "CityTown", "UT", "1234567"};
//        String[] rows = {"GeraldSmith", "12345", "Gerald", "Smith", "gsmith@cars.com", "0", "OPP", "OPP", "OPP", "0", "OPP", "OPP", "OPP", "0"};
//        String[] types = {"text", "text", "text", "text", "text", "long", "text", "text", "text", "int", "text", "text", "text", "int"};
//        String[] rows = {null, null, null, null, null, "0", null, null, null, "0", null, null, null, "0"};
////        
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

//        dbm.addNewItemFromArray(table, fields, rows, types);
        

//        dbm.updateItemInInventoryWithProdID("10001", "Quantity", "100");
//        dbm.updateItemInInventoryWithProdID("10002", "Quantity", "100");
//        dbm.updateItemInInventoryWithProdID("10003", "Quantity", "100");
//        dbm.updateItemInInventoryWithProdID("10004", "Quantity", "100");
//        dbm.updateItemInInventoryWithProdID("10005", "Quantity", "100");
//        dbm.updateItemInInventoryWithProdID("10006", "Quantity", "10");
//        dbm.updateItemInInventoryWithProdID("10007", "Quantity", "100");
//        dbm.updateItemInInventoryWithProdID("10008", "Quantity", "100");
//        dbm.updateItemInInventoryWithProdID("10009", "Quantity", "100");

        String link = "ProductPages/RainX Wipers.jsp";
        String imageLink = "i/51tcVQeOkhL.jpg";
        String prodId = "10001";
        String prodId2 = "10004";
//        dbm.updateItemInInventoryWithProdID(prodId, "ImageLink", imageLink);
//        dbm.updateItemInInventoryWithProdID(prodId, "Link", link);
        
//        ProductList plist = new ProductList(dbm.selectAllOfDB("Inventory"));
//        plist.displayProductList();
        String userId = "10002";
        String quantity = "3";
//        System.out.println(dbm.getCartItemIDFromProdIdAndUserID(prodId, userId));
//        System.out.println(dbm.addItemToCartFromInventory(prodId, userId, quantity));
//        System.out.println(dbm.removeItemFromCartToInventory(prodId, userId, quantity));

        dbm.addItemToCartFromInventory(prodId, userId, quantity);
//        dbm.clearCarts();
//        dbm.addGuest();
//        dbm.printAllDBs();
//        dbm.updateItemInInventoryWithProdID("10007", "Category", "Headlight Bulbs");
            dbm.printAllDBs();
//        String[][] arr = dbm.searchInventory("Wipers");
//            for (String[] row : arr) {
//        System.out.println(Arrays.toString(row));
//    }


        


        
        

        
    }
}
