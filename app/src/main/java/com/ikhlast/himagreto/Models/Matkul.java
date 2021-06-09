package com.ikhlast.himagreto.Models;

import java.io.Serializable;

public class Matkul implements Serializable {
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

    public Matkul() {}

    public Matkul(String Name, String Detail) {
        this.Name = Name;
        this.Detail = Detail;
    }

    @Override
    public String toString() {
        return " "+Name+" "+Detail;
    }
}
