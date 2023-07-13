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
    public void whenOnlyValidDateIsToday_thenReturnsTodayAtNow(){
        Date startDate = Date.from(Instant.parse("2023-07-10T12:12:12.00Z"));
        Date endDate = Date.from(Instant.parse("2023-07-10T00:00:00.00Z"));
        Date[] range = {startDate, endDate};
        List<DeliveryDate> actual=deliveryService.getValidDeliveryDates("123456", range, new HashSet<>(Arrays.asList(DayOfWeek.MONDAY)));
        assertEquals(1, actual.size());
        assertEquals(startDate, actual.get(0).getDeliveryDate());
        assertFalse(actual.get(0).getIsGreenDelivery());
        assertEquals("123456", actual.get(0).getPostalCode());
    }

    @Test
    public void whenMultipleValidWeekDaysInTwoWeeks_thenReturnsOnlyThoseWithCorrectAttrs(){
        Date startDate = Date.from(Instant.parse("2023-07-10T12:12:12.00Z"));
        Date endDate = Date.from(Instant.parse("2023-07-25T00:00:00.00Z"));
        Date[] range = {startDate, endDate};
        List<DeliveryDate> actual=deliveryService.getValidDeliveryDates("123456",
            range,
            new HashSet<>(Arrays.asList(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY)));
        assertEquals(4, actual.size());
        for (DeliveryDate date:actual){
            assertEquals("123456", date.getPostalCode());
        }
        Date expectedDate0 = Date.from(Instant.parse("2023-07-11T00:00:00.00Z"));
        Date expectedDate1 = Date.from(Instant.parse("2023-07-13T00:00:00.00Z")); // GREEN!
        Date expectedDate2 = Date.from(Instant.parse("2023-07-18T00:00:00.00Z"));
        Date expectedDate3 = Date.from(Instant.parse("2023-07-20T00:00:00.00Z"));

        assertEquals(expectedDate0, actual.get(0).getDeliveryDate());
        assertFalse(actual.get(0).getIsGreenDelivery());

        assertEquals(expectedDate1, actual.get(1).getDeliveryDate());
        assertTrue(actual.get(1).getIsGreenDelivery());

        assertEquals(expectedDate2, actual.get(2).getDeliveryDate());
        assertFalse(actual.get(2).getIsGreenDelivery());

        assertEquals(expectedDate3, actual.get(3).getDeliveryDate());
        assertFalse(actual.get(3).getIsGreenDelivery());

    }

    @Test
    public void whenNoValidWeekdaysInRange_thenReturnsEmpty(){
        Date startDate = Date.from(Instant.parse("2023-07-10T12:12:12.00Z"));
        Date endDate = Date.from(Instant.parse("2023-07-14T00:00:00.00Z"));
        Date[] range = {startDate, endDate};
        List<DeliveryDate> actual=deliveryService.getValidDeliveryDates("123456", range, new HashSet<>(Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)));
        assertEquals(0, actual.size());

    }

}
