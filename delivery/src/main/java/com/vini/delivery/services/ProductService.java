package com.vini.delivery.services;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.vini.delivery.entities.Product;
import com.vini.delivery.entities.ProductType;

public class ProductService {
        public List<Product> getProductListFromFile(String filePath) throws FileNotFoundException, IOException, ParseException {
        ArrayList<Product> products = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader(filePath));

        for (Object o : a){
            Product product = parseJsonObj(o);
            products.add(product);

        }
        return products;
    }
    private Product parseJsonObj(Object o) {
        JSONObject prod = (JSONObject) o;
        if(!prod.keySet().containsAll(Arrays.asList("productId", "name", "deliveryDays", "productType", "daysInAdvance")))
            throw new IllegalArgumentException("Content of file is invalid.");

        Long productId = (Long) prod.get("productId");
        String name = (String) prod.get("name");
        JSONArray deliveryDaysList = (JSONArray) prod.get("deliveryDays");
        List<DayOfWeek> deliveryDays = new ArrayList<>();
        for(Object d : deliveryDaysList)
            deliveryDays.add(DayOfWeek.of(((Long)d).intValue()));
        String productType = (String) prod.get("productType");
        Long daysInAdvance = (Long) prod.get("daysInAdvance");

        return new Product(productId.intValue()
            , name, deliveryDays, ProductType.valueOf(productType)
            , daysInAdvance.intValue());
    }
;
}
