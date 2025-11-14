package com.svalero.saludate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthEntry {
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
