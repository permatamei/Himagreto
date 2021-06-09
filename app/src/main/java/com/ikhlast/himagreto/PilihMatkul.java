package com.ikhlast.himagreto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ikhlast.himagreto.Adapters.AdapterPilihMatkul;

import java.util.ArrayList;

public class PilihMatkul extends AppCompatActivity implements AdapterPilihMatkul.DataListener {

    private ArrayList<String> data;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm;

    private String intentBundle;

    private DatabaseReference db;
    private ProgressDialog loading;
    private AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_matkul);

        db = FirebaseDatabase.getInstance().getReference();
        rv = findViewById(R.id.rv_pilihmatkul);
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        Bundle b = getIntent().getExtras();
        if (b != null){
            ins(b);
        }
    }

    public void ins(Bundle bundle){
        loading = ProgressDialog.show(PilihMatkul.this,
                null,
                "Harap tunggu...",
                true,
                false);
        intentBundle = bundle.getString("namaSemester");
        db.child("List/File/"+intentBundle).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    data.add(ds.getKey());
                }

                adapter = new AdapterPilihMatkul(data, PilihMatkul.this);
                rv.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onMatkulClick(String barang, int position) {
//        Toast.makeText(this, "anda memilih "+barang, Toast.LENGTH_LONG).show();
        if (db != null) {
            Intent i = new Intent(PilihMatkul.this, PilihBab.class);
            i.putExtra("namaMatkul", intentBundle+"%%"+barang);

            startActivity(i);
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0,0);
    }
}