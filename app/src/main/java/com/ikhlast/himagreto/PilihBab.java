package com.ikhlast.himagreto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_bab);

        db = FirebaseDatabase.getInstance().getReference();
        fs = FirebaseStorage.getInstance();
        rv = findViewById(R.id.rv_pilihbab);
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
        Toast.makeText(this, "anda memilih "+barang, Toast.LENGTH_LONG).show();
        db.child("List/File/"+x[0]+"/"+x[1]+"/"+barang).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sr = fs.getReferenceFromUrl(snapshot.getValue(String.class).replace("\"", ""));
//                Toast.makeText(PilihBab.this, "anda memilih "+sr, Toast.LENGTH_LONG).show();
//                sr.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> Toast.makeText(PilihBab.this, "File berhasil didownload", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Toast.makeText(PilihBab.this, "File gagal didownload", Toast.LENGTH_LONG).show());
                //todo
                File localf = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Himagreto");
                Toast.makeText(PilihBab.this, "anda memilih "+localf.getAbsolutePath(), Toast.LENGTH_LONG).show();
                try {
                    localf = File.createTempFile("Himagreto-","");
                    sr.getFile(localf).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull FileDownloadTask.TaskSnapshot snapshot) {
                            double prog = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());

                        }
                    })
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(PilihBab.this, "File berhasil didownload", Toast.LENGTH_LONG).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println(e);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
//Toast.makeText(PilihBab.this, "File berhasil didownload", Toast.LENGTH_LONG).show();
//            Toast.makeText(PilihBab.this, "File gagal didownload", Toast.LENGTH_LONG).show();
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}