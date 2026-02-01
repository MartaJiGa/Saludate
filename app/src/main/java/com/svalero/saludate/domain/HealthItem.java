package com.svalero.saludate.domain;

import java.io.Serializable;

public class HealthItem implements Serializable {

    //region Properties

    private String id;
    private String name;
    private String description;
    private String idDataType;
    private boolean sendNotifications;
    private int numberPeriodNotification;
    private String timeUnitNotification;

    //endregion

    //region Constructor

    public HealthItem() {}

    //endregion

    //region Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdDataType() {
        return idDataType;
    }

    public void setIdDataType(String idDataType) {
        this.idDataType = idDataType;
    }

    public boolean isSendNotifications() {
        return sendNotifications;
    }

    public void setSendNotifications(boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
    }

    public int getNumberPeriodNotification() {
        return numberPeriodNotification;
    }

    public void setNumberPeriodNotification(int numberPeriodNotification) {
        this.numberPeriodNotification = numberPeriodNotification;
    }

    public String getTimeUnitNotification() {
        return timeUnitNotification;
    }

    public void setTimeUnitNotification(String timeUnitNotification) {
        this.timeUnitNotification = timeUnitNotification;
    }


    //endregion
}
