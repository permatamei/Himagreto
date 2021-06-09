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
import com.ikhlast.himagreto.PilihBab;
import com.ikhlast.himagreto.R;

import java.util.ArrayList;

public class AdapterPilihBab extends RecyclerView.Adapter<AdapterPilihBab.ViewHolder> {
    private ArrayList<String> bab;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterPilihBab(ArrayList<String> list, Context ctx) {
        bab = list;
        context = ctx;
        listener = (PilihBab)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView judul;
        CardView cv;

        ViewHolder(View v){
            super(v);
            judul = v.findViewById(R.id.text_pilihbab_nomorbab);
            cv = v.findViewById(R.id.isilistpilihbab_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isi_rv_pilihbab, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String judul = bab.get(position);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBabClick(bab.get(position), position);
            }
        });

        holder.judul.setText("Bab "+judul);
//        holder.deskripsi.setText(desc);
    }

    @Override
    public int getItemCount() {
        return bab.size();
    }

    public interface DataListener{
        void onBabClick(String barang, int position);
    }
}
