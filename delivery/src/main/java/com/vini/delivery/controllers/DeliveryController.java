package com.vini.delivery.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.vini.delivery.entities.DeliveryDate;
import com.vini.delivery.entities.Product;
import com.vini.delivery.services.DeliveryDateService;
import com.vini.delivery.services.ProductService;

public class DeliveryController {
    DeliveryDateService deliveryDateService;
    ProductService productService;
    
    public DeliveryController(){
        deliveryDateService = new DeliveryDateService();
        productService = new ProductService();
    }

    public void printAllDeliveryDatesForPostalCodeForProductListFile(String postalCode, String fileToProductList) throws FileNotFoundException, IOException, ParseException{
            List<Product> allProducts = productService.getProductListFromFile(fileToProductList);
			List<DayOfWeek> validDaysOfWeek = productService.getValidWeekDaysForAllProducts(allProducts);
			Date[] dateRange = productService.getValidDeliveryDateRangeForProducts(allProducts);
			
			List<DeliveryDate> deliveryDatesUnsorted = deliveryDateService.getValidDeliveryDates(postalCode, dateRange, new HashSet<>(validDaysOfWeek));
			System.out.println("[");
			for(int i=0; i<deliveryDatesUnsorted.size()-1;i++)
				System.out.println(deliveryDatesUnsorted.get(i) + ",");
			System.out.println(deliveryDatesUnsorted.get(deliveryDatesUnsorted.size()-1));
			System.out.println("]");

    }

}
