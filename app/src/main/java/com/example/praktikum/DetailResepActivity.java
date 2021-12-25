package com.example.praktikum;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praktikum.adapter.DBAdapter;
import com.example.praktikum.helper.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DetailResepActivity extends AppCompatActivity {
    private TextView namaText, porsiText, tingkatText, lamaText, bahanText, tahapText, kategoriText;
    private ImageView foto_resep;
    private String idResep;
    private DBHelper dbHelper;
    private FloatingActionButton btnBack, btnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resep);

        foto_resep = findViewById(R.id.fotoResep);

        namaText = findViewById(R.id.textJudulResep);
        porsiText = findViewById(R.id.textPorsi);
        tingkatText = findViewById(R.id.textTingkat);
        lamaText = findViewById(R.id.textLama);
        bahanText = findViewById(R.id.textBahan);
        tahapText = findViewById(R.id.textTahap);
        kategoriText = findViewById(R.id.textKategori);

        btnBack = findViewById(R.id.btn_Back);
        btnMore = findViewById(R.id.btn_More);

        // get id record dari adapter melalui intent
        Intent intent = getIntent();
        idResep = intent.getStringExtra("id_resep");

        dbHelper = new DBHelper(this);
        
        showDetailResep();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailResepActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showDetailResep() {
        String QUERY = "SELECT * FROM resep WHERE id = "+idResep;

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);

        //tetep ngecek db untuk recordnya
        if (cursor.moveToFirst()){
            do {
                String id = ""+ cursor.getInt(cursor.getColumnIndex("id"));
                String nama = ""+ cursor.getString(cursor.getColumnIndex("nama_resep"));
                String foto = ""+ cursor.getString(cursor.getColumnIndex("foto"));
                String porsi = ""+ cursor.getString(cursor.getColumnIndex("porsi"));
                String level = ""+ cursor.getString(cursor.getColumnIndex("level"));
                int lama = Integer.parseInt(""+ cursor.getString(cursor.getColumnIndex("lama")));
                String bahan = ""+ cursor.getString(cursor.getColumnIndex("bahan"));
                String tahap = ""+ cursor.getString(cursor.getColumnIndex("tahap"));
                String kategori = ""+ cursor.getString(cursor.getColumnIndex("kategori"));

                namaText.setText(nama);
                porsiText.setText(porsi);
                tingkatText.setText(level);
                lamaText.setText(String.valueOf(lama));
                bahanText.setText(bahan);
                tahapText.setText(tahap);
                kategoriText.setText(kategori);

                //jika user tidak menginput foto maka imageuri jadi null, jadi disini diset foto defaultnya
                if (foto.equals("null")){
                    foto_resep.setImageResource(R.drawable.add_photo);
                }
                else {
                    foto_resep.setImageURI(Uri.parse(foto));
                }
                btnMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] options = {"Edit", "Delete"};

                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailResepActivity.this);
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //tombol edit diklik
                                if (which==0){
                                    //tombol edit diklik
                                    Intent intent = new Intent(DetailResepActivity.this, TambahResepActivity.class);
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
                                    startActivity(intent);
                                }
                                //tombol delete diklik
                                else if (which==1){
                                    dbHelper.delete(id);
                                    Intent intent = new Intent(DetailResepActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                        builder.create().show();
                    }
                });

            }while (cursor.moveToNext());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(DetailResepActivity.this,"onRestart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(DetailResepActivity.this,"onResume",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(DetailResepActivity.this,"onStop",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(DetailResepActivity.this,"onDestroy",Toast.LENGTH_SHORT).show();
    }
}