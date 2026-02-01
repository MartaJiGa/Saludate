package com.svalero.saludate.domain;

import java.io.Serializable;

public class HealthEntry implements Serializable {
    private String id;
    private String idUser;
    private String idHealthItem;
    private String stringValue;
    private Integer intValue;
    private Double decimalValue;
    private String imageValue;
    private String dateValue;
    private Boolean booleanValue;
}
