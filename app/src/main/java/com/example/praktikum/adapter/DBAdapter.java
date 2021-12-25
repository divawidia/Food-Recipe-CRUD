package com.example.praktikum.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.praktikum.DetailResepActivity;
import com.example.praktikum.MainActivity;
import com.example.praktikum.R;
import com.example.praktikum.Resep;
import com.example.praktikum.TambahResepActivity;
import com.example.praktikum.helper.DBHelper;

import java.util.ArrayList;

public class DBAdapter extends RecyclerView.Adapter<DBAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Resep> arrayList;
    DBHelper databaseHelper;

    //constructor
    public DBAdapter(Context context, ArrayList<Resep> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

        databaseHelper = new DBHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoResep;
        TextView namaResep, kategori, lamaResep;
        ImageButton moreBtn;

        public ViewHolder(@NonNull View view) {
            super(view);

            fotoResep = view.findViewById(R.id.fotoResep);
            namaResep = view.findViewById(R.id.text_nama_resep);
            lamaResep = view.findViewById(R.id.text_lama_resep);
            kategori = view.findViewById(R.id.text_kategori_resep);
            moreBtn = view.findViewById(R.id.moreBtn);
        }
    }

    @NonNull
    @Override
    public DBAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infalate layout
        View view = LayoutInflater.from(context).inflate(R.layout.list_resep, parent, false);
        return new ViewHolder(view);
    }

    //get data, set data, handle view click in method
    @Override
    public void onBindViewHolder(@NonNull DBAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //get data
        Resep modelResep = arrayList.get(position);
        String id = modelResep.getId();
        String nama = modelResep.getNama_resep();
        String foto = modelResep.getFoto_resep();
        String porsi = modelResep.getPorsi_resep();
        String level = modelResep.getLevel();
        int lama = modelResep.getLama();
        String bahan = modelResep.getBahan_resep();
        String tahap = modelResep.getTahap_resep();
        String kategori = modelResep.getKategori();

        //set data ke views
        holder.namaResep.setText(nama);
        holder.kategori.setText(kategori);
        holder.lamaResep.setText(String.valueOf(lama));
        holder.fotoResep.setImageURI(Uri.parse(foto));

        //jika user tidak menginput foto maka imageuri jadi null, jadi disini diset foto defaultnya
        if (foto.equals("null")){
            holder.fotoResep.setImageResource(R.drawable.add_photo);
        }
        else {
            holder.fotoResep.setImageURI(Uri.parse(foto));
        }

        //handle klik item (diarahkan ke activity detail saat klik item)
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass record id to next activity to show details of that records
                Intent intent = new Intent(context, DetailResepActivity.class);
                intent.putExtra("id_resep", id);
                context.startActivity(intent);

            }
        });

        //handle tombol more untuk edit, delete
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMoreDialog(
                       ""+position,
                        ""+id,
                        ""+nama,
                        ""+foto,
                        ""+porsi,
                        ""+level,
                        Integer.valueOf(""+lama),
                        ""+bahan,
                        ""+tahap,
                        ""+kategori);
            }
        });
    }

    public void showMoreDialog(String position, String id, String nama, String foto, String porsi, String level, Integer lama, String bahan, String tahap, String kategori){
        String[] options = {"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //tombol edit diklik
                if (which==0){
                    //tombol edit diklik
                    Intent intent = new Intent(context, TambahResepActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("nama", nama);
                    intent.putExtra("foto", foto);
                    intent.putExtra("porsi", porsi);
                    intent.putExtra("level", level);
                    intent.putExtra("lama", lama);
                    intent.putExtra("bahan", bahan);
                    intent.putExtra("tahap", tahap);
                    intent.putExtra("kategori", kategori);
                    intent.putExtra("isEditMode", true);
                    context.startActivity(intent);
                }
                //tombol delete diklik
                else if (which==1){
                    databaseHelper.delete(id);
                    ((MainActivity)context).onResume();
                }
            }
        });
        builder.create().show();
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
