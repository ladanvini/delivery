package com.vini.delivery;

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

public class DeliveryApplication {

	public static void main(String[] args) {
		System.out.println("Hello world!");
		ProductService productService = new ProductService();
		DeliveryDateService deliveryDateService = new DeliveryDateService();

		try {
			List<Product> allProducts = productService.getProductListFromFile("delivery\\src\\main\\resources\\static\\productList_0.json");
			List<DayOfWeek> validDaysOfWeek = productService.getValidWeekDaysForAllProducts(allProducts);
			Date[] dateRange = productService.getValidDeliveryDateRangeForProducts(allProducts);
			
			List<DeliveryDate> deliveryDatesUnsorted = deliveryDateService.getValidDeliveryDates("123456", dateRange, new HashSet<>(validDaysOfWeek));
			System.out.println("[");
			for(int i=0; i<deliveryDatesUnsorted.size()-1;i++)
				System.out.println(deliveryDatesUnsorted.get(i) + ",");
			System.out.println(deliveryDatesUnsorted.get(deliveryDatesUnsorted.size()-1));
			System.out.println("]");
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
