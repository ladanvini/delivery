package com.vini.delivery.services;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.vini.delivery.entities.DeliveryDate;

public class DeliveryDateService {

    public List<DeliveryDate> getValidDeliveryDates(
        String postalCode,
        Date[] validDateRange,
        Set<DayOfWeek> validWeekDays)
    {
        List<DeliveryDate> deliveryDates = new ArrayList<>();


        return deliveryDates;
    }

}
