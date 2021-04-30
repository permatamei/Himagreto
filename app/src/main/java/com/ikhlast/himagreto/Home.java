package com.ikhlast.himagreto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
//TODO: BENERIN PAKE VIEWPAGER ATAU BNV?
public class Home extends AppCompatActivity implements AdapterHome.DataListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference db;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Semester> semester;

    private ProgressDialog loading;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String user;

    AlertDialog.Builder alert;
    Sessions sessions;

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        sessions = new Sessions(getApplicationContext());
        bnv = findViewById(R.id.nav_home);
        bnv.getMenu().getItem(0).setChecked(true);
        bnv.setOnNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");

        rv = (RecyclerView) findViewById(R.id.list_home);
        rv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        db = FirebaseDatabase.getInstance().getReference();

        loading = ProgressDialog.show(Home.this,
                null,
                "Harap tunggu...",
                true,
                false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu:
                break;
            case R.id.tugas_menu:
                break;
            case R.id.profil_menu:
                break;
        }
        return false;
    }

    @Override
    public void onSemesterClick(Semester smt, int position) {
        Toast.makeText(Home.this, "Anda mengklik semester "+smt, Toast.LENGTH_LONG).show();
    }
}