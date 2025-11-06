package DB_Objects;


import DB_Objects.DBManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Darkform
 */
/**
 * Base class for all user types in the system.
 */
public class User {
    private String userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private DBManager dbm = new DBManager();

    public User() {
        this.userId = "";
        this.username = "";
        this.password = "";
        this.firstName = "";
        this.lastName = "";
        this.role = "";
    }

    public User(String userId, String username, String password, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public void setUserInfo(String userId, String username, String password,
                            String firstName, String lastName, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }
    

    public String determineUserTypeByUserID(String uID) {
        String[] userArr = dbm.selectFromUsersByUserID(uID);
        if (userArr[7].equals("OPP")) {
            return "OrderProcessingPerson";
        }
        else
            return "Customer";
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return (this.getFirstName() + " " + this.getLastName());
    }
    
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public DBManager getDbm() {
        return this.dbm;
    }
    
    public void displayUserInfo() {
        System.out.println("=== User Information ===");
        System.out.println("User ID:    " + userId);
        System.out.println("Username:   " + username);
        System.out.println("Password:   " + password);
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name:  " + lastName);
        System.out.println("Role:       " + role);
        System.out.println("========================");
    }
}
