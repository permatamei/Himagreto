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
import com.ikhlast.himagreto.Models.Tugas;
import com.ikhlast.himagreto.R;

import java.util.ArrayList;

public class AdapterTugas extends RecyclerView.Adapter<AdapterTugas.ViewHolder> {
    private ArrayList<Tugas> tugases;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterTugas(ArrayList<Tugas> tugas, Context ctx) {
        tugases = tugas;
        context = ctx;
        listener = (Home)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView matkul, deskripsi, deadline;
        CardView cv;

        ViewHolder(View v){
            super(v);
            deadline = v.findViewById(R.id.dedlen_tgl);
            deskripsi = v.findViewById(R.id.text_tugas_isi_rv_tugas);
            cv = v.findViewById(R.id.isi_rv_tugas_cardview_tugasnya);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isi_rv_tugas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {


        final String mataKuliah = tugases.get(position).getMatkul();
        final String desc = tugases.get(position).getDeskripsi();
        final String dedlen = tugases.get(position).getDeadline();


        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onTugasSeninClick(tugases.get(position), position);
            }
        });

        holder.deskripsi.setText(mataKuliah+": "+ desc);
        holder.deadline.setText(dedlen);
    }

    @Override
    public int getItemCount() {
        return tugases.size();
    }

    public interface DataListener{
        void onTugasSeninClick(Tugas tugas, int position);
    }
}
