package com.ikhlast.himagreto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.himagreto.Adapters.AdapterAdmin;
import com.ikhlast.himagreto.Adapters.AdapterHome;
import com.ikhlast.himagreto.Adapters.AdapterTugasAdmin;
import com.ikhlast.himagreto.Adapters.AdapterTugasAdmin;
import com.ikhlast.himagreto.Models.Semester;
import com.ikhlast.himagreto.Models.Tugas;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Admin extends AppCompatActivity implements AdapterAdmin.DataListener, AdapterTugasAdmin.DataListener, AdapterTugasAdmin.DeleteListener, AdapterTugasAdmin.EditListener, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db;
    private RecyclerView rv, rv1, rvAdmin, rvAngkatanAktif, rvSenin, rvSelasa, rvRabu, rvKamis, rvJumat;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager, layoutManager1, lmAdmin, lmSenin, lmSelasa, lmAngkatanAktif, lmRabu, lmKamis, lmJumat;
    private ArrayList<Semester> semester = new ArrayList<>();
    private ArrayList<Tugas> tgSenin, tgSelasa, tgRabu, tgKamis, tgJumat;
    private ArrayList<String> listAktif, listAngkatan;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user, tanggal, jam, x;

    private ImageView iv, tbSenin, tbSelasa, tbRabu, tbKamis, tbJumat;
    private TextView tv, greeting, tambah, batal, edit;
    private EditText matkul, deskripsi, deadline;
    private TextInputLayout txInputMatkul, txInputDeskripsi, txInputDeadline;
    private Button w;

    private MaterialCardView mcvNama, mcvDomisili, mcvHp, mcvHbd;

    private ScrollView sv1, sv2;
    private View vFrame, v, vs;
    private Spinner spAngkatan;

    AlertDialog.Builder alert;
    AlertDialog dialog;
    Sessions sessions;

    BottomNavigationView bnv;
    ViewPager viewPager;
    viewAdapter vpAdapter;
    int in = 1;
    int[] layouts;
    LinearLayout lb;
    Intent i;
    Scene scene1, scene2;
    ViewGroup sceneRoot, sceneFirst;
    Transition tFade;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        alert = new AlertDialog.Builder(this);
        sessions = new Sessions(getApplicationContext());
        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");

        iv = findViewById(R.id.foto);
        tv = findViewById(R.id.ikhlas_tauf);
        greeting = findViewById(R.id.good_morning);
        setJam();

        Glide.with(Admin.this)
                .load(R.drawable.profil)
                .into(iv);

        bnv = findViewById(R.id.nav_admin);
        bnv.getMenu().getItem(0).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.view_pager_home);

        layouts = new int[]{
                R.layout.admin_monitor,
                R.layout.admin_tugas,
                R.layout.admin_comingsoon
        };

        vpAdapter = new viewAdapter();
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(pagelistener);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        sceneFirst = findViewById(R.id.design_admin);

        //ini buat smentara, sebenernya ngalangin navigation, nanti coba diakalin lagi buat developnya.
        //di home juga
        v = inflater.inflate(R.layout.admin_monitor_inflate, sceneFirst, false);
        sceneFirst.addView(v);

        rvAdmin = findViewById(R.id.admin_monitor_rvAdmin);
        rvAngkatanAktif = findViewById(R.id.admin_monitor_rvAngkatanAktif);
        rvAdmin.setHasFixedSize(true);
        rvAngkatanAktif.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        rvAdmin.setLayoutManager(layoutManager);
        rvAngkatanAktif.setLayoutManager(layoutManager1);

        vFrame = findViewById(R.id.group_10);
        sceneRoot = findViewById(R.id.scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene2, this);
        getAdmin();
        setUsername("1");

    }

    private void setJam(){
        tanggal = DateFormat.getDateTimeInstance().format(new Date());
        tanggal = tanggal.replace(".", ":");
        jam = tanggal.substring(tanggal.indexOf(":")-2);
        if (jam.contains("PM")){

            if (Integer.parseInt(jam.substring(0, 2).trim()) < 12 && Integer.parseInt(jam.substring(0, 2).trim().trim()) >= 6){
                greeting.setText(R.string.selamat_malam);
            } else if (Integer.parseInt(jam.substring(0, 2).trim()) < 6 && Integer.parseInt(jam.substring(0, 2).trim()) >= 3){
                greeting.setText(R.string.selamat_sore);
            } else if (Integer.parseInt(jam.substring(0, 2).trim()) < 3 && Integer.parseInt(jam.substring(0, 2).trim()) >= 0){
                greeting.setText(R.string.selamat_siang);
            }
        } else if (jam.contains("AM")){
            if (Integer.parseInt(jam.substring(0, 2).trim()) < 12 && Integer.parseInt(jam.substring(0, 2).trim()) >= 11){
                greeting.setText(R.string.selamat_siang);
            } else if (Integer.parseInt(jam.substring(0, 2).trim()) < 11 && Integer.parseInt(jam.substring(0, 2).trim()) >= 0){
                greeting.setText(R.string.selamat_pagi);
            }
        } else {
            if (Integer.parseInt(jam.substring(0, 2).trim()) < 24 && Integer.parseInt(jam.substring(0, 2).trim()) >= 18){
                greeting.setText(R.string.selamat_malam);
            } else if (Integer.parseInt(jam.substring(0, 2).trim()) < 11 && Integer.parseInt(jam.substring(0, 2).trim()) >= 0){
                greeting.setText(R.string.selamat_pagi);
            } else if (Integer.parseInt(jam.substring(0, 2).trim()) < 18 && Integer.parseInt(jam.substring(0, 2).trim()) >= 15) {
                greeting.setText(R.string.selamat_sore);
            } else if (Integer.parseInt(jam.substring(0, 2).trim()) < 15 && Integer.parseInt(jam.substring(0, 2).trim()) >= 11){
                greeting.setText(R.string.selamat_siang);
            }
        }
    }

    private void setUsername(String num){
        db.child("User/"+mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (num.equals("1")) {
                    if (snapshot.child("nama").exists()){
                        tv.setText(snapshot.child("nama").getValue(String.class));
                    } else {
                        tv.setText(user.toUpperCase());
                    }
//                } else if (!num.equals("1")){
//                    if (snapshot.child("nama").exists()){
//                        tv.setText(snapshot.child("nama").getValue(String.class));
//                        tvNama.setText(snapshot.child("nama").getValue(String.class));
//                    } else {
//                        tv.setText(user.toUpperCase());
//                    }
//                    if (snapshot.child("nohp").exists()){
//                        tvHp.setText(snapshot.child("nohp").getValue(String.class));
//                    }
//                    if (snapshot.child("domisili").exists()){
//                        tvDom.setText(snapshot.child("domisili").getValue(String.class));
//                    }
//                    if (snapshot.child("hbd").exists()){
//                        tvHbd.setText(snapshot.child("hbd").getValue(String.class));
//                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    ViewPager.OnPageChangeListener pagelistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                LinearLayout ll = findViewById(R.id.layout_prebuild);
                ll.setVisibility(View.VISIBLE);

                iv = findViewById(R.id.foto);
                tv = findViewById(R.id.ikhlas_tauf);
                greeting = findViewById(R.id.good_morning);
                setJam();

                Glide.with(Admin.this)
                        .load(R.drawable.profil)
                        .into(iv);

                bnv.getMenu().getItem(1).setChecked(true);
                
                rvAdmin = findViewById(R.id.admin_monitor_rvAdmin);
                rvAngkatanAktif = findViewById(R.id.admin_monitor_rvAngkatanAktif);

                rvAdmin.setHasFixedSize(true);
                rvAngkatanAktif.setHasFixedSize(true);

                lmAdmin = new LinearLayoutManager(getApplicationContext());
                lmAngkatanAktif = new LinearLayoutManager(getApplicationContext());

                rvAdmin.setLayoutManager(lmAdmin);
                rvAngkatanAktif.setLayoutManager(lmAngkatanAktif);

                getAdmin();
                sceneFirst.removeView(v);
                setUsername("1");
            } else if (position == 1){

                iv = findViewById(R.id.foto);
                tv = findViewById(R.id.ikhlas_tauf);
                greeting = findViewById(R.id.good_morning);
                setJam();

                Glide.with(Admin.this)
                        .load(R.drawable.profil)
                        .into(iv);

                bnv.getMenu().getItem(1).setChecked(true);
                db.child("List/AngkatanAktif").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listAngkatan = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            listAngkatan.add(ds.getValue(String.class));
                        }

                        spAngkatan = findViewById(R.id.spinnerTugasPerAngkatan);
                        ArrayAdapter<String> adapterAngkatan = new ArrayAdapter<String>(Admin.this, android.R.layout.simple_list_item_1, listAngkatan);
                        adapterAngkatan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spAngkatan.setAdapter(adapterAngkatan);
                        adapterAngkatan.notifyDataSetChanged(); // buat fixin isinya dari db
                        spAngkatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (spAngkatan.getSelectedItem() != null){
                                    getTugas(spAngkatan.getSelectedItem().toString());
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        tbSenin = findViewById(R.id.admin_tambah_senin);
                        tbSelasa = findViewById(R.id.admin_tambah_selasa);
                        tbRabu = findViewById(R.id.admin_tambah_rabu);
                        tbKamis = findViewById(R.id.admin_tambah_kamis);
                        tbJumat = findViewById(R.id.admin_tambah_jumat);

                        tbSenin.setOnClickListener(Admin.this);
                        tbSelasa.setOnClickListener(Admin.this);
                        tbRabu.setOnClickListener(Admin.this);
                        tbKamis.setOnClickListener(Admin.this);
                        tbJumat.setOnClickListener(Admin.this);
                        sceneFirst.removeView(v);
                        setUsername("1");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            } else {

                iv = findViewById(R.id.foto);
                tv = findViewById(R.id.ikhlas_tauf);
                greeting = findViewById(R.id.good_morning);
                bnv.getMenu().getItem(2).setChecked(true);
                setJam();

                Glide.with(Admin.this)
                        .load(R.drawable.profil)
                        .into(iv);

            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void getTugas(String data) {
        rvSenin = findViewById(R.id.admin_tugas_rvSenin);
        rvSelasa = findViewById(R.id.admin_tugas_rvSelasa);
        rvRabu = findViewById(R.id.admin_tugas_rvRabu);
        rvKamis = findViewById(R.id.admin_tugas_rvKamis);
        rvJumat = findViewById(R.id.admin_tugas_rvJumat);

        rvSenin.setHasFixedSize(true);
        rvSelasa.setHasFixedSize(true);
        rvRabu.setHasFixedSize(true);
        rvKamis.setHasFixedSize(true);
        rvJumat.setHasFixedSize(true);

        lmSenin = new LinearLayoutManager(getApplicationContext());
        lmSelasa = new LinearLayoutManager(getApplicationContext());
        lmRabu = new LinearLayoutManager(getApplicationContext());
        lmKamis = new LinearLayoutManager(getApplicationContext());
        lmJumat = new LinearLayoutManager(getApplicationContext());

        rvSenin.setLayoutManager(lmSenin);
        rvSelasa.setLayoutManager(lmSelasa);
        rvRabu.setLayoutManager(lmRabu);
        rvKamis.setLayoutManager(lmKamis);
        rvJumat.setLayoutManager(lmJumat);
        loading = ProgressDialog.show(Admin.this,
                null,
                "Harap tunggu...",
                true,
                false);
        db.child("List/Tugas/"+data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgSenin = new ArrayList<>();
                for (DataSnapshot dsSenin : snapshot.child("Senin").getChildren()) {
                    Tugas tugas = dsSenin.getValue(Tugas.class);
                    tgSenin.add(tugas);
                }
                adapter = new AdapterTugasAdmin(tgSenin, Admin.this);
                rvSenin.setAdapter(adapter);

                tgSelasa = new ArrayList<>();
                for (DataSnapshot dsSelasa : snapshot.child("Selasa").getChildren()) {
                    Tugas tugas = dsSelasa.getValue(Tugas.class);
                    tgSelasa.add(tugas);
                }
                adapter = new AdapterTugasAdmin(tgSelasa, Admin.this);
                rvSelasa.setAdapter(adapter);

                tgRabu = new ArrayList<>();
                for (DataSnapshot dsRabu : snapshot.child("Rabu").getChildren()) {
                    Tugas tugas = dsRabu.getValue(Tugas.class);
                    tgRabu.add(tugas);
                }
                adapter = new AdapterTugasAdmin(tgRabu, Admin.this);
                rvRabu.setAdapter(adapter);

                tgKamis = new ArrayList<>();
                for (DataSnapshot dsKamis : snapshot.child("Kamis").getChildren()) {
                    Tugas tugas = dsKamis.getValue(Tugas.class);
                    tgKamis.add(tugas);
                }
                adapter = new AdapterTugasAdmin(tgKamis, Admin.this);
                rvKamis.setAdapter(adapter);

                tgJumat = new ArrayList<>();
                for (DataSnapshot dsJumat : snapshot.child("Jumat").getChildren()) {
                    Tugas tugas = dsJumat.getValue(Tugas.class);
                    tgJumat.add(tugas);
                }
                adapter = new AdapterTugasAdmin(tgJumat, Admin.this);
                rvJumat.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.monitor:
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(0,true);
                }
                break;
            case R.id.tugas_menu:
                if (viewPager.getCurrentItem() != 1) {
                    viewPager.setCurrentItem(1, true);
                }
                break;
            case R.id.config:
                if (viewPager.getCurrentItem() != 2) {
                    viewPager.setCurrentItem(2, true);
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onAdminClick(String nama, int position) {
        Toast.makeText(Admin.this, "Anda mengklik " + nama, Toast.LENGTH_LONG).show();

//        if (db != null){
//                i = new Intent(Admin.this, PilihMatkul.class);
//                i.putExtra("namaSemester", nama);
//
//            startActivity(i);
//            overridePendingTransition(0, 0);
//        }
    }

    @Override
    public void onTugasSeninClick(Tugas tugas, int position) {
        Toast.makeText(Admin.this, "Anda mengklik tugas " + tugas, Toast.LENGTH_LONG).show();
    }

    private void getAdmin() {
        loading = ProgressDialog.show(Admin.this,
                null,
                "Harap tunggu...",
                true,
                false);

        db.child("List/Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAktif = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    String k = ds.getValue(String.class);
                    listAktif.add(k);
                }
                adapter = new AdapterAdmin(listAktif, Admin.this);
                rvAdmin.setAdapter(adapter);
                loading.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        db.child("List/AngkatanAktif").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAktif = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    String k = ds.getValue(String.class);
                    listAktif.add(k);
                }
                adapter = new AdapterAdmin(listAktif, Admin.this);
                rvAngkatanAktif.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.admin_tambah_senin:
                vs = getLayoutInflater().inflate(R.layout.tambahmatkul, null);
                txInputMatkul = vs.findViewById(R.id.textinput);
                txInputMatkul.setHint("Isi nama mata kuliah");
                txInputMatkul.setPlaceholderText("ANSMET");
                txInputMatkul.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                matkul = vs.findViewById(R.id.admin_matkulentry);
                txInputDeskripsi = vs.findViewById(R.id.textinput2);
                txInputDeskripsi.setHint("Isi deskripsi");
                txInputDeskripsi.setPlaceholderText("Laporan 7 Analisis Siklon");
                txInputDeskripsi.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deskripsi = vs.findViewById(R.id.admin_deskripsientry);
                txInputDeadline = vs.findViewById(R.id.textinput3);
                txInputDeadline.setHint("Isi deadline");
                txInputDeadline.setPlaceholderText("20 Juni 2021");
                txInputDeadline.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deadline = vs.findViewById(R.id.admin_deadlineentry);
                tambah = vs.findViewById(R.id.dialog_txt_tambah);
                batal = vs.findViewById(R.id.dialog_txt_batal);

                alert = new AlertDialog.Builder(this);
                alert.setView(vs);
                dialog = alert.create();
                tambah.setOnClickListener(view -> {
                    @Nullable String txm = matkul.getText().toString();
                    @Nullable String txds = deskripsi.getText().toString();
                    @Nullable String txdd = deadline.getText().toString();
                    if (txm.equals("") || txds.equals("") || txdd.equals("")){
                        Toast t = Toast.makeText(getApplicationContext(), "Tidak boleh ada kolom yang kosong", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0,0);
                        t.show();
                    } else {
                        db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Senin").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int ins = 1;
                                while (snapshot.child(String.valueOf(ins)).exists()){
                                    ins++;
                                }
                                x = snapshot.child(String.valueOf(ins)).getKey();
                                System.out.println(ins);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Senin/"+String.valueOf(Integer.parseInt(x))+"/Matkul").setValue(txm);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Senin/"+String.valueOf(Integer.parseInt(x))+"/Deskripsi").setValue(txds);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Senin/"+String.valueOf(Integer.parseInt(x))+"/Deadline").setValue(txdd);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Senin/"+String.valueOf(Integer.parseInt(x))+"/Hari").setValue("Senin");
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Senin/"+String.valueOf(Integer.parseInt(x))+"/Node").setValue(x);
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                batal.setOnClickListener(view -> dialog.dismiss());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

            case R.id.admin_tambah_selasa:
                vs = getLayoutInflater().inflate(R.layout.tambahmatkul, null);
                txInputMatkul = vs.findViewById(R.id.textinput);
                txInputMatkul.setHint("Isi nama mata kuliah");
                txInputMatkul.setPlaceholderText("ANSMET");
                txInputMatkul.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                matkul = vs.findViewById(R.id.admin_matkulentry);
                txInputDeskripsi = vs.findViewById(R.id.textinput2);
                txInputDeskripsi.setHint("Isi deskripsi");
                txInputDeskripsi.setPlaceholderText("Laporan 7 Analisis Siklon");
                txInputDeskripsi.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deskripsi = vs.findViewById(R.id.admin_deskripsientry);
                txInputDeadline = vs.findViewById(R.id.textinput3);
                txInputDeadline.setHint("Isi deadline");
                txInputDeadline.setPlaceholderText("20 Juni 2021");
                txInputDeadline.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deadline = vs.findViewById(R.id.admin_deadlineentry);
                tambah = vs.findViewById(R.id.dialog_txt_tambah);
                batal = vs.findViewById(R.id.dialog_txt_batal);

                alert = new AlertDialog.Builder(this);
                alert.setView(vs);
                dialog = alert.create();
                tambah.setOnClickListener(view -> {
                    @Nullable String txm = matkul.getText().toString();
                    @Nullable String txds = deskripsi.getText().toString();
                    @Nullable String txdd = deadline.getText().toString();
                    if (txm.equals("") || txds.equals("") || txdd.equals("")){
                        Toast t = Toast.makeText(getApplicationContext(), "Tidak boleh ada kolom yang kosong", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0,0);
                        t.show();
                    } else {
                        db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Selasa").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int ins = 1;
                                while (snapshot.child(String.valueOf(ins)).exists()){
                                    ins++;
                                }
                                x = snapshot.child(String.valueOf(ins)).getKey();
                                System.out.println(ins);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Selasa/"+String.valueOf(Integer.parseInt(x))+"/Matkul").setValue(txm);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Selasa/"+String.valueOf(Integer.parseInt(x))+"/Deskripsi").setValue(txds);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Selasa/"+String.valueOf(Integer.parseInt(x))+"/Deadline").setValue(txdd);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Selasa/"+String.valueOf(Integer.parseInt(x))+"/Hari").setValue("Selasa");
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Selasa/"+String.valueOf(Integer.parseInt(x))+"/Node").setValue(x);
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                batal.setOnClickListener(view -> dialog.dismiss());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

                case R.id.admin_tambah_rabu:
                vs = getLayoutInflater().inflate(R.layout.tambahmatkul, null);
                txInputMatkul = vs.findViewById(R.id.textinput);
                txInputMatkul.setHint("Isi nama mata kuliah");
                txInputMatkul.setPlaceholderText("ANSMET");
                txInputMatkul.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                matkul = vs.findViewById(R.id.admin_matkulentry);
                txInputDeskripsi = vs.findViewById(R.id.textinput2);
                txInputDeskripsi.setHint("Isi deskripsi");
                txInputDeskripsi.setPlaceholderText("Laporan 7 Analisis Siklon");
                txInputDeskripsi.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deskripsi = vs.findViewById(R.id.admin_deskripsientry);
                txInputDeadline = vs.findViewById(R.id.textinput3);
                txInputDeadline.setHint("Isi deadline");
                txInputDeadline.setPlaceholderText("20 Juni 2021");
                txInputDeadline.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deadline = vs.findViewById(R.id.admin_deadlineentry);
                tambah = vs.findViewById(R.id.dialog_txt_tambah);
                batal = vs.findViewById(R.id.dialog_txt_batal);

                alert = new AlertDialog.Builder(this);
                alert.setView(vs);
                dialog = alert.create();
                tambah.setOnClickListener(view -> {
                    @Nullable String txm = matkul.getText().toString();
                    @Nullable String txds = deskripsi.getText().toString();
                    @Nullable String txdd = deadline.getText().toString();
                    if (txm.equals("") || txds.equals("") || txdd.equals("")){
                        Toast t = Toast.makeText(getApplicationContext(), "Tidak boleh ada kolom yang kosong", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0,0);
                        t.show();
                    } else {
                        db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Rabu").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int ins = 1;
                                while (snapshot.child(String.valueOf(ins)).exists()){
                                    ins++;
                                }
                                x = snapshot.child(String.valueOf(ins)).getKey();
                                System.out.println(ins);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Rabu/"+String.valueOf(Integer.parseInt(x))+"/Matkul").setValue(txm);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Rabu/"+String.valueOf(Integer.parseInt(x))+"/Deskripsi").setValue(txds);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Rabu/"+String.valueOf(Integer.parseInt(x))+"/Deadline").setValue(txdd);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Rabu/"+String.valueOf(Integer.parseInt(x))+"/Hari").setValue("Rabu");
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Rabu/"+String.valueOf(Integer.parseInt(x))+"/Node").setValue(x);
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                batal.setOnClickListener(view -> dialog.dismiss());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

                case R.id.admin_tambah_kamis:
                vs = getLayoutInflater().inflate(R.layout.tambahmatkul, null);
                txInputMatkul = vs.findViewById(R.id.textinput);
                txInputMatkul.setHint("Isi nama mata kuliah");
                txInputMatkul.setPlaceholderText("ANSMET");
                txInputMatkul.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                matkul = vs.findViewById(R.id.admin_matkulentry);
                txInputDeskripsi = vs.findViewById(R.id.textinput2);
                txInputDeskripsi.setHint("Isi deskripsi");
                txInputDeskripsi.setPlaceholderText("Laporan 7 Analisis Siklon");
                txInputDeskripsi.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deskripsi = vs.findViewById(R.id.admin_deskripsientry);
                txInputDeadline = vs.findViewById(R.id.textinput3);
                txInputDeadline.setHint("Isi deadline");
                txInputDeadline.setPlaceholderText("20 Juni 2021");
                txInputDeadline.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deadline = vs.findViewById(R.id.admin_deadlineentry);
                tambah = vs.findViewById(R.id.dialog_txt_tambah);
                batal = vs.findViewById(R.id.dialog_txt_batal);

                alert = new AlertDialog.Builder(this);
                alert.setView(vs);
                dialog = alert.create();
                tambah.setOnClickListener(view -> {
                    @Nullable String txm = matkul.getText().toString();
                    @Nullable String txds = deskripsi.getText().toString();
                    @Nullable String txdd = deadline.getText().toString();
                    if (txm.equals("") || txds.equals("") || txdd.equals("")){
                        Toast t = Toast.makeText(getApplicationContext(), "Tidak boleh ada kolom yang kosong", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0,0);
                        t.show();
                    } else {
                        db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Kamis").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int ins = 1;
                                while (snapshot.child(String.valueOf(ins)).exists()){
                                    ins++;
                                }
                                x = snapshot.child(String.valueOf(ins)).getKey();
                                System.out.println(ins);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Kamis/"+String.valueOf(Integer.parseInt(x))+"/Matkul").setValue(txm);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Kamis/"+String.valueOf(Integer.parseInt(x))+"/Deskripsi").setValue(txds);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Kamis/"+String.valueOf(Integer.parseInt(x))+"/Deadline").setValue(txdd);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Kamis/"+String.valueOf(Integer.parseInt(x))+"/Hari").setValue("Kamis");
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Kamis/"+String.valueOf(Integer.parseInt(x))+"/Node").setValue(x);
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                batal.setOnClickListener(view -> dialog.dismiss());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

                case R.id.admin_tambah_jumat:
                vs = getLayoutInflater().inflate(R.layout.tambahmatkul, null);
                txInputMatkul = vs.findViewById(R.id.textinput);
                txInputMatkul.setHint("Isi nama mata kuliah");
                txInputMatkul.setPlaceholderText("ANSMET");
                txInputMatkul.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                matkul = vs.findViewById(R.id.admin_matkulentry);
                txInputDeskripsi = vs.findViewById(R.id.textinput2);
                txInputDeskripsi.setHint("Isi deskripsi");
                txInputDeskripsi.setPlaceholderText("Laporan 7 Analisis Siklon");
                txInputDeskripsi.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deskripsi = vs.findViewById(R.id.admin_deskripsientry);
                txInputDeadline = vs.findViewById(R.id.textinput3);
                txInputDeadline.setHint("Isi deadline");
                txInputDeadline.setPlaceholderText("20 Juni 2021");
                txInputDeadline.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
                deadline = vs.findViewById(R.id.admin_deadlineentry);
                tambah = vs.findViewById(R.id.dialog_txt_tambah);
                batal = vs.findViewById(R.id.dialog_txt_batal);

                alert = new AlertDialog.Builder(this);
                alert.setView(vs);
                dialog = alert.create();
                tambah.setOnClickListener(view -> {
                    @Nullable String txm = matkul.getText().toString();
                    @Nullable String txds = deskripsi.getText().toString();
                    @Nullable String txdd = deadline.getText().toString();
                    if (txm.equals("") || txds.equals("") || txdd.equals("")){
                        Toast t = Toast.makeText(getApplicationContext(), "Tidak boleh ada kolom yang kosong", Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0,0);
                        t.show();
                    } else {
                        db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Jumat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int ins = 1;
                                while (snapshot.child(String.valueOf(ins)).exists()){
                                    ins++;
                                }
                                x = snapshot.child(String.valueOf(ins)).getKey();
                                System.out.println(ins);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Jumat/"+String.valueOf(Integer.parseInt(x))+"/Matkul").setValue(txm);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Jumat/"+String.valueOf(Integer.parseInt(x))+"/Deskripsi").setValue(txds);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Jumat/"+String.valueOf(Integer.parseInt(x))+"/Deadline").setValue(txdd);
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Jumat/"+String.valueOf(Integer.parseInt(x))+"/Hari").setValue("Jumat");
                                db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/Jumat/"+String.valueOf(Integer.parseInt(x))+"/Node").setValue(x);
                                dialog.dismiss();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

                batal.setOnClickListener(view -> dialog.dismiss());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDelete(Tugas tugas, int position) {
        if (db != null){
            db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/"+tugas.getHari()+"/"+tugas.getNode()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast t = Toast.makeText(Admin.this, "Tugas berhasil dihapus.", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0,0);
                    t.show();
                }
            });

        }
    }

    @Override
    public void onEdit(Tugas tugas, int position) {
        if (db != null){
            vs = getLayoutInflater().inflate(R.layout.editmatkul, null);
            txInputMatkul = vs.findViewById(R.id.textinput);
            txInputMatkul.setHint("Isi nama mata kuliah");
            txInputMatkul.setPlaceholderText("ANSMET");
            txInputMatkul.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
            matkul = vs.findViewById(R.id.admin_matkulentry);
            matkul.setText(tugas.getMatkul());
            txInputDeskripsi = vs.findViewById(R.id.textinput2);
            txInputDeskripsi.setHint("Isi deskripsi");
            txInputDeskripsi.setPlaceholderText("Laporan 7 Analisis Siklon");
            txInputDeskripsi.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
            deskripsi = vs.findViewById(R.id.admin_deskripsientry);
            deskripsi.setText(tugas.getDeskripsi());
            txInputDeadline = vs.findViewById(R.id.textinput3);
            txInputDeadline.setHint("Isi deadline");
            txInputDeadline.setPlaceholderText("20 Juni 2021");
            txInputDeadline.setPlaceholderTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruAtas)));
            deadline = vs.findViewById(R.id.admin_deadlineentry);
            deadline.setText(tugas.getDeadline());
            edit = vs.findViewById(R.id.dialog_txt_edit);
            batal = vs.findViewById(R.id.dialog_txt_batal);

            alert = new AlertDialog.Builder(this);
            alert.setView(vs);
            dialog = alert.create();
            edit.setOnClickListener(view -> {
                @Nullable String txm = matkul.getText().toString();
                @Nullable String txds = deskripsi.getText().toString();
                @Nullable String txdd = deadline.getText().toString();
                if (txm.equals("") || txds.equals("") || txdd.equals("")){
                    Toast t = Toast.makeText(getApplicationContext(), "Tidak boleh ada kolom yang kosong", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0,0);
                    t.show();
                } else {
                    db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/"+tugas.getHari()+"/"+tugas.getNode()+"/Matkul").setValue(txm);
                    db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/"+tugas.getHari()+"/"+tugas.getNode()+"/Deskripsi").setValue(txds);
                    db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/"+tugas.getHari()+"/"+tugas.getNode()+"/Deadline").setValue(txdd);
                    db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/"+tugas.getHari()+"/"+tugas.getNode()+"/Hari").setValue(tugas.getHari());
                    db.child("List/Tugas/"+spAngkatan.getSelectedItem().toString()+"/"+tugas.getHari()+"/"+tugas.getNode()+"/Node").setValue(tugas.getNode()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast t = Toast.makeText(getApplicationContext(), "Data tugas berhasil diubah.", Toast.LENGTH_LONG);
                            t.setGravity(Gravity.CENTER, 0,0);
                            t.show();
                        }
                    });
                    dialog.dismiss();
                }
            });

            batal.setOnClickListener(view -> dialog.dismiss());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        }
    }

    public class viewAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public viewAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            return super.instantiateItem(container, position);
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            View v = (View) object;
            container.removeView(v);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        logout();
    }

    public void logout(){
        alert = new AlertDialog.Builder(this);
        alert
                .setTitle("Keluar")
                .setMessage("Apakah anda ingin Keluar?")
                .setCancelable(false)
                .setPositiveButton("Keluar", (dialog, which) -> {
                    db.child("sedangAktif").child(user).removeValue();
                    sessions.logoutUser();
                    mAuth.signOut();
                    finish();
                    overridePendingTransition(0,0);
                }).setNegativeButton("Batal", (dialog, which) -> dialog.cancel()).create().show();
    }
}