package com.svalero.saludate.domain;

import java.io.Serializable;

public class UserData implements Serializable {

    //region Properties

    private String firebaseUserId;
    private String name;
    private String surname;
    private String birthdate;
    private int sex;

    //endregion

    //region Constructor

    public UserData() {}

    //endregion

    //region Getters and Setters

    public UserData(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public String getFirebaseUserId() {
        return firebaseUserId;
    }

    public void setFirebaseUserId(String firebaseUserId) {
        this.firebaseUserId = firebaseUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    //endregion
}
