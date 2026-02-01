package com.svalero.saludate.domain;

import java.io.Serializable;

public class HealthDatatype implements Serializable {

    //region Properties

    private String id;
    private int code;
    private String type;

    //endregion

    //region Constructor

    public HealthDatatype(){}

    //endregion

    //region Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    //endregion
}
