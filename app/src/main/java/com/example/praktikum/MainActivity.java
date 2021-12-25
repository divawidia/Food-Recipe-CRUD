package com.example.praktikum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.praktikum.adapter.DBAdapter;
import com.example.praktikum.helper.DBHelper;


public class MainActivity extends AppCompatActivity{
    private Button btnTambah;
    private RecyclerView listResep;
    private TextView textViewEmpty;

    private DBHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTambah = findViewById(R.id.btnTambah);
        listResep = findViewById(R.id.list_resep);
        textViewEmpty = findViewById(R.id.textEmpty);

        dbHelper = new DBHelper(this);

        if (dbHelper.dataKosong()){
            textViewEmpty.setVisibility(View.VISIBLE);
            listResep.setVisibility(View.GONE);
        }else {
            loadRecords();
            textViewEmpty.setVisibility(View.GONE);
        }

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TambahResepActivity.class);
                intent.putExtra("isEditMode", false);
                startActivity(intent);
            }
        });
    }

    private void loadRecords() {
        DBAdapter adapter = new DBAdapter(MainActivity.this, dbHelper.getResep());
        listResep.setAdapter(adapter);

    }



    @Override
    public void onResume() {
        super.onResume();
        if (dbHelper.dataKosong()){
            textViewEmpty.setVisibility(View.VISIBLE);
            listResep.setVisibility(View.GONE);
        }else {
            loadRecords();
            textViewEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handle item menu
        return super.onOptionsItemSelected(item);
    }
}