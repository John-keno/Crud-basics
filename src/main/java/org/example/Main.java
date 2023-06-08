package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final String USERNAME1 = "admin";
    private static final String USERNAME2 = "user";

    private static final String PASSWORD = "password";
    private static String inputText;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);
        do {
            //Objects declaration
            Login login = new Login();

            System.out.println("===========================");
            System.out.println("====Basic Console Store====");
            System.out.println("==========================");
            System.out.println();
            System.out.println("[1] Login as Admin");
            System.out.println("[2] Login as Customer");
            System.out.println("[3] Exit console app");
            System.out.println("==========================");

            inputText = input.next();

            switch (inputText){
                case "1" ->{
                    System.out.println("Input Admin username");
                    inputText = input.next();
                    login.setUsername(inputText.toLowerCase());
                    System.out.println("Input Admin password");
                    inputText = input.next();
                    login.setPassword(inputText);

                    if(login.getUsername().equals(USERNAME1) && login.getPassword().equals(PASSWORD)){
                        do {

                            gotoAdminPanel(input);

                        }while (!inputText.equals("logout"));

                    }


                }
                case "2" -> {
                    System.out.println("Input username");
                    inputText = input.next();
                    login.setUsername(inputText.toLowerCase());
                    System.out.println("Input password");
                    inputText = input.next();
                    login.setPassword(inputText);

                    if(login.getUsername().equals(USERNAME2)){
                        do {
                            gotoUserPanel(input);
                        }while (!inputText.equals("logout"));

                    }
                }
                case "3" -> inputText = "exit";
            }

        }while (!inputText.equals("exit"));
    }

    private static void gotoUserPanel(Scanner input) {
        System.out.println();
        System.out.println("[1] List All products");
        System.out.println("[2] List Product By ID");
        System.out.println("[3] logout");
        System.out.println("==========================");
        inputText = input.next();
        switch (inputText) {
            case "1" -> {
                System.out.println("=====Retrieving all products========");
                ProductDAO dao = new ProductDAO();
                List<Product> products = dao.getAllProducts();
                for (Product product : products) {
                    System.out.println(product.toString());
                }
            }
            case "2" -> {
                int id;
                System.out.println("=====Retrieving product by ID========");
                System.out.println("Input product ID");
                inputText = input.next();
                id = Integer.parseInt(inputText);
                ProductDAO dao = new ProductDAO();
                Product product = dao.getProduct(id);
                if (product.getId() != 0){
                    System.out.println(product.toString());
                }else {
                    System.out.println("product not found");
                }
            }
            case "3" ->{
                inputText = "logout";
            }
        }
    }

    private static void gotoAdminPanel(Scanner input) {
        System.out.println();
        System.out.println("[1] List All products");
        System.out.println("[2] List Product By ID");
        System.out.println("[3] Add Product");
        System.out.println("[4] Update Product by ID");
        System.out.println("[5] Delete product by ID");
        System.out.println("[6] logout");
        System.out.println("==========================");
        inputText = input.next();
        switch (inputText){
            case "1" -> {
                System.out.println("=====Retrieving all products========");
                ProductDAO dao = new ProductDAO();
                List<Product> products = dao.getAllProducts();
                for (Product product : products){
                    System.out.println(product.toString());
                }
            }
            case "2" -> {
                int id;
                System.out.println("=====Retrieving product by ID========");
                System.out.println("Input product ID");
                inputText = input.next();
                id = Integer.parseInt(inputText);
                ProductDAO dao = new ProductDAO();
                Product product = dao.getProduct(id);
                if (product.getId() != 0){
                    System.out.println(product.toString());
                }else {
                    System.out.println("product not found");
                }
            }
            case "3" -> {
                Product product = new Product();
                ProductDAO dao = new ProductDAO();
                System.out.println("=====Adding Product========");
                System.out.println("Input product Name");
                inputText = input.next();
                product.setProductName(inputText);
                input.nextLine();

                System.out.println("Input product SKU");
                inputText = input.nextLine();
                product.setSKU(inputText);

                System.out.println("Input product Description");
                inputText = input.nextLine();
                product.setDescription(inputText);

                System.out.println("Input product Category");
                inputText = input.nextLine();
                product.setCategory(inputText);

                System.out.println("Input product price");
                inputText = input.nextLine();
                product.setPrice(Integer.parseInt(inputText));

                boolean isSuccessful = dao.addProduct(product);

                if(isSuccessful){
                    System.out.println("Product successfully added");
                }else{
                    System.out.println("Unable to add Product");
                }
            }
            case "4" -> {
                Product product;
                ProductDAO dao = new ProductDAO();
                System.out.println("=====Updating Product========");
                System.out.println("Input product ID");
                inputText = input.next();
                int id = Integer.parseInt(inputText);
                product = dao.getProduct(id);
                System.out.println("=====Printing out product========");
                if (product.getId() != 0){
                    System.out.println(product.toString());
                    System.out.println("Click enter to any field you don't want to modify");
                }else {
                    System.out.println("Product not found");
                    break;
                }
                input.nextLine();

                System.out.println("Input product Name");
                inputText = input.nextLine();
                if (!Objects.equals(inputText, "")){
                    product.setProductName(inputText);
                }

                System.out.println("Input product SKU");
                inputText = input.nextLine();
                if (!Objects.equals(inputText, "")){
                    product.setSKU(inputText);
                }

                System.out.println("Input product Description");
                inputText = input.nextLine();
                if (!Objects.equals(inputText, "")){
                    product.setDescription(inputText);
                }

                System.out.println("Input product Category");
                inputText = input.nextLine();
                if (!Objects.equals(inputText, "")){
                    product.setCategory(inputText);
                }

                System.out.println("Input product price");
                inputText = input.nextLine();
                int val = 0;
                if (!Objects.equals(inputText, "")){
                    val = Integer.parseInt(inputText);
                    product.setPrice(val);
                }

                boolean isSuccessful = dao.updateProduct(product);

                if(isSuccessful){
                    System.out.println("Product successfully Updated");
                }else{
                    System.out.println("Unable to Update Product");
                }
            }
            case "5" -> {
                ProductDAO dao = new ProductDAO();
                System.out.println("=====Retrieving all products========");
                List<Product> products = dao.getAllProducts();
                for (Product product : products){
                    System.out.println(product.toString());
                }
                System.out.println("=====Select Product you want to delete========");
                System.out.println("Input product ID");
                inputText = input.next();

                int id = Integer.parseInt(inputText);

                boolean successful = dao.deleteProduct(id);

                if(successful){
                    System.out.println("Product with ID "+id+" is deleted successfully");
                }else {
                    System.out.println("Unable to delete Product. pls check if ID is valid");
                }
            }

            case "6" ->{
                inputText = "logout";
            }
        }
    }


}