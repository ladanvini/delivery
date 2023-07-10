package com.vini.delivery.entities;
import java.time.DayOfWeek;
import java.util.List;

public class Product {
    private Integer productId;
    private String name;
    private List<DayOfWeek> deliveryDays;
    private ProductType productType;
    private Integer daysInAdvance;

    public Product(Integer productId, String name, List<DayOfWeek> deliveryDays, ProductType productType,
            Integer daysInAdvance) {
        this.productId = productId;
        this.name = name;
        this.deliveryDays = deliveryDays;
        this.productType = productType;
        this.daysInAdvance = daysInAdvance;
    }
    public Product() {
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<DayOfWeek> getDeliveryDays() {
        return deliveryDays;
    }
    public void setDeliveryDays(List<DayOfWeek> deliveryDays) {
        this.deliveryDays = deliveryDays;
    }
    public ProductType getProductType() {
        return productType;
    }
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    public Integer getDaysInAdvance() {
        return daysInAdvance;
    }
    public void setDaysInAdvance(Integer daysInAdvance) {
        this.daysInAdvance = daysInAdvance;
    }     
    
}
