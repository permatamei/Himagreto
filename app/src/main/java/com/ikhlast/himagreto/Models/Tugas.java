package com.ikhlast.himagreto.Models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Tugas implements Serializable {
    private String Matkul;
    private String Deskripsi;
    private String Deadline;

    public String getMatkul() {
        return Matkul;
    }

    public void setMatkul(String Matkul) {
        this.Matkul = Matkul;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String Deskripsi) {
        this.Deskripsi = Deskripsi;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String Deadline) {
        this.Deadline = Deadline;
    }

    public Tugas() {}

    public Tugas(String Matkul, String Deskripsi, String Deadline) {
        this.Matkul = Matkul;
        this.Deskripsi = Deskripsi;
        this.Deadline = Deadline;
    }

    @Override
    public String toString() {
        return "Tugas{" +
                "Matkul='" + Matkul + '\'' +
                ", Deskripsi='" + Deskripsi + '\'' +
                ", Deadline='" + Deadline + '\'' +
                '}';
    }
}
