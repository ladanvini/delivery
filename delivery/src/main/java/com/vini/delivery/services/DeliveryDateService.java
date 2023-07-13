package com.vini.delivery.services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.vini.delivery.entities.Product;
import com.vini.delivery.entities.ProductType;

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

    /**
     * @param products
     * A delivery date is not valid if the customer has ordered a product that cannot be delivered in time.
     * This is determined by the daysInAdvance property on the product.
     * I.E. if the product needs to be ordered 4 days in advance, all delivery days before today + 4 are
     * invalid as the product cannot be delivered on time.
     *
     * All external products need to be ordered 5 days in advance.
     *
     * Temporary products can only be ordered within the current week (Mon-Sun).
     *
     * @return The valid delivery date range based on above rules
     */
    Date[] getValidDeliveryDateRangeForProducts(List<Product> products) {
        Date[] dateRange = {null, null};
        if(products== null || products.size()==0)
            return dateRange;

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        
        int daysToSunday = 7 - calendar.DAY_OF_WEEK;
        boolean tempFlag = false;
        int minDaysInAdvance = 0;
        for(Product product:products) {
            if(product.getProductType() == ProductType.EXTERNAL && minDaysInAdvance<5)
                minDaysInAdvance = 5;
            if(product.getDaysInAdvance()>minDaysInAdvance)
                minDaysInAdvance = product.getDaysInAdvance();
            if(product.getProductType()==ProductType.TEMPORARY && minDaysInAdvance>daysToSunday)
                return dateRange;
            if(product.getProductType()==ProductType.TEMPORARY)
                tempFlag = true;
        }
        int endOfRange = 14;
        if (tempFlag)
        endOfRange = daysToSunday;
        if (endOfRange < minDaysInAdvance)
        return dateRange;
        
        // Set end of range
        calendar.add(Calendar.DATE, endOfRange);
        dateRange[1] = calendar.getTime();
        // Reset calendar to now
        calendar.setTime(currentDate);
        // Set start of range
        calendar.add(Calendar.DATE, minDaysInAdvance);
        dateRange[0] = calendar.getTime();
        
        return dateRange;
    }


}
