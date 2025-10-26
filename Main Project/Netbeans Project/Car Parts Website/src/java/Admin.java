/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.teamproject;
//INHERIT Base class: User Child classes: Customer, OrderProcessingPerson From User, inherit Customer and OrderProcessingPerson
/**
 *
 * @author Andy Tran
 */

//This code is meant to inherit everything from the User.java and automatically have login, password, firstName, lastName, and userId and all their getter/setter
public class Admin extends User {

    // Default constructor
    public Admin() {
        super(); // This calls the User() constructor
    }

    // You can also add new fields unique to Admin if needed
    private String role = "ADMIN";

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
