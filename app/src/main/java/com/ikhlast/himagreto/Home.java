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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//TODO: TAMBAHIN LAYOUT BUAT TAMPILAN RECYCLER VIEW HOME DAN TUGAS
public class Home extends AppCompatActivity implements AdapterHome.DataListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db;
    private RecyclerView rv, rv1, rv2;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager, layoutManager1, layoutManager2;
    private ArrayList<Semester> semester;

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

        sessions = new Sessions(getApplicationContext());
        db = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");

        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(0).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);

        viewPager = findViewById(R.id.view_pager_home);
        //TODO: BIKIN LAYOUT RECYCLER VIEW UNTUK SEMUA HALAMAN HOME, harinya dipermanenin aja.
        layouts = new int[] {
                R.layout.home_home,
                R.layout.home_tugas,
                R.layout.profil
        };

        vpAdapter = new viewAdapter();
        viewPager.setAdapter(vpAdapter);
        viewPager.addOnPageChangeListener(pagelistener);

        rv = findViewById(R.id.list_home_sementara);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(layoutManager);
        //do set rv

        loading = ProgressDialog.show(Home.this,
                null,
                "Harap tunggu...",
                true,
                false);
    }

    ViewPager.OnPageChangeListener pagelistener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0){
                bnv.getMenu().getItem(0).setChecked(true);
                //TODO: BENERIN CAST
                rv1 = findViewById(R.id.admin_recycler_konfirmasi);
                rv1.setHasFixedSize(true);
                layoutManager1 = new LinearLayoutManager(getApplicationContext());
                rv1.setLayoutManager(layoutManager1);
                //TODO: GANTI BUAT SET ITEM KE RV
//                getConf();
                rv.setVisibility(View.GONE);
            } else if (position == 1){
                //ini buat tugas
                bnv.getMenu().getItem(1).setChecked(true);
                //TODO: BENERIN CAST KE RV DI LAYOUT TUGAS
                rv2 = findViewById(R.id.admin_recycler_konfirmasi);
                rv2.setHasFixedSize(true);
                layoutManager2 = new LinearLayoutManager(getApplicationContext());
                rv2.setLayoutManager(layoutManager2);
                //TODO: GANTI BUAT SET ITEM KE RV
//                getConf();
                rv.setVisibility(View.GONE);
            } else {
                //ini buat profil
                bnv.getMenu().getItem(2).setChecked(true);
                //TODO: BIKIN LAYOUT PROFIL WKWK
                rv2 = findViewById(R.id.admin_recycler_konfirmasi);
                rv2.setHasFixedSize(true);
                layoutManager2 = new LinearLayoutManager(getApplicationContext());
                rv2.setLayoutManager(layoutManager2);
                //TODO: GANTI KE SET ITEM RVNYA KALO PROFIL PAKE RV
//                getConf();
                rv.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public class viewAdapter extends PagerAdapter {
        private LayoutInflater inflater;

        public viewAdapter(){};

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu:
                if (viewPager.getCurrentItem() != 0){
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.tugas_menu:
                if (viewPager.getCurrentItem() != 1){
                    viewPager.setCurrentItem(1);
                }
                break;
            case R.id.profil_menu:
                if (viewPager.getCurrentItem() != 2){
                    viewPager.setCurrentItem(2);
                }
                break;
        }
        return true;
    }

    @Override
    public void onSemesterClick(Semester smt, int position) {
        Toast.makeText(Home.this, "Anda mengklik semester "+smt, Toast.LENGTH_LONG).show();
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
}