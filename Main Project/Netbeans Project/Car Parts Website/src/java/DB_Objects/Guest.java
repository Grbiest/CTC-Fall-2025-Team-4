package DB_Objects;


import DB_Objects.User;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Grant
 */
public class Guest extends User {

    // Default constructor
    public Guest() {
        super(); // This calls the User() constructor
        this.setRole("guest");
    }


}
