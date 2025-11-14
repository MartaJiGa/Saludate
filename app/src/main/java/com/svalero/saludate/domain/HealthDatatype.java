package com.svalero.saludate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDatatype {
    private String id;
    private int code;
    private String type;
}
