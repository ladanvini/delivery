package com.vini.delivery.services.product;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import com.vini.delivery.entities.Product;
import com.vini.delivery.entities.ProductType;
import com.vini.delivery.services.ProductService;

public class GetValidDeliveryDateRangeForProductsTests {
    ProductService productService;
    Instant fakeCurrentTime;
    @Before
    public void setUp() throws Exception{
        Clock clock = Clock.fixed(Instant.parse("2023-07-10T12:12:12.00Z"), ZoneId.of("UTC"));
        fakeCurrentTime = Instant.now(clock);

        productService = new ProductService();
    }

    @Test
    public void whenProductsHaveDifferentDaysInAdvance_thenReturnsTheLatestRange() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=0; idx<5; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.NORMAL,
                    idx
                )
            );
        }
        // Current Time = "2023-07-10T12:12:12.00Z"
        Date expectedStartDate = Date.from(Instant.parse("2023-07-14T00:00:00.00Z"));
        Date expectedEndDate = Date.from(Instant.parse("2023-07-25T00:00:00.00Z"));
        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertEquals(expectedStartDate, actualDateRange[0]);
            assertEquals(expectedEndDate, actualDateRange[1]);
        }
    }

    @Test
    public void whenProductsHaveNoDaysInAdvance_thenReturnsNowToFourteenDays() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=0; idx<5; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.NORMAL,
                    0
                )
            );
        }
        // Current Date is "2023-07-10T12:12:12.00Z"
        Date expectedStartDate = Date.from(Instant.parse("2023-07-10T12:12:12.00Z"));
        Date expectedEndDate = Date.from(Instant.parse("2023-07-25T00:00:00.00Z"));
        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertEquals(expectedStartDate, actualDateRange[0]);
            assertEquals(expectedEndDate, actualDateRange[1]);
        }
    }

    @Test
    public void whenExternalProductsAndNoDaysInAdvance_thenReturnsFromFiveToFourteenDays() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=0; idx<5; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.NORMAL,
                    0
                )
            );
        }
        testProducts.add(
                new Product(
                    6,
                    "some_name",
                    new HashSet<>(),
                    ProductType.EXTERNAL,
                    0
                )
            );

        // Current Date is "2023-07-10T12:12:12.00Z"
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date expectedStartDate = Date.from(Instant.parse("2023-07-15T00:00:00.00Z"));
        Date expectedEndDate = Date.from(Instant.parse("2023-07-25T00:00:00.00Z"));
        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertEquals(expectedStartDate, actualDateRange[0]);
            assertEquals(expectedEndDate, actualDateRange[1]);
        }
    }

    @Test
    public void whenExternalProductsAndSmallerDaysInAdvance_thenReturnsFromFiveToFourteenDays() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=0; idx<3; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.NORMAL,
                    idx
                )
            );
        }
        testProducts.add(
                new Product(
                    6,
                    "some_name",
                    new HashSet<>(),
                    ProductType.EXTERNAL,
                    0
                )
            );

        // Current Date is "2023-07-10T12:12:12.00Z"
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

        Date expectedStartDate = Date.from(Instant.parse("2023-07-15T00:00:00.00Z"));
        Date expectedEndDate = Date.from(Instant.parse("2023-07-25T00:00:00.00Z"));
        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertEquals(expectedStartDate, actualDateRange[0]);
            assertEquals(expectedEndDate, actualDateRange[1]);
        }
    }

    @Test
    public void whenExternalProductsAndLargerDaysInAdvance_thenReturnsLatestToFourteenDays() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=3; idx<9; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.EXTERNAL,
                    idx
                )
            );
        }

        // Current Date is "2023-07-10T12:12:12.00Z"

        Date expectedStartDate = Date.from(Instant.parse("2023-07-18T00:00:00.00Z"));
        Date expectedEndDate = Date.from(Instant.parse("2023-07-25T00:00:00.00Z"));

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertEquals(expectedStartDate, actualDateRange[0]);
            assertEquals(expectedEndDate, actualDateRange[1]);
        }
    }

    @Test
    public void whenTempProductsHaveDifferentDaysInAdvance_thenReturnsTheLatestUntilEndOfWeek() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=0; idx<5; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.TEMPORARY,
                    idx
                )
            );
        }
        // Current Date is "2023-07-10" and is on Monday
        // Meaning Temporary products can be delivered until the end of "2023-07-16" (Sunday)

        Date expectedStartDate = Date.from(Instant.parse("2023-07-14T00:00:00.00Z"));
        Date expectedEndDate = Date.from(Instant.parse("2023-07-17T00:00:00.00Z"));
        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertEquals(expectedStartDate, actualDateRange[0]);
            assertEquals(expectedEndDate, actualDateRange[1]);
        }
    }

    @Test
    public void whenTodayIsSundayAndTemporaryProductExists_thenReturnsTodaytoToday() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=0; idx<5; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.TEMPORARY,
                    0
                )
            );
        }
        // Current Date is "2023-07-16" and is on Sunday
        Clock clock = Clock.fixed(Instant.parse("2023-07-16T12:12:12.00Z"), ZoneId.of("UTC"));
        fakeCurrentTime = Instant.now(clock);
        // Meaning Temporary products can be delivered until the end of Today

        Date expectedStartDate = Date.from(Instant.parse("2023-07-16T12:12:12.00Z"));
        Date expectedEndDate = Date.from(Instant.parse("2023-07-17T00:00:00.00Z"));
        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertEquals(expectedStartDate, actualDateRange[0]);
            assertEquals(expectedEndDate, actualDateRange[1]);
        }
    }


    @Test
    public void whenTempProductsWithDaysInAdvancePassingCurrentWeek_thenReturnsNullNull() {
        List<Product> testProducts = new ArrayList<>();
        for(int idx=6; idx<9; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.TEMPORARY,
                    idx
                )
            );
        }
        // Current Date is "2023-07-10" and is on Monday
        // Meaning Temporary products can be delivered until the end of "2023-07-16" (Sunday)

        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertNull(actualDateRange[0]);
            assertNull(actualDateRange[1]);
        }
    }



    @Test
    public void whenProductsWithDaysInAdvancePassingCurrentWeekAndExistsSingleTemp_thenReturnsNullNull() {
        List<Product> testProducts = new ArrayList<>();
        // Current Date is "2023-07-10" and is on Monday
        // Meaning Temporary products can be delivered until the end of "2023-07-16" (Sunday)
        // So if there exist products with daysInAdvance >= 7 AND TEMP prods, delivery is not possible

        for(int idx=3; idx<9; idx++){
            testProducts.add(
                new Product(
                    idx,
                    "some_name"+idx,
                    new HashSet<>(),
                    ProductType.NORMAL,
                    idx
                )
            );
        }
        testProducts.add(
                new Product(
                    0,
                    "some_name",
                    new HashSet<>(),
                    ProductType.TEMPORARY,
                    0
                )
            );
            
        try (MockedStatic<Instant> mockedStatic = mockStatic(Instant.class)) {
            mockedStatic.when(Instant::now).thenReturn(fakeCurrentTime);
            Date[] actualDateRange = productService.getValidDeliveryDateRangeForProducts(testProducts);
        
            assertEquals(2, actualDateRange.length);
            assertNull(actualDateRange[0]);
            assertNull(actualDateRange[1]);
        }
    }

}
