
import DB_Objects.DBManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Grant
 */
public class ResetInventoryClass {
    public static void main(String[] args) {
        DBManager dbm = new DBManager();
        dbm.updateItemInInventoryWithProdID("10001", "Quantity", "100");
        dbm.updateItemInInventoryWithProdID("10002", "Quantity", "0");
        dbm.updateItemInInventoryWithProdID("10003", "Quantity", "100");
        dbm.updateItemInInventoryWithProdID("10004", "Quantity", "100");
        dbm.updateItemInInventoryWithProdID("10005", "Quantity", "100");
        dbm.updateItemInInventoryWithProdID("10006", "Quantity", "10");
        dbm.updateItemInInventoryWithProdID("10007", "Quantity", "100");
        dbm.updateItemInInventoryWithProdID("10008", "Quantity", "100");
        dbm.updateItemInInventoryWithProdID("10009", "Quantity", "100");
        dbm.printAllDBs();
    }
}
