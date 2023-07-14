package com.vini.delivery;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.vini.delivery.controllers.DeliveryController;
import com.vini.delivery.controllers.HomeController;

public class DeliveryApplication {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		HomeController homeController = new HomeController();
		DeliveryController deliveryController = new DeliveryController();
		while(true) {
			Boolean run = homeController.startOrderCommand();
			if(!run)
				break;
			//"11558"
			String postalCode = homeController.getPostalCode();
			//"delivery\\src\\main\\resources\\static\\productList_0.json"
			String pathToProductListFile = homeController.getProductListFilePath();
			try {
					deliveryController.printAllDeliveryDatesForPostalCodeForProductListFile(postalCode, pathToProductListFile);
				} catch (FileNotFoundException e) {
					System.out.println(">> The file containing the product list was not found.");
					// IF DEBUG
					// e.printStackTrace();
				} catch (IOException e) {
					System.out.println(">> The file containing the product list could not be read.");
					// e.printStackTrace();
				} catch (ParseException e) {
					System.out.println(">> The file containing the product list has invalid format.");
					// e.printStackTrace();
				}
				
		}
		System.out.println("Goodbye world!");
	}

}
