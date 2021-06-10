package com.ikhlast.himagreto.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikhlast.himagreto.Admin;
import com.ikhlast.himagreto.Home;
import com.ikhlast.himagreto.Models.Semester;
import com.ikhlast.himagreto.R;

import java.util.ArrayList;

public class AdapterAdmin extends RecyclerView.Adapter<AdapterAdmin.ViewHolder> {
    private ArrayList<String> nama;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterAdmin(ArrayList<String> barang, Context ctx) {
        nama = barang;
        context = ctx;
        listener = (Admin)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView judul;
        CardView cv;

        ViewHolder(View v){
            super(v);
            judul = v.findViewById(R.id.text_monitor_adminke);
            cv = v.findViewById(R.id.isilistmonitoradmin_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isi_rv_listadmin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String judul = nama.get(position);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onAdminClick(nama.get(position), position);
            }
        });

        holder.judul.setText(judul);
    }

    @Override
    public int getItemCount() {
        return nama.size();
    }

    public interface DataListener{
        void onAdminClick(String barang, int position);
    }
}
