package com.ikhlast.himagreto.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ikhlast.himagreto.Admin;
import com.ikhlast.himagreto.Home;
import com.ikhlast.himagreto.Models.Tugas;
import com.ikhlast.himagreto.R;

import java.util.ArrayList;

public class AdapterTugasAdmin extends RecyclerView.Adapter<AdapterTugasAdmin.ViewHolder> {
    private ArrayList<Tugas> tugases;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private DeleteListener listener2;
    private EditListener listener3;
    private String user;

    public AdapterTugasAdmin(ArrayList<Tugas> tugas, Context ctx) {
        tugases = tugas;
        context = ctx;
        listener = (Admin)ctx;
        listener2 = (Admin) ctx;
        listener3 = (Admin) ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView matkul, deskripsi, deadline;
        CardView cv;
        ImageView edit, hapus;

        ViewHolder(View v){
            super(v);
            deadline = v.findViewById(R.id.dedlen_tgl);
            deskripsi = v.findViewById(R.id.text_tugas_isi_rv_tugas);
            edit = v.findViewById(R.id.admin_edit_tugas);
            hapus = v.findViewById(R.id.admin_hapus_tugas);
            cv = v.findViewById(R.id.isi_rv_tugas_cardview_tugasnya);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isi_rv_tugas_admin, parent, false);
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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener3.onEdit(tugases.get(position), position);
            }
        });

        holder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert
                        .setTitle("Hapus tugas")
                        .setMessage("Yakin ingin menghapus tugas ini?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                listener2.onDelete(tugases.get(position), position);
                            }
                        }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return tugases.size();
    }

    public interface DataListener{
        void onTugasSeninClick(Tugas tugas, int position);
    }

    public interface DeleteListener {
        void onDelete(Tugas tugas, int position);
    }

    public interface EditListener {
        void onEdit(Tugas tugas, int position);
    }

}
