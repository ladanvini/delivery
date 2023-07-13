package com.vini.delivery.services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vini.delivery.entities.Product;

public class DeliveryDateService {
    /**
     * @param allProducts
     * A delivery date is not valid if a product can't be delivered on that weekday.
     * @return list of valid delivery dates based on the above rule.
     */
    public List<DayOfWeek> getValidWeekDaysForAllProducts(List<Product> allProducts) {
        ArrayList<DayOfWeek> validWeekdays = new ArrayList<>();

        if(allProducts== null || allProducts.size()==0)
            return validWeekdays;
            
        int[] weekdayCount = {0,0,0,0,0,0,0};
        for(Product product:allProducts){
            for(DayOfWeek day:product.getDeliveryDays()){
                weekdayCount[day.getValue()-1]++;
            }
        }
        for(int idx=0; idx<7; idx++){
            if (weekdayCount[idx] == allProducts.size())
                validWeekdays.add(DayOfWeek.of(idx+1));
        }
        Collections.sort(validWeekdays);
        return validWeekdays;

    }
}
