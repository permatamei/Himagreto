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
import com.ikhlast.himagreto.R;

import java.util.ArrayList;

public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {
    private ArrayList<Semester> semester;
    private Context context;

    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DataListener listener;
    private String user;

    public AdapterHome(ArrayList<Semester> barang, Context ctx) {
        semester = barang;
        context = ctx;
        listener = (Home)ctx;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView judul, deskripsi;
        CardView cv;

        ViewHolder(View v){
            super(v);
            judul = v.findViewById(R.id.text_semester_cardhome);
            deskripsi = v.findViewById(R.id.text_isi_semester_cardhome);
            cv = v.findViewById(R.id.isilisthome_cardview);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        user = mUser.getEmail().replace("@himagreto-ipb.web.app", "");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.isi_rv_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final String judul = semester.get(position).getName();
        final String desc = semester.get(position).getDetail();

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onSemesterClick(semester.get(position), position);
            }
        });

        holder.judul.setText(judul);
        holder.deskripsi.setText(desc);
    }

    @Override
    public int getItemCount() {
        return semester.size();
    }

    public interface DataListener{
        void onSemesterClick(Semester barang, int position);
    }
}
