package com.ikhlast.himagreto;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout txNim, txPass;
    EditText nim, password;
    Button masuk;
    String u, p, nick, tanggal;
    AlertDialog.Builder alert;
    Sessions session;
    ProgressDialog loading;
    DateFormat time;
    Animation animMove;
    LinearLayout lp;
    ArrayList<String> listAdmin;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tanggal = time.getDateTimeInstance().format(new Date());

        txNim = findViewById(R.id.textinput);
        txPass = findViewById(R.id.textinputpass);
//        txNim.setSuffixText("@himagreto-ipb.web.app");
//        txNim.setSuffixTextColor(ColorStateList.valueOf(getResources().getColor(R.color.biruTeks)));
        nim = findViewById(R.id.login_nimentry);
        password = findViewById(R.id.login_passentry);
        masuk = findViewById(R.id.login_btmasuk);
        alert = new AlertDialog.Builder(this);
        session = new Sessions(getApplicationContext());
        db = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();


        lp = findViewById(R.id.llpoweredby);
        animMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
//        nim.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    lp.startAnimation(animMove);
//                    return true;
//                }
//                return false; //dont want to listen anymore
//            }
//        });

        masuk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_btmasuk:
                u = nim.getText().toString();
                p = password.getText().toString();
                db.child("List/Admin").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listAdmin = new ArrayList<>();
                        for (DataSnapshot ds : snapshot.getChildren()){
                            String adm = ds.getValue(String.class);
                            listAdmin.add(adm);
                        }
                        login(u, p);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                break;
        }
    }
    private void login(String u, String p){
        nim.setError(null);
        if (u.equals("") || p.equals("")){
            alert.setTitle("Ada kolom yang kosong")
                    .setMessage("Username / Password tidak boleh kosong")
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).create().show();
        } else {
            loading = ProgressDialog.show(Login.this,
                    null,
                    "Masuk sebagai "+u+" ...",
                    true,
                    false);
            if (!u.contains("@himagreto-ipb.web.app")) {
                u = u+"@himagreto-ipb.web.app";
            }
            auth.signInWithEmailAndPassword(u.toLowerCase(), p)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = auth.getCurrentUser();
                                nick = user.getEmail().replace("@himagreto-ipb.web.app", "");
                                if (nick != null) {
                                    session.createLoginSession(nick, user.getEmail());
//                                    if (nick.equals("admin54") || nick.equals("admin55") || nick.equals("admin56")) {
                                    if (listAdmin.contains(nick)) {
//                                    startActivity(new Intent(Login.this, Admin.class));
                                    startActivity(new Intent(Login.this, Admin.class));
                                    overridePendingTransition(0,0);
//                                    finish();
                                    loading.dismiss();
                                        } else {
                                        db.child("sedangAktif").child(nick).child("time").setValue(tanggal);
                                        db.child("sedangAktif").child(nick).child("phone").setValue(Build.MANUFACTURER+" "+Build.MODEL+", Android "+Build.VERSION.RELEASE);
//                                        startActivity(new Intent(Login.this, Home.class));
                                        startActivity(new Intent(Login.this, Home.class));
                                        overridePendingTransition(0,0);
                                        finish();
                                        loading.dismiss();
                                        }
                                }
                            } else {
                                alert.setTitle("Gagal masuk")
                                        .setMessage("Pastikan NIM dan password benar")
                                        .setCancelable(true)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        }).create().show();
                                loading.dismiss();
                            }
                        }
                    });
        }
    }
}
