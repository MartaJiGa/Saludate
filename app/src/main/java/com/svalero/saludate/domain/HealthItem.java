package com.svalero.saludate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthItem {
    private String id;
    private String name;
    private String description;
    private String idDataType;
    private boolean sendNotifications;
    private int numberPeriodNotification;
    private String timeUnitNotification;
}
