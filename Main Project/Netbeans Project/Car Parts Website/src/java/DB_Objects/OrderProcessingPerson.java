package DB_Objects;


import DB_Objects.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Darkform
 */
public class OrderProcessingPerson extends User {
    public OrderProcessingPerson() {
        super(); // inherits everything from User
        this.setRole("orderProcessingPerson");
    }
    public OrderProcessingPerson(String userId, String username, String password, String firstName, String lastName) {
        super(userId, username, password, firstName, lastName);
        this.setRole("orderProcessingPerson");
    }
    public void setOrderProcessingPersonInfoFromArray(String[] OPParr) {
        this.setUserInfo(OPParr[0], OPParr[1], OPParr[2], OPParr[3], OPParr[4], "orderprocessingperson");
    }
}
