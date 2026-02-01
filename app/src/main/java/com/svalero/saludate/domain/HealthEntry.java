package com.svalero.saludate.domain;

import java.io.Serializable;

public class HealthEntry implements Serializable {

    //region Properties

    private String id;
    private String idUser;
    private String idHealthItem;
    private String stringValue;
    private Integer intValue;
    private Double decimalValue;
    private String imageValue;
    private String dateValue;
    private Boolean booleanValue;

    //endregion

    //region Constructor

    public HealthEntry() {}

    //endregion

    //region Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdHealthItem() {
        return idHealthItem;
    }

    public void setIdHealthItem(String idHealthItem) {
        this.idHealthItem = idHealthItem;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public Integer getIntValue() {
        return intValue;
    }

    public void setIntValue(Integer intValue) {
        this.intValue = intValue;
    }

    public Double getDecimalValue() {
        return decimalValue;
    }

    public void setDecimalValue(Double decimalValue) {
        this.decimalValue = decimalValue;
    }

    public String getImageValue() {
        return imageValue;
    }

    public void setImageValue(String imageValue) {
        this.imageValue = imageValue;
    }

    public String getDateValue() {
        return dateValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public Boolean getBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(Boolean booleanValue) {
        this.booleanValue = booleanValue;
    }


    //endregion
}
