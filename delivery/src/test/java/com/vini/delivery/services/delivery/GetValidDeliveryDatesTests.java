package com.vini.delivery.services.delivery;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.vini.delivery.entities.DeliveryDate;
import com.vini.delivery.services.DeliveryDateService;

public class GetValidDeliveryDatesTests {
    DeliveryDateService deliveryService;
    Instant fakeCurrentTime;

    @Before
    public void setUp(){
        deliveryService = new DeliveryDateService();
    }

    @Test
    public void whenOnlyValidDayIsToday_thenReturnsTodayAtNow(){
        Date startDate = Date.from(Instant.parse("2023-07-10T12:12:12.00Z"));
        Date endDate = Date.from(Instant.parse("2023-07-10T00:00:00.00Z"));
        Date[] range = {startDate, endDate};
        List<DeliveryDate> actual=deliveryService.getValidDeliveryDates("123456", range, new HashSet<>(Arrays.asList(DayOfWeek.MONDAY)));
        assertEquals(1, actual.size());
    }
}
