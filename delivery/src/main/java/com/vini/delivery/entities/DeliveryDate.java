package com.vini.delivery.entities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

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
    @Override
    public String toString() {
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        sdf.setTimeZone(TimeZone.getTimeZone("CET"));
        String deliveryIsoStr = sdf.format(deliveryDate);
        return "\t{\n" 
                + "\t\tpostalCode:" + postalCode + ",\n"
                + "\t\tdeliveryDate:" + deliveryIsoStr + ",\n"
                + "\t\tisGreenDelivery:" + isGreenDelivery + ",\n"
                + "\t}";
    }

    
}
