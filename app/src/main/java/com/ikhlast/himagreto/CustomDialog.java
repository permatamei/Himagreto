package com.ikhlast.himagreto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CustomDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public String here;
    public TextView edit, batal;
    public EditText isi;

    public CustomDialog(Activity a, String where) {
        super(a);
        this.c = a;
        this.here = where;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layouteditoprofil);
        isi = findViewById(R.id.profil_nameentrys);
        edit = findViewById(R.id.dialog_txt_edit);
        batal = findViewById(R.id.dialog_txt_batal);
        edit.setOnClickListener(this);
        batal.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_txt_batal:
                dismiss();
                break;
            case R.id.dialog_txt_edit:
//                c.finish();
                if (here.equals("nama")){
                    AlertDialog.Builder al = new AlertDialog.Builder(getOwnerActivity());
                    al.setMessage("Ubah nama menjadi "+isi.getText().toString()+" ?")
                            .create().show();

                }
                cancel();
                break;
            default:
                break;
        }
        dismiss();
    }
}
