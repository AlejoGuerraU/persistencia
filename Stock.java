package com.mycompany.persistencia;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.InputMismatchException;

public final class Stock {

    private ArrayList<Products> products = new ArrayList<>();
    private static final String JSON_FILE = "src/main/java/com/mycompany/persistencia/products.json";

    public Stock() {
        loadProductsFromJSON();
    }

    public ArrayList<Products> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Products> products) {
        this.products = products;
    }
//to load the data
    public void loadProductsFromJSON() {
        try (Reader reader = new FileReader(JSON_FILE)) {
            System.out.println("Ruta del archivo JSON: " + JSON_FILE);

            Gson gson = new Gson();
            Products[] productsArray = gson.fromJson(reader, Products[].class);
            if (productsArray != null) {
                products.addAll(Arrays.asList(productsArray));
                System.out.println("Products loaded from JSON file.");
            } else {
                System.err.println("Error: JSON file is empty or could not be parsed.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: JSON file not found - " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error: IO exception occurred - " + e.getMessage());
        }
    }
//to save produc data
    public void saveProductsToJSON() {
        try (Writer writer = new FileWriter(JSON_FILE)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(products, writer);
            System.out.println("Products saved to JSON file.");
        } catch (IOException e) {
            System.err.println("Error saving products to JSON: " + e.getMessage());
        }
    }
//"table"
    public void showProducts() {
        System.out.println("Product\t\tAmount\t\tPrice");
        for (Products product : products) {
            System.out.printf("%-15s %-10d %-10.2f\n", product.getName(), product.getAmount(), product.getPrice());
        }
    }
//function to add units
    public void addAmount(Scanner scan, String productNameToAdd) {
        String productName = getInputString(scan, "");
        Products product = findProduct(productName);
        if (product != null) {

            int units = getInputInt(scan, "Enter the number of units to add");
            if (units > 0) {

                product.setAmount(product.getAmount() + units);
            } else {
                System.out.println("Invalid amount.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }
//function to remove units
    public void removeAmount(Scanner scan, String productNameToRemove) {
        String productName = getInputString(scan, "");
        Products product = findProduct(productName);
        if (product != null) {
            int units = getInputInt(scan, "Enter the number of units to remove");
            if (units > 0 && units <= product.getAmount()) {
                product.setAmount(product.getAmount() - units);
            } else {
                System.out.println("Invalid quantity or not enough quantity in inventory.");
            }
        } else {
            System.out.println("Product not found.");
        }
    }

    public void delete(Scanner scan, String productNameToDelete) {
        String productName = getInputString(scan, "");
        Products product = findProduct(productName);
        if (product != null) {
            products.remove(product);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void filter(Scanner scan, String productNameToFilter) {
        String productName = getInputString(scan, "");
        Products product = findProduct(productName);
        if (product != null) {
            System.out.println("Product\t\tAmount\t\tPrice");
            System.out.printf("%-15s %-10d %-10.2f\n", product.getName(), product.getAmount(), product.getPrice());
        } else {
            System.out.println("Product not found.");
        }
    }
//Option menu
    public void options(Scanner scan) {
        int option;
        System.out.println("Welcome");
        do {
            System.out.println("Type the option you want to make");
            System.out.println("1. View inventory");
            System.out.println("2. Add units");
            System.out.println("3. Remove units");
            System.out.println("4. Delete Product");
            System.out.println("5. Filter product");
            System.out.println("6. Exit");
            option = getInputInt(scan, "");
            switch (option) {

                case 1 ->
                    showProducts();
                case 2 -> {
                    String productNameToAdd = getInputString(scan, "Enter the name of the Product to add units");
                    addAmount(scan, productNameToAdd);
                }
                case 3 -> {
                    String productNameToRemove = getInputString(scan, "Enter the name of the Product to remove units");
                    removeAmount(scan, productNameToRemove);
                }
                case 4 -> {
                    String productNameToDelete = getInputString(scan, "Enter the name of the Product to delete");
                    delete(scan, productNameToDelete);
                }
                case 5 -> {
                    String productNameToFilter = getInputString(scan, "Enter the name of the Product to filter");
                    filter(scan, productNameToFilter);
                }
                case 6 ->
                    saveProductsToJSON();
                default ->
                    System.out.println("Please enter a valid option");
            }
            System.out.println("---------------------------------------");
        } while (option != 6);
    }

    private String getInputString(Scanner scan, String prompt) {
        System.out.println(prompt);
        return scan.nextLine();
    }

    private int getInputInt(Scanner scan, String prompt) {
        int value = 0;
        boolean valid = false;
        do {
            try {
                System.out.println(prompt);
                value = scan.nextInt();
                valid = true;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scan.next();
            }
        } while (!valid);
        return value;
    }

    private Products findProduct(String productName) {
        for (Products product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }
}
