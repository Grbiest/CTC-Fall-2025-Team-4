
import DB_Objects.DBManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Grant
 */
public class RemoveGuestsClass {
        public static void main(String[] args) {
        DBManager dbm = new DBManager();
        dbm.removeGuests();
        dbm.printAllDBs();
    }
}
