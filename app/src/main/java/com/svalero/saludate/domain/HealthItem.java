package com.svalero.saludate.domain;

import java.io.Serializable;

public class HealthItem implements Serializable {
    private String id;
    private String name;
    private String description;
    private String idDataType;
    private boolean sendNotifications;
    private int numberPeriodNotification;
    private String timeUnitNotification;
}
