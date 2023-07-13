package com.vini.delivery.services;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.vini.delivery.entities.DeliveryDate;

public class DeliveryDateService {

    public List<DeliveryDate> getValidDeliveryDates(
        String postalCode,
        Date[] validDateRange,
        Set<DayOfWeek> validWeekDays)
    {
        List<DeliveryDate> deliveryDates = new ArrayList<>();
        if(postalCode == null || postalCode == "")
            throw new IllegalArgumentException("Invalid Postal Code");
        if(validDateRange == null || validDateRange.length != 2)
            throw new IllegalArgumentException("Invalid Date Range");
        if(validWeekDays == null)
            throw new IllegalArgumentException("Invalid Week Days");

        // If no valid weekdays, then no valid delivery dates
        if(validWeekDays.size() ==0)
            return deliveryDates;
        Calendar start = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("UTC")));
        start.setTime(validDateRange[0]);
        Calendar end = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("UTC")));
        end.setTime(validDateRange[1]);
        if(end.get(Calendar.HOUR_OF_DAY)!=0 || end.get(Calendar.MINUTE)!=0 || end.get(Calendar.SECOND)!=0 || end.get(Calendar.MILLISECOND)!=0)
            throw new IllegalArgumentException("Invalid End of Date range. Must end at Midnight.");

        if(validWeekDays.contains(calendarDayToDayOfWeek(start.get(Calendar.DAY_OF_WEEK))))
            deliveryDates.add(new DeliveryDate(postalCode, start.getTime(), this.isGreen(start)));
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        
        while(start.get(Calendar.DATE) != end.get(Calendar.DATE)) {
            if(validWeekDays.contains(calendarDayToDayOfWeek(start.get(Calendar.DAY_OF_WEEK))))
                deliveryDates.add(new DeliveryDate(postalCode, start.getTime(), this.isGreen(start)));
            start.add(Calendar.DATE, 1);
        }
        
        return deliveryDates;
    }

    private boolean isGreen(Calendar date){
        /*If the date (day of month) is divisible by 7 or 13, it us green delivery!
        * (So basically 7, 13, 14, 21, 26 of each month)
        */
        return (date.get(Calendar.DATE)%7==0) || (date.get(Calendar.DATE)%13==0);
    }

    private DayOfWeek calendarDayToDayOfWeek(int calendarDay) {
        int day = (calendarDay + 6)%7;
        if(day == 0)
            return DayOfWeek.SUNDAY;
        return DayOfWeek.of(day);
    }
}
