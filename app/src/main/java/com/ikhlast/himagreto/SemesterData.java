package com.ikhlast.himagreto;

import com.ikhlast.himagreto.Models.Semester;

import java.util.ArrayList;

public class SemesterData {
    private static String[] semesterNames = {
            "Semester 3",
            "Semester 4",
            "Semester 5"
    };

    private static String[] semesterDetail = {
            "Dasar-dasar Agronomi, Klimatologi, Mekanika Fluida, Meteorologi, Oseanografi Umum, Pengantar Ilmu Tanah",
            "Algoritme dan Pemrograman, Iklim dan Lingkungan, Metode Observasi dan Instrumentasi Meteorologi, Metode Statistika, Persamaan Differensial Biasa, Termodinamika Atmosfer",
            "Dinamika Atmosfer, Hidrometeorologi, Klimatologi Tropika, Metode Klimatologi, Meteorologi Satelit, Sains Perubahan Iklim"
    };

    static ArrayList<Semester> getListData() {
        ArrayList<Semester> list = new ArrayList<Semester>();
        for (int pos = 0; pos < semesterNames.length; pos++){
            Semester smt = new Semester();
            smt.setName(semesterNames[pos]);
            smt.setDetail(semesterDetail[pos]);
            list.add(smt);
        }
        return list;
    }
}
