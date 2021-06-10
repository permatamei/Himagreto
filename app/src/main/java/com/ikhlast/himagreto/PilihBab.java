package com.ikhlast.himagreto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.ikhlast.himagreto.Adapters.AdapterPilihBab;
import com.ikhlast.himagreto.Adapters.AdapterPilihMatkul;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PilihBab extends AppCompatActivity implements AdapterPilihBab.DataListener {

    private ArrayList<String> data;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm;

    private String intentBundle;
    private String[] x;

    private DatabaseReference db;
    private FirebaseStorage fs;
    private StorageReference sr;
    private ProgressDialog loading;
    private AlertDialog.Builder alert;

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_bab);

        db = FirebaseDatabase.getInstance().getReference();
        fs = FirebaseStorage.getInstance();
        rv = findViewById(R.id.rv_pilihbab);
        wv = findViewById(R.id.webview);
        wv.getSettings().setDomStorageEnabled(true);
//        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient());
        wv.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                Toast.makeText(PilihBab.this, "Download start", Toast.LENGTH_LONG).show();
            }
        });
        rv.setHasFixedSize(true);
        lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        Bundle b = getIntent().getExtras();
        if (b != null){
            ins(b);
        }
    }

    public void ins(Bundle bundle){
        loading = ProgressDialog.show(PilihBab.this,
                null,
                "Harap tunggu...",
                true,
                false);
        intentBundle = bundle.getString("namaMatkul");
        x = intentBundle.split("%%");
        db.child("List/File/"+x[0]+"/"+x[1]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    data.add(ds.getKey());
                }

                adapter = new AdapterPilihBab(data, PilihBab.this);
                rv.setAdapter(adapter);
                loading.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBabClick(String barang, int position) {
        Toast.makeText(this, "File Bab "+barang+" akan didownload melalui website.", Toast.LENGTH_LONG).show();
        loading = ProgressDialog.show(PilihBab.this,
                null,
                "Mendownload...",
                true,
                false);
        db.child("List/File/"+x[0]+"/"+x[1]+"/"+barang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sr = fs.getReferenceFromUrl(snapshot.getValue(String.class).replace("\"", ""));
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(snapshot.getValue(String.class)));
                startActivity(i);
                loading.dismiss();
                overridePendingTransition(0,0);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
        overridePendingTransition(0,0);
    }
}