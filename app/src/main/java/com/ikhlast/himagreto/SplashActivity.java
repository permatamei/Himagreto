package com.ikhlast.himagreto;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.ikhlast.himagreto.Sessions;

public class SplashActivity extends AppCompatActivity {
    private int delay = 2000;
    public static int jmlsoal, jmlsoaldummy, jmlsoalpg, jmlsoalpgdummy, jmlsoalbs, jmlsoalbsdummy, pbenar, pbenarbs, psalah, psalahbs, pkosong, timlolos, waktu;
    public static String bnow, bnext, jkosong, sesi, spass, spassdummy, benar, salah, cp;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    Sessions session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
//        int uiOpt = getWindow().getDecorView().getSystemUiVisibility();
//        if (Build.VERSION.SDK_INT >= 14) {
//            uiOpt ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
//        }
//
//        // Status bar hiding: Backwards compatible to Jellybean
//        if (Build.VERSION.SDK_INT >= 16) {
//            uiOpt ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
//        }
//
//        // Immersive mode: Backward compatible to KitKat.
//        // Note that this flag doesn't do anything by itself, it only augments the behavior
//        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
//        // all three flags are being toggled together.
//        // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
//        // Sticky immersive mode differs in that it makes the navigation and status bars
//        // semi-transparent, and the UI flag does not get cleared when the user interacts with
//        // the screen.
//        if (Build.VERSION.SDK_INT >= 18) {
//            uiOpt ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//        }
//        getWindow().getDecorView().setSystemUiVisibility(uiOpt);

        session = new Sessions(getApplicationContext());

        try {
            new Handler(Looper.myLooper()).postDelayed(new Runnable(){
                @Override
                public void run(){

                    session.checkLogin();
//                    startActivity(new Intent(SplashActivity.this, Login.class));
                    overridePendingTransition(0,0);
                    finish();
                }
            },delay);
            } catch (Exception e){
            Toast.makeText(this, ""+e, Toast.LENGTH_LONG).show();
        }

    }
}
