package com.vini.delivery;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.vini.delivery.services.ProductService;

public class DeliveryApplication {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		ProductService productService = new ProductService();
		try {
			productService.getProductListFromFile("delivery\\src\\main\\resources\\static\\productList_0.json");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Goodbye world!");
		
	}

}
