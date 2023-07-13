package com.vini.delivery.services.product;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.vini.delivery.entities.Product;
import com.vini.delivery.entities.ProductType;
import com.vini.delivery.services.ProductService;

public class GetValidWeekDaysForAllProductsTest {
    ProductService productService;
    
    @Before
    public void setUp() {
         productService = new ProductService();
    }

    @Test
    public void whenAllProductsCanBeDeliveredOnAllDays_thenReturnsAllDays(){
        List<Product>  testProducts = new ArrayList<>();
        Set<DayOfWeek> allWeekDays =  new HashSet<>(Arrays.asList(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY,
                DayOfWeek.SUNDAY
            ));

        for(int i=0; i<5; i++) {
            testProducts.add(new Product(i,
            "some_name"+i,
            allWeekDays,
            ProductType.NORMAL,
            0));
        }
        
        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        List<DayOfWeek> expected = new ArrayList<>(allWeekDays);
        Collections.sort(expected);
        assertIterableEquals(expected, actual);
        
    }

    @Test
    public void whenAllProductsHaveNoDeliveryDays_thenReturnsEmpty(){
        List<Product>  testProducts = new ArrayList<>();
        Set<DayOfWeek> noWeekDays =  new HashSet<>();

        for(int i=0; i<5; i++) {
            testProducts.add(new Product(i,
            "some_name"+i,
            noWeekDays,
            ProductType.NORMAL,
            0));
        }
        
        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        List<DayOfWeek> expected = new ArrayList<>();
        assertIterableEquals(expected, actual);
        
    }

    @Test
    public void whenSomeProductsHaveNoDeliveryDays_thenReturnsEmpty(){
        List<Product>  testProducts = new ArrayList<>();
        Set<DayOfWeek> someWeekDays =  new HashSet<>(Arrays.asList(
                DayOfWeek.MONDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.SUNDAY
            ));

        for(int i=0; i<5; i++) {
            testProducts.add(new Product(i,
            "some_name"+i,
            someWeekDays,
            ProductType.NORMAL,
            0));
        }
        testProducts.add(new Product(6,
            "some_name",
            new HashSet<>(),
            ProductType.NORMAL,
            0));
        
        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        List<DayOfWeek> expected = new ArrayList<>();
        assertIterableEquals(expected, actual);
        
    }

    @Test
    public void whenAllProductsCanBeDeliveredOnSameDays_thenReturnsSameDays(){
        List<Product>  testProducts = new ArrayList<>();
        Set<DayOfWeek> sameWeekDays =  new HashSet<>(Arrays.asList(
                DayOfWeek.MONDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.SUNDAY
            ));

        for(int i=0; i<5; i++) {
            testProducts.add(new Product(i,
            "some_name"+i,
            sameWeekDays,
            ProductType.NORMAL,
            0));
        }
        
        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        List<DayOfWeek> expected = new ArrayList<>(sameWeekDays);
        Collections.sort(expected);
        assertIterableEquals(expected, actual);
        
    }

    @Test
    public void whenAllProductsOnlyHaveOneDayInCommon_thenReturnsOnlyCommonDay(){
        List<Product>  testProducts = new ArrayList<>();
        ArrayList<DayOfWeek> commonWeekDays =  new ArrayList<>(Arrays.asList(
                DayOfWeek.MONDAY
            ));

        for(int i=0; i<5; i++) {
            testProducts.add(new Product(i,
            "some_name"+i,
            new HashSet<>(Arrays.asList(commonWeekDays.get(0), DayOfWeek.of(i+1))),
            ProductType.NORMAL,
            0));
        }
        
        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        assertIterableEquals(commonWeekDays, actual);
        
    }

    @Test
    public void whenProductsHaveNoDaysInCommon_thenReturnsEmpty(){
        List<Product>  testProducts = new ArrayList<>();

        for(int i=0; i<5; i++) {
            testProducts.add(new Product(i,
            "some_name"+i,
            new HashSet<>(Arrays.asList(DayOfWeek.of(i+1))),
            ProductType.NORMAL,
            0));
        }
        
        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        assertIterableEquals(new ArrayList<>(), actual);
        
    }

    @Test
    public void whenSomeProductsHaveSomeDaysInCommon_thenReturnsEmpty(){
        List<Product>  testProducts = new ArrayList<>();

        for(int i=0; i<5; i++) {
            testProducts.add(new Product(i,
            "some_name"+i,
            new HashSet<>(Arrays.asList(DayOfWeek.of(i+1), DayOfWeek.of(i+2))),
            ProductType.NORMAL,
            0));
        }
        
        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        assertIterableEquals(new ArrayList<>(), actual);
        
    }


    @Test
    public void whenEmptyProducts_thenReturnsEmpty(){
        List<Product>  testProducts = new ArrayList<>();

        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        assertIterableEquals(new ArrayList<>(), actual);
    }

    @Test
    public void whenNoProducts_thenReturnsEmpty(){
        List<Product>  testProducts = null;

        List<DayOfWeek> actual = productService.getValidWeekDaysForAllProducts(testProducts);
        assertIterableEquals(new ArrayList<>(), actual);
    }

}
