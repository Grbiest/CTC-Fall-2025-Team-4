/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB_Objects;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Grant
 */
public class ProductList {
    private List<String[]> productList;
    
    public ProductList() {
        this.productList = new ArrayList<>();
    }
    
    public ProductList(String[][] product) {
        this.productList = new ArrayList<>();
        if (product != null) {
            for (String[] p : product) {
                if (p != null) {
                    this.productList.add(p);
                }
            }
        }
    }
    
    public void addProductToList(String[] product) {
        this.productList.add(product);
    }
    
    public void removeProductFromList(String[] product) {
        this.productList.remove(product);
    }
    
    public String[][] getProductListAsArray(){
        return this.productList.toArray(new String[0][]);
    }
    
    public void displayProductList() {
        System.out.println("========================================");
        System.out.println("            PRODUCT LIST");
        System.out.println("========================================");

        if (productList.isEmpty()) {
            System.out.println("No products in list.");
            System.out.println("========================================");
            return;
        }

        // Print each product as a row
        for (int i = 0; i < productList.size(); i++) {
            String[] product = productList.get(i);

            System.out.print("Row " + i + " | ");

            for (int j = 0; j < product.length; j++) {
                System.out.print(product[j]);

                if (j < product.length - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }

    System.out.println("========================================");
}


    
    
    
    
}
