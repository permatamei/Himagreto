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
import com.ikhlast.himagreto.Home;
import com.ikhlast.himagreto.Models.Semester;
import com.ikhlast.himagreto.PilihMatkul;
import com.ikhlast.himagreto.R;

import java.util.ArrayList;

public class AdapterPilihMatkul extends RecyclerView.Adapter<AdapterPilihMatkul.ViewHolder> {
    private ArrayList<String> matkul;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterPilihMatkul(ArrayList<String> list, Context ctx) {
        matkul = list;
        context = ctx;
        listener = (PilihMatkul)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView judul;
        CardView cv;

        ViewHolder(View v){
            super(v);
            judul = v.findViewById(R.id.text_pilihmatkul_namamatkul);
            cv = v.findViewById(R.id.isilistpilihmatkul_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isi_rv_pilihmatkul, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String judul = matkul.get(position);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMatkulClick(matkul.get(position), position);
            }
        });

        holder.judul.setText(judul);
//        holder.deskripsi.setText(desc);
    }

    @Override
    public int getItemCount() {
        return matkul.size();
    }

    public interface DataListener{
        void onMatkulClick(String barang, int position);
    }
}
