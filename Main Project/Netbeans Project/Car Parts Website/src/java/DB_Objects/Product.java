/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB_Objects;

/**
 *
 * @author Darkform
 */
public class Product {
    private String prodId;
    private String name;
    private String description;
    private String price;   
    private String category;
    private String quantity;
    private String link;
    private String imageLink;

    // Default constructor
    public Product() {
        this.prodId = "";
        this.name = "";
        this.description = "";
        this.price = "";
        this.category = "";
    }

    // Getters and Setters
    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
