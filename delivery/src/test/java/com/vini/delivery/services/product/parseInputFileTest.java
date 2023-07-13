package com.vini.delivery.services.product;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vini.delivery.entities.Product;
import com.vini.delivery.entities.ProductType;
import com.vini.delivery.services.ProductService;

public class parseInputFileTest {
    ProductService productService;
    File testFile;
    BufferedWriter writer;

    @Before
    public void setUp() throws IOException, ParseException {
        productService = new ProductService();
        testFile = new File("test0.json");
        testFile.setWritable(true);
        testFile.createNewFile();
        writer = new BufferedWriter(new FileWriter(testFile.getPath()));
    }

    @After
    public void tearDown() {
        testFile.delete();
        
    }
    @Test
    public void whenFileHasSingleProduct_thenParsesProductCorrectly() throws IOException, ParseException {
        writer.write("[{ "
        +"    \"productId\": 0,"
        +"    \"name\": \"tomato\","
        +"    \"deliveryDays\": [1, 3, 6],"
        +"    \"productType\": \"NORMAL\","
        +"    \"daysInAdvance\": 0"
        +"}]");
        writer.close();

        List<Product> actualList = productService.getProductListFromFile(testFile.getPath());
        assertEquals(1, actualList.size());
        Product actual = actualList.get(0);
        ArrayList<DayOfWeek> expectedDaysOfWeek = new ArrayList<>(
            Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY)
            );
        assertEquals(0, actual.getProductId());
        assertEquals("tomato", actual.getName());
        assertIterableEquals(expectedDaysOfWeek,actual.getDeliveryDays());
        assertEquals(ProductType.NORMAL, actual.getProductType());
        assertEquals(0, actual.getDaysInAdvance());
        
    }
    @Test
    public void whenFileHasMultipleProduct_thenParsesAllCorrectly() throws IOException, ParseException {
        writer.write("["
        +"{ "
        +"    \"productId\": 0,"
        +"    \"name\": \"tomato\","
        +"    \"deliveryDays\": [1, 3, 6],"
        +"    \"productType\": \"NORMAL\","
        +"    \"daysInAdvance\": 0"
        +"}"
        +"{ "
        +"    \"productId\": 1,"
        +"    \"name\": \"cucumber\","
        +"    \"deliveryDays\": [2],"
        +"    \"productType\": \"TEMPORARY\","
        +"    \"daysInAdvance\": 10"
        +"}"
        +"]");
        writer.close();

        List<Product> actualList = productService.getProductListFromFile(testFile.getPath());
        assertEquals(2, actualList.size());
        
        ArrayList<DayOfWeek> expectedDaysOfWeek = new ArrayList<>(
            Arrays.asList(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SATURDAY)
            );

        assertEquals(0, actualList.get(0).getProductId());
        assertEquals("tomato", actualList.get(0).getName());
        assertIterableEquals(expectedDaysOfWeek,actualList.get(0).getDeliveryDays());
        assertEquals(ProductType.NORMAL, actualList.get(0).getProductType());
        assertEquals(0, actualList.get(0).getDaysInAdvance());
        
        assertEquals(1, actualList.get(1).getProductId());
        assertEquals("cucumber", actualList.get(1).getName());
        assertIterableEquals(
            new ArrayList<>(Arrays.asList(DayOfWeek.TUESDAY)),
            actualList.get(1).getDeliveryDays()
        );
        assertEquals(ProductType.TEMPORARY, actualList.get(1).getProductType());
        assertEquals(10, actualList.get(1).getDaysInAdvance());

    }
    @Test
    public void whenFileHasIncompleteProduct_thenRaises() throws IOException, ParseException {
        writer.write("["
        +"{ "
        +"    \"bloop\": 0,"
        +"    \"name\": \"tomato\","
        +"    \"deliveryDays\": [1, 3, 6],"
        +"    \"productType\": \"NORMAL\","
        +"    \"daysInAdvance\": 0"
        +"}"
        +"]");
        writer.close();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.getProductListFromFile(testFile.getPath());
        });
        String expectedMessage = "Content of file is invalid.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
    @Test
    public void whenFileHasInvalidFormat_thenRaises() throws IOException, ParseException {
        writer.write(""
        +"{ "
        +"    \"bloop\": 0,"
        +"    \"name\": \"tomato\","
        +"    \"deliveryDays\": [1, 3, 6],"
        +"    \"productType\": \"NORMAL\","
        +"    \"daysInAdvance\": 0"
        +"}"
        +"]");
        writer.close();
        assertThrows(ParseException.class, () -> {
            productService.getProductListFromFile(testFile.getPath());
        });
    }
}
