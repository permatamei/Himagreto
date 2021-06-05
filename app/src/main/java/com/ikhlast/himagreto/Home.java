package com.ikhlast.himagreto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.ikhlast.himagreto.Adapters.AdapterHome;
import com.ikhlast.himagreto.Adapters.AdapterTugas;
import com.ikhlast.himagreto.Models.Semester;
import com.ikhlast.himagreto.Models.Tugas;

import java.util.ArrayList;
import java.util.Objects;

import static android.view.WindowManager.*;

public class Home extends AppCompatActivity implements AdapterHome.DataListener, AdapterTugas.DataListener, View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db;
    private RecyclerView rv, rv1, rvSenin, rvSelasa, rvRabu, rvKamis, rvJumat;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager, layoutManager1, lmSenin, lmSelasa, lmRabu, lmKamis, lmJumat;
    private ArrayList<Semester> semester = new ArrayList<>();
    private ArrayList<Tugas> tgSenin, tgSelasa, tgRabu, tgKamis, tgJumat;
    private ArrayList<String> listAktif;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user;

    private ImageView iv;
    private TextView tv;
    private EditText nama, ttl, domisili, emailIPB, hp;
    private TextInputLayout txEmail;
    private Button ubahProfil;

    private MaterialCardView mcvNama, mcvDomisili, mcvHp, mcvHbd;

    private ScrollView sv1, sv2;
    private View vFrame, v;

    AlertDialog.Builder alert;
    Sessions sessions;

    BottomNavigationView bnv;
    ViewPager viewPager;
    viewAdapter vpAdapter;
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
//        View d = getWindow().getDecorView();
//        d.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.home);

        alert = new AlertDialog.Builder(this);
        sessions = new Sessions(getApplicationContext());
        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");

        iv = findViewById(R.id.foto);
        tv = findViewById(R.id.ikhlas_tauf);
        if (mUser.getDisplayName() != null && !mUser.getDisplayName().equals("")){
            tv.setText(mUser.getDisplayName());
        } else {
            tv.setText(user.toUpperCase());
        }

        Glide.with(Home.this)
                .load(R.drawable.gua)
                .into(iv);

        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(0).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.view_pager_home);

        layouts = new int[]{
                R.layout.home_home,
                R.layout.home_tugas,
                R.layout.home_profil
        };

        vpAdapter = new viewAdapter();
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(pagelistener);

        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        sceneFirst = findViewById(R.id.design_home);
        v = inflater.inflate(layouts[0], sceneFirst, false);
        sceneFirst.addView(v);

        rv1 = findViewById(R.id.admin_recycler_home);
        rv1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getApplicationContext());
        rv1.setLayoutManager(layoutManager1);

        vFrame = findViewById(R.id.group_10);
        sceneRoot = findViewById(R.id.scene_root);
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.scene1, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, R.layout.scene2, this);

        getSemester();
    }

    ViewPager.OnPageChangeListener pagelistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                tFade = new Fade();
                vFrame.setBackground(ContextCompat.getDrawable(Home.this, R.drawable.frame));
                TransitionManager.go(scene1, tFade);
                iv = findViewById(R.id.foto);
                tv = findViewById(R.id.ikhlas_tauf);
                if (mUser.getDisplayName() != null && !mUser.getDisplayName().equals("")){
                    tv.setText(mUser.getDisplayName());
                } else {
                    tv.setText(user.toUpperCase());
                }

                Glide.with(Home.this)
                        .load(R.drawable.gua)
                        .into(iv);

                bnv.getMenu().getItem(0).setChecked(true);
                rv1 = findViewById(R.id.admin_recycler_home);
                rv1.setHasFixedSize(true);
                layoutManager1 = new LinearLayoutManager(getApplicationContext());
                rv1.setLayoutManager(layoutManager1);
                getSemester();
                sceneFirst.removeView(v);
            } else if (position == 1) {
                //ini buat tugas
                tFade = new Fade();
                vFrame.setBackground(ContextCompat.getDrawable(Home.this, R.drawable.frame));
                TransitionManager.go(scene1, tFade);
                iv = findViewById(R.id.foto);
                tv = findViewById(R.id.ikhlas_tauf);
                if (mUser.getDisplayName() != null && !mUser.getDisplayName().equals("")){
                    tv.setText(mUser.getDisplayName());
                } else {
                    tv.setText(user.toUpperCase());
                }

                Glide.with(Home.this)
                        .load(R.drawable.gua)
                        .into(iv);

                bnv.getMenu().getItem(1).setChecked(true);
                rvSenin = findViewById(R.id.home_tugas_rvSenin);
                rvSelasa = findViewById(R.id.home_tugas_rvSelasa);
                rvRabu = findViewById(R.id.home_tugas_rvRabu);
                rvKamis = findViewById(R.id.home_tugas_rvKamis);
                rvJumat = findViewById(R.id.home_tugas_rvJumat);

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

                getTugas();
                sceneFirst.removeView(v);
            } else {
                sceneFirst.removeView(v);
                sv1 = findViewById(R.id.sv1);
                tFade = new Fade();
                vFrame.setBackground(ContextCompat.getDrawable(Home.this, R.drawable.frame_profil));
                TransitionManager.go(scene2, tFade);
                iv = findViewById(R.id.foto);
                tv = findViewById(R.id.ikhlas_tauf);
                bnv.getMenu().getItem(2).setChecked(true);
                if (mUser.getDisplayName() != null && !mUser.getDisplayName().equals("")){
                    tv.setText(mUser.getDisplayName());
                } else {
                    tv.setText(user.toUpperCase());
                }

                Glide.with(Home.this)
                        .load(R.drawable.gua)
                        .into(iv);

                mcvNama = findViewById(R.id.card_profil_nama);
                mcvDomisili = findViewById(R.id.card_profil_domisili);
                mcvHp  = findViewById(R.id.card_profil_hp);
                mcvHbd = findViewById(R.id.card_profil_hbd);
                mcvNama.setOnClickListener(Home.this);
                mcvDomisili.setOnClickListener(Home.this);
                mcvHp.setOnClickListener(Home.this);
                mcvHbd.setOnClickListener(Home.this);
//                sv2 = findViewById(R.id.sv2);
                //ini buat profil
//                if (mUser.getDisplayName() != null && mUser.getDisplayName().equals("")){
//
//                    txEmail = findViewById(R.id.textinputemail);
//                    txEmail.setSuffixText("@apps.ipb.ac.id");
//                    txEmail.setSuffixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruTeks)));
//                    nama = findViewById(R.id.profil_nameentry);
//                    ttl = findViewById(R.id.profil_ttlentry);
//                    domisili = findViewById(R.id.profil_domisilientry);
//                    emailIPB = findViewById(R.id.profil_emailentry);
//                    hp = findViewById(R.id.profil_hpentry);
//                    ubahProfil = findViewById(R.id.profil_btsubmit);
//                } else {
//                    sv1.setVisibility(View.GONE);
//                }

//                rv2 = findViewById(R.id.admin_recycler_konfirmasi);
//                rv2.setHasFixedSize(true);
//                layoutManager2 = new LinearLayoutManager(getApplicationContext());
//                rv2.setLayoutManager(layoutManager2);
                //TODO: GANTI KE SET ITEM RVNYA KALO PROFIL PAKE RV
//                getConf();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_materi:
                if (viewPager.getCurrentItem() != 0) {
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.tugas_menu:
                if (viewPager.getCurrentItem() != 1) {
                    viewPager.setCurrentItem(1);
                }
                break;
            case R.id.profil_menu:
                if (viewPager.getCurrentItem() != 2) {
                    viewPager.setCurrentItem(2);
                }
                break;
        }
        return true;
    }

    @Override
    public void onSemesterClick(Semester smt, int position) {
        Toast.makeText(Home.this, "Anda mengklik semester " + smt, Toast.LENGTH_LONG).show();
        //TODO: SAMBUNGIN KE DB
//        if (db != null){
//            if (viewPager.getCurrentItem() == 0) {
//                i = new Intent(Home.this, Details.class);
//                i.putExtra(CODE, barang.getUser());
//            } else if (viewPager.getCurrentItem() == 1){
//                i = new Intent(Home.this, DetailsBerjalan.class);
//                i.putExtra(CODE, barang.getUser());
//            }
//            startActivity(i);
//            overridePendingTransition(0, 0);
//        }
    }

    @Override
    public void onTugasSeninClick(Tugas tugas, int position) {
        Toast.makeText(Home.this, "Anda mengklik tugas " + tugas, Toast.LENGTH_LONG).show();
    }

    private void getSemester() {
        loading = ProgressDialog.show(Home.this,
                null,
                "Harap tunggu...",
                true,
                false);

        db.child("List/Semester").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                semester = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Semester smt = ds.getValue(Semester.class);
                    semester.add(smt);
                }
                AdapterHome adapterHome = new AdapterHome(semester, Home.this);
                rv1.setAdapter(adapterHome);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getTugas() {
        loading = ProgressDialog.show(Home.this,
                null,
                "Harap tunggu...",
                true,
                false);
        db.child("List/AngkatanAktif").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAktif = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    String k = ds.getValue(String.class);
                    listAktif.add(k);
                }
                if (user.contains(listAktif.get(0).toLowerCase())) {
                    db.child("List/Tugas/"+listAktif.get(0)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            tgSenin = new ArrayList<>();
                            for (DataSnapshot dsSenin : snapshot.child("Senin").getChildren()) {
                                Tugas tugas = dsSenin.getValue(Tugas.class);
                                tgSenin.add(tugas);
                            }
                            adapter = new AdapterTugas(tgSenin, Home.this);
                            rvSenin.setAdapter(adapter);

                            tgSelasa = new ArrayList<>();
                            for (DataSnapshot dsSelasa : snapshot.child("Selasa").getChildren()) {
                                Tugas tugas = dsSelasa.getValue(Tugas.class);
                                tgSelasa.add(tugas);
                            }
                            adapter = new AdapterTugas(tgSelasa, Home.this);
                            rvSelasa.setAdapter(adapter);

                            tgRabu = new ArrayList<>();
                            for (DataSnapshot dsRabu : snapshot.child("Rabu").getChildren()) {
                                Tugas tugas = dsRabu.getValue(Tugas.class);
                                tgRabu.add(tugas);
                            }
                            adapter = new AdapterTugas(tgRabu, Home.this);
                            rvRabu.setAdapter(adapter);

                            tgKamis = new ArrayList<>();
                            for (DataSnapshot dsKamis : snapshot.child("Kamis").getChildren()) {
                                Tugas tugas = dsKamis.getValue(Tugas.class);
                                tgKamis.add(tugas);
                            }
                            adapter = new AdapterTugas(tgKamis, Home.this);
                            rvKamis.setAdapter(adapter);

                            tgJumat = new ArrayList<>();
                            for (DataSnapshot dsJumat : snapshot.child("Jumat").getChildren()) {
                                Tugas tugas = dsJumat.getValue(Tugas.class);
                                tgJumat.add(tugas);
                            }
                            adapter = new AdapterTugas(tgJumat, Home.this);
                            rvJumat.setAdapter(adapter);
                            loading.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (user.contains(listAktif.get(1).toLowerCase())) {
                    db.child("List/Tugas/"+listAktif.get(1)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            tgSenin = new ArrayList<>();
                            for (DataSnapshot dsSenin : snapshot.child("Senin").getChildren()) {
                                Tugas tugas = dsSenin.getValue(Tugas.class);
                                tgSenin.add(tugas);
                            }
                            adapter = new AdapterTugas(tgSenin, Home.this);
                            rvSenin.setAdapter(adapter);

                            tgSelasa = new ArrayList<>();
                            for (DataSnapshot dsSelasa : snapshot.child("Selasa").getChildren()) {
                                Tugas tugas = dsSelasa.getValue(Tugas.class);
                                tgSelasa.add(tugas);
                            }
                            adapter = new AdapterTugas(tgSelasa, Home.this);
                            rvSelasa.setAdapter(adapter);

                            tgRabu = new ArrayList<>();
                            for (DataSnapshot dsRabu : snapshot.child("Rabu").getChildren()) {
                                Tugas tugas = dsRabu.getValue(Tugas.class);
                                tgRabu.add(tugas);
                            }
                            adapter = new AdapterTugas(tgRabu, Home.this);
                            rvRabu.setAdapter(adapter);

                            tgKamis = new ArrayList<>();
                            for (DataSnapshot dsKamis : snapshot.child("Kamis").getChildren()) {
                                Tugas tugas = dsKamis.getValue(Tugas.class);
                                tgKamis.add(tugas);
                            }
                            adapter = new AdapterTugas(tgKamis, Home.this);
                            rvKamis.setAdapter(adapter);

                            tgJumat = new ArrayList<>();
                            for (DataSnapshot dsJumat : snapshot.child("Jumat").getChildren()) {
                                Tugas tugas = dsJumat.getValue(Tugas.class);
                                tgJumat.add(tugas);
                            }
                            adapter = new AdapterTugas(tgJumat, Home.this);
                            rvJumat.setAdapter(adapter);
                            loading.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                } else if (user.contains(listAktif.get(2).toLowerCase())) {
                    db.child("List/Tugas/"+listAktif.get(2)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            tgSenin = new ArrayList<>();
                            for (DataSnapshot dsSenin : snapshot.child("Senin").getChildren()) {
                                Tugas tugas = dsSenin.getValue(Tugas.class);
                                tgSenin.add(tugas);
                            }
                            adapter = new AdapterTugas(tgSenin, Home.this);
                            rvSenin.setAdapter(adapter);

                            tgSelasa = new ArrayList<>();
                            for (DataSnapshot dsSelasa : snapshot.child("Selasa").getChildren()) {
                                Tugas tugas = dsSelasa.getValue(Tugas.class);
                                tgSelasa.add(tugas);
                            }
                            adapter = new AdapterTugas(tgSelasa, Home.this);
                            rvSelasa.setAdapter(adapter);

                            tgRabu = new ArrayList<>();
                            for (DataSnapshot dsRabu : snapshot.child("Rabu").getChildren()) {
                                Tugas tugas = dsRabu.getValue(Tugas.class);
                                tgRabu.add(tugas);
                            }
                            adapter = new AdapterTugas(tgRabu, Home.this);
                            rvRabu.setAdapter(adapter);

                            tgKamis = new ArrayList<>();
                            for (DataSnapshot dsKamis : snapshot.child("Kamis").getChildren()) {
                                Tugas tugas = dsKamis.getValue(Tugas.class);
                                tgKamis.add(tugas);
                            }
                            adapter = new AdapterTugas(tgKamis, Home.this);
                            rvKamis.setAdapter(adapter);

                            tgJumat = new ArrayList<>();
                            for (DataSnapshot dsJumat : snapshot.child("Jumat").getChildren()) {
                                Tugas tugas = dsJumat.getValue(Tugas.class);
                                tgJumat.add(tugas);
                            }
                            adapter = new AdapterTugas(tgJumat, Home.this);
                            rvJumat.setAdapter(adapter);
                            loading.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card_profil_nama:
                alert = new AlertDialog.Builder(this, R.layout.layouteditoprofil);
                alert.setMessage("Silahkan isi nama")
                        .setTitle("Nama")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText etn = findViewById(R.id.profil_nameentrys);
                                String entt = etn.getText().toString();
                                Toast.makeText(Home.this, entt, Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        })
                        .show();
                break;
            case R.id.card_profil_domisili:
                break;
            case R.id.card_profil_hbd:
                break;
            case R.id.card_profil_hp:
                break;
        }
    }

    public class viewAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public viewAdapter() {
        }

        ;

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
        alert
                .setTitle("Keluar")
                .setMessage("Apakah anda ingin Keluar?")
                .setCancelable(false)
                .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.child("sedangAktif").child(user).removeValue();
                        sessions.logoutUser();
                        mAuth.signOut();
                        finish();
                        overridePendingTransition(0,0);
                    }
                }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create().show();
    }
}