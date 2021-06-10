package com.ikhlast.himagreto.Models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Tugas implements Serializable {
    private String Matkul, Hari, Node, Deskripsi, Deadline;

    public Tugas(String matkul, String hari, String node, String deskripsi, String deadline) {
        Matkul = matkul;
        Hari = hari;
        Node = node;
        Deskripsi = deskripsi;
        Deadline = deadline;
    }

    public Tugas(){}

    @Override
    public String toString() {
        return "Tugas{" +
                "Matkul='" + Matkul + '\'' +
                ", Hari='" + Hari + '\'' +
                ", Node='" + Node + '\'' +
                ", Deskripsi='" + Deskripsi + '\'' +
                ", Deadline='" + Deadline + '\'' +
                '}';
    }

    public String getMatkul() {
        return Matkul;
    }

    public void setMatkul(String matkul) {
        Matkul = matkul;
    }

    public String getHari() {
        return Hari;
    }

    public void setHari(String hari) {
        Hari = hari;
    }

    public String getNode() {
        return Node;
    }

    public void setNode(String node) {
        Node = node;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getDeadline() {
        return Deadline;
    }

    public void setDeadline(String deadline) {
        Deadline = deadline;
    }
}
