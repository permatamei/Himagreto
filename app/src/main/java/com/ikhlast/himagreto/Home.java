package com.ikhlast.himagreto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
//TODO: TAMBAHIN LAYOUT BUAT TAMPILAN RECYCLER VIEW HOME DAN TUGAS
public class Home extends AppCompatActivity implements AdapterHome.DataListener, AdapterTugas.DataListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db;
    private RecyclerView rv, rv1, rvSenin, rvSelasa, rvRabu, rvKamis, rvJumat;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager, layoutManager1, lmSenin, lmSelasa, lmRabu, lmKamis, lmJumat;
    private ArrayList<Semester> semester = new ArrayList<>();
    private ArrayList<Tugas> tgSenin, tgSelasa, tgRabu, tgKamis, tgJumat;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user;

    AlertDialog.Builder alert;
    Sessions sessions;

    BottomNavigationView bnv;
    ViewPager viewPager;
    viewAdapter vpAdapter;
    int[] layouts;
    LinearLayout lb;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        int uiOpt = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= 14) {
            uiOpt ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        // Status bar hiding: Backwards compatible to Jellybean
        if (Build.VERSION.SDK_INT >= 16) {
            uiOpt ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        }

        // Immersive mode: Backward compatible to KitKat.
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
        // Sticky immersive mode differs in that it makes the navigation and status bars
        // semi-transparent, and the UI flag does not get cleared when the user interacts with
        // the screen.
        if (Build.VERSION.SDK_INT >= 18) {
            uiOpt ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }
        getWindow().getDecorView().setSystemUiVisibility(uiOpt);

        sessions = new Sessions(getApplicationContext());
        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");

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

        rv = findViewById(R.id.admin_recycler_home_sementara);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        getSemester(1);
    }

    ViewPager.OnPageChangeListener pagelistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                bnv.getMenu().getItem(0).setChecked(true);
                rv1 = findViewById(R.id.admin_recycler_home);
                rv1.setHasFixedSize(true);
                layoutManager1 = new LinearLayoutManager(getApplicationContext());
                rv1.setLayoutManager(layoutManager1);
                //TODO: GANTI BUAT SET ITEM KE RV
                getSemester(2);
                rv.setVisibility(View.GONE);
            } else if (position == 1) {
                //ini buat tugas
                bnv.getMenu().getItem(1).setChecked(true);
                //TODO: BENERIN CAST KE RV DI LAYOUT TUGAS
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


                //TODO: GANTI BUAT SET ITEM KE RV
                getTugas();
                rv.setVisibility(View.GONE);
            } else {
                //ini buat profil
                bnv.getMenu().getItem(2).setChecked(true);
                //TODO: BIKIN LAYOUT PROFIL WKWK
//                rv2 = findViewById(R.id.admin_recycler_konfirmasi);
//                rv2.setHasFixedSize(true);
//                layoutManager2 = new LinearLayoutManager(getApplicationContext());
//                rv2.setLayoutManager(layoutManager2);
                //TODO: GANTI KE SET ITEM RVNYA KALO PROFIL PAKE RV
//                getConf();
                rv.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu:
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

    private void getSemester(int num) {
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
                //        semester = new ArrayList<>();
//        semester.addAll(SemesterData.getListData());

                AdapterHome adapterHome = new AdapterHome(semester, Home.this);
                if (num == 1) {
                    rv.setAdapter(adapterHome);
                } else {
                    rv1.setAdapter(adapterHome);
                }
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
        if (user.contains("g2417")) {
            db.child("List/Tugas/54").addValueEventListener(new ValueEventListener() {
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
        } else if (user.contains("g2418")) {
            db.child("List/Tugas/55").addValueEventListener(new ValueEventListener() {
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
        } else if (user.contains("g2419")) {
            db.child("List/Tugas/56").addValueEventListener(new ValueEventListener() {
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
}