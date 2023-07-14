package com.vini.delivery.controllers;

import java.util.Scanner;

public class HomeController {
    Scanner scanner;

    public HomeController(){
        scanner = new Scanner(System.in);
        System.out.println("--------- Hello! Welcome to Delivery Service! ---------");
    }

    public boolean startOrderCommand(){
        while(true){
            System.out.println("* Would you like to enter a new order?");
            System.out.println("-- Please enter y for yes and n for no");
            String command =  scanner.next();
            switch(command){
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println(">> Please enter a valid command.");
            }
        }
    }

    public String getPostalCode(){
        System.out.println("* Please enter your postal code:");
        String postalCode = scanner.next();
        return postalCode;
    }
    public String getProductListFilePath() {
        System.out.println("* Please enter the path to JSON FILE containing your product list:");
        System.out.println("-- you can provide a relative path from the root");

        String filePath = scanner.next();
        return filePath;
    }
}
