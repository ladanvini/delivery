package com.vini.delivery.services;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vini.delivery.entities.Product;
import com.vini.delivery.entities.ProductType;

public class DeliveryDateService {

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
    public Date[] getValidDeliveryDateRangeForProducts(List<Product> products) {
        Date[] dateRange = {null, null};
        if(products== null || products.size()==0)
            return dateRange;   

        Date currentDate = Date.from(Instant.now());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        
        /*
         * Sunday = 1, so if today is Sunday, we have 7 days until next Sunday
         */
        int daysToSunday = 8 - calendar.get(Calendar.DAY_OF_WEEK);
        if (daysToSunday >= 7)
            daysToSunday = 0;
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
