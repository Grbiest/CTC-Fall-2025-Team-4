package DB_Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Andy Tran
 */
public class Customer extends User {
    private String email;
    private String phoneNumber;
    private String shippingStreet;
    private String shippingCity;
    private String shippingState;
    private String shippingZip;
    private String billingStreet;
    private String billingCity;
    private String billingState;
    private String billingZip;
    
    
    //private Cart cart;

    public Customer() {
        super();  // calls User’s constructor
        this.email = "";
        this.phoneNumber = "";
        this.shippingStreet = "";
        this.shippingCity = "";
        this.shippingState = "";
        this.shippingZip = "";
        this.billingStreet = "";
        this.billingCity = "";
        this.billingState = "";
        this.billingZip = "";
         
        //this.cart = new Cart();
        this.setRole("customer");
    }
    
    public Customer(String userId, String username, String password,
                String firstName, String lastName,
                String email, String phoneNumber,
                String shippingStreet, String shippingCity, String shippingState, String shippingZip,
                String billingStreet, String billingCity, String billingState, String billingZip) {
        super(userId, username, password, firstName, lastName);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shippingStreet = shippingStreet;
        this.shippingCity = shippingCity;
        this.shippingState = shippingState;
        this.shippingZip = shippingZip;
        this.billingStreet = billingStreet;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingZip = billingZip;
        this.setRole("customer");
    }
    
    public void setCustomerInfo(String userId, String username, String password,
                            String firstName, String lastName,
                            String email, String phoneNumber,
                            String shippingStreet, String shippingCity, String shippingState, String shippingZip,
                            String billingStreet, String billingCity, String billingState, String billingZip) {
        // Set inherited fields
        this.setUserId(userId);
        this.setUsername(username);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setRole("customer"); // enforce role consistency

        // Set customer-specific fields
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.shippingStreet = shippingStreet;
        this.shippingCity = shippingCity;
        this.shippingState = shippingState;
        this.shippingZip = shippingZip;
        this.billingStreet = billingStreet;
        this.billingCity = billingCity;
        this.billingState = billingState;
        this.billingZip = billingZip;
    }
    
    public void setCustomerInfoFromArray(String[] arr){
        this.setCustomerInfo(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5], arr[6], arr[7], arr[8], arr[9], arr[10], arr[11], arr[12], arr[13], arr[14]);
    }
    
    public void setCustomerInfoFromUserID(String userID) {
        this.setCustomerInfoFromArray(this.getDbm().selectFromUsersByUserID(userID));
    }
    
    



    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getShippingStreet() {
        return shippingStreet;
    }

    public void setShippingStreet(String shippingStreet) {
        this.shippingStreet = shippingStreet;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingZip() {
        return shippingZip;
    }

    public void setShippingZip(String shippingZip) {
        this.shippingZip = shippingZip;
    }

    public String getBillingStreet() {
        return billingStreet;
    }

    public void setBillingStreet(String billingStreet) {
        this.billingStreet = billingStreet;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingZip() {
        return billingZip;
    }

    public void setBillingZip(String billingZip) {
        this.billingZip = billingZip;
    }
    
        public void displayCustomerInfo() {
        System.out.println("=== Customer Information ===");
        System.out.println("User ID:        " + getUserId());
        System.out.println("Username:       " + getUsername());
        System.out.println("Password:       " + getPassword());
        System.out.println("First Name:     " + getFirstName());
        System.out.println("Last Name:      " + getLastName());
        System.out.println("Role:           " + getRole());
        System.out.println("Email:          " + email);
        System.out.println("Phone Number:   " + phoneNumber);
        System.out.println("--- Shipping Address ---");
        System.out.println("Street:         " + shippingStreet);
        System.out.println("City:           " + shippingCity);
        System.out.println("State:          " + shippingState);
        System.out.println("ZIP:            " + shippingZip);
        System.out.println("--- Billing Address ---");
        System.out.println("Street:         " + billingStreet);
        System.out.println("City:           " + billingCity);
        System.out.println("State:          " + billingState);
        System.out.println("ZIP:            " + billingZip);
        System.out.println("===========================");
    }

}