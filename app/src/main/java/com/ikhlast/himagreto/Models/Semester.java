package com.ikhlast.himagreto.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class Semester implements Serializable {
    //harus bener-bener sama kek di firebase
    private String Name;
    private String Detail;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String Detail) {
        this.Detail = Detail;
    }

    public Semester() {}

    public Semester(String Name, String Detail) {
        this.Name = Name;
        this.Detail = Detail;
    }

    @Override
    public String toString() {
        return " "+Name+" "+Detail;
    }
}
