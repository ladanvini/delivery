package com.vini.delivery.entities;

import java.util.Date;

public class DeliveryDate {
    private String postalCode;
    private Date deliveryDate;
    private Boolean isGreenDelivery;

    
    public DeliveryDate(String postalCode, Date deliveryDate, Boolean isGreenDelivery) {
        this.postalCode = postalCode;
        this.deliveryDate = deliveryDate;
        this.isGreenDelivery = isGreenDelivery;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public Date getDeliveryDate() {
        return deliveryDate;
    }
    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
    public Boolean getIsGreenDelivery() {
        return isGreenDelivery;
    }
    public void setIsGreenDelivery(Boolean isGreenDelivery) {
        this.isGreenDelivery = isGreenDelivery;
    }

    
}
