package com.example.praktikum;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.praktikum.helper.DBHelper;
import com.makeramen.roundedimageview.RoundedImageView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class TambahResepActivity extends AppCompatActivity {
    EditText nama, bahan, tahap;
    EditText porsi;
    Button btnSubmit;
    RadioGroup level;
    RadioButton radio, mudah, sedang, susah;
    SeekBar lama;
    TextView textLama;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8;
    RoundedImageView fotoResep;

    //konstanta permission
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //konstanta ambil gambar
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALERY_CODE = 103;
    //array permission
    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;
    private String id, nama_resep, porsi_resep, level_resep, bahan_resep, tahap_resep, kategori;
    private Integer lama_resep;
    private boolean isEditMode = false;

    private DBHelper dbHelper = new DBHelper(this);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_resep);

        btnSubmit = findViewById(R.id.buttonSubmit);

        fotoResep = findViewById(R.id.addFotoResep);

        nama = findViewById(R.id.editTextNamaResep);
        porsi = findViewById(R.id.editTextPorsi);
        bahan = findViewById(R.id.editTextBahan);
        tahap = findViewById(R.id.editTextTahap);

        lama = findViewById(R.id.SeekBarLama);
        textLama = findViewById(R.id.TextLama);

        cb1 = findViewById(R.id.checkMinum);
        cb2 = findViewById(R.id.checkKue);
        cb3 = findViewById(R.id.checkCepatSaji);
        cb4 = findViewById(R.id.checkRoti);
        cb5 = findViewById(R.id.checkPizza);
        cb6 = findViewById(R.id.checkBarat);
        cb7 = findViewById(R.id.checkSeafood);
        cb8 = findViewById(R.id.checkNusantara);

        mudah = findViewById(R.id.radioButtonMudah);
        sedang = findViewById(R.id.radioButtonSedang);
        susah = findViewById(R.id.radioButtonSusah);

        //get data dari intent
        Intent intent = getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);
        //update data
        if (isEditMode){

            //get data
            id = intent.getStringExtra("id");
            nama_resep = intent.getStringExtra("nama");
            imageUri = Uri.parse(intent.getStringExtra("foto"));
            porsi_resep = intent.getStringExtra("porsi");
            level_resep = intent.getStringExtra("level");
            lama_resep = intent.getExtras().getInt("lama");
            bahan_resep = intent.getStringExtra("bahan");
            tahap_resep = intent.getStringExtra("tahap");
            kategori = intent.getStringExtra("kategori");

            //set data ke view
            nama.setText(nama_resep);
            porsi.setText(porsi_resep);
            bahan.setText(bahan_resep);
            tahap.setText(tahap_resep);
            textLama.setText(String.valueOf(lama_resep));

            //set radio button checked sesuai value dari resep
            if ("Mudah".equals(level_resep)){
                mudah.setChecked(true);
            }else if ("Sedang".equals(level_resep)){
                sedang.setChecked(true);
            }else if ("Sudah".equals(level_resep)){
                susah.setChecked(true);
            }else {
                mudah.setChecked(false);
                sedang.setChecked(false);
                susah.setChecked(false);
            }

            //jika tidak ada data image, value imageUri jadi null
            if (imageUri.toString().equals("null")){
                fotoResep.setImageResource(R.drawable.add_photo);
            }
            else {
                fotoResep.setImageURI(imageUri);
            }

            lama.setProgress((int) (lama_resep));
            lama.setMax(60);
            lama.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    textLama.setText(String.valueOf((int) progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            String[] kategori_resep = kategori.split(" ");
            for (String s : kategori_resep) {
                switch (s) {
                    case "*Minuman":
                        cb1.setChecked(true);
                        break;
                    case "*Kue":
                        cb2.setChecked(true);
                        break;
                    case "*Cepat-Saji":
                        cb3.setChecked(true);
                        break;
                    case "*Roti":
                        cb4.setChecked(true);
                        break;
                    case "*Pizza&Pasta":
                        cb5.setChecked(true);
                        break;
                    case "*Barat":
                        cb6.setChecked(true);
                        break;
                    case "*Seafood":
                        cb7.setChecked(true);
                        break;
                    case "*Nusantara":
                        cb8.setChecked(true);
                        break;
                }
            }
        }
        else {
            lama.setProgress(1);
            lama.setMax(60);
            lama.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    textLama.setText(Integer.toString((int) progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        dbHelper = new DBHelper(this);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        fotoResep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePickDialog();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validasi()){
                    alert();
                }
            }
        });
    }

    private void imagePickDialog() {
        String[] options = {"Camera", "Galery"};
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        builder.setTitle("Ambil gambar dari");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //handle click
                if (which==0){
                    //camera clicked
                    if (!checkCameraPermissions()){
                        requestCameraPermissions();
                    }else {
                        pickFromCamera();
                    }
                }
                else if (which==1){
                    if (!checkStoragePermissions()){
                        requestStoragePermissions();
                    }else {
                        pickFromGalery();
                    }
                }
            }
        });
        builder.create().show();
    }

    private void pickFromGalery() {
        //intent untuk ngambil gambar dari galery, gambar bakal direturn di method onActivityResult
        Intent galeryIntent = new Intent(Intent.ACTION_PICK);
        galeryIntent.setType("image/*"); //hanya boleh dalam format image
        startActivityForResult(galeryIntent, IMAGE_PICK_GALERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Image title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraintent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermissions(){
        // mengecek jika penyimpanan di enable atau tidak
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private void requestStoragePermissions(){
        // request izin penyimpanan
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermissions(){
        // mengecek jika izin kamera sudah diaktifkan atau tidak
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermissions(){
        // request izin penyimpanan
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //hasil dari izin diterima/tidak
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    //if allowed return true otherwise false
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "Izin dari kamera & penyimpanan diperlukan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    //if allowed return true otherwise false
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGalery();
                    }
                    else {
                        Toast.makeText(this, "Izin dari penyimpanan diperlukan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //gambar yang diambil dari kamera/galery bakal diterima disini
        if (resultCode == RESULT_OK){
            //image is picked
            if (requestCode == IMAGE_PICK_GALERY_CODE){
                //picked from galery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == IMAGE_PICK_CAMERA_CODE){
                //picked from camera

                //crop image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //cropped image recieved
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    fotoResep.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //error
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error,Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validasi(){
        if (nama.getText().toString().length() == 0){
            nama.requestFocus();
            nama.setError("Nama resep harus diisi!");
            return false;
        }
        else if (porsi.getText().toString().length() == 0){
            porsi.requestFocus();
            porsi.setError("Porsi harus diisi!");
            return false;
        }
        else if (!mudah.isChecked() && !sedang.isChecked() && !susah.isChecked()){
            Toast.makeText(TambahResepActivity.this,"Pilih salah satu tingkat kesulitan resep!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (bahan.getText().toString().length() == 0){
            bahan.requestFocus();
            bahan.setError("Bahan-bahan harus diisi!");
            return false;
        }
        else if (tahap.getText().toString().length() == 0){
            tahap.requestFocus();
            tahap.setError("Tahap-tahap harus diisi!");
            return false;
        }
        else if (!cb1.isChecked() && !cb2.isChecked() && !cb3.isChecked() && !cb4.isChecked() && !cb5.isChecked() && !cb6.isChecked() && !cb7.isChecked() && !cb8.isChecked()){
            Toast.makeText(TambahResepActivity.this,"Pilih salah satu kategori resep!", Toast.LENGTH_LONG).show();
            return false;
        }
        else {
            return true;
        }
    }

    private void  alert(){
        level = findViewById(R.id.radioGroupLevel);

        int idRadio = level.getCheckedRadioButtonId();
        radio = findViewById(idRadio);

        nama_resep = nama.getText().toString();
        porsi_resep = porsi.getText().toString();
        bahan_resep = bahan.getText().toString();
        tahap_resep = tahap.getText().toString();
        level_resep = radio.getText().toString();
        lama_resep = Integer.parseInt(textLama.getText().toString());
        kategori = "Kategori resep yang dipilih: ";

        if(cb1.isChecked()){
            kategori += "\n*Minuman";
        }
        if(cb2.isChecked()){
            kategori += "\n*Kue";
        }
        if(cb3.isChecked()){
            kategori += "\n*Cepat Saji";
        }
        if(cb4.isChecked()){
            kategori += "\n*Roti";
        }
        if(cb5.isChecked()){
            kategori += "\n*Pizza & Pasta";
        }
        if(cb6.isChecked()){
            kategori += "\n*Barat";
        }
        if(cb7.isChecked()){
            kategori += "\n*Seafood";
        }
        if(cb8.isChecked()){
            kategori += "\n*Nusantara";
        }

        AlertDialog.Builder alertdialog=new AlertDialog.Builder(TambahResepActivity.this);
        alertdialog.setTitle("Apakah input anda sudah benar?");
        alertdialog.setMessage("Nama Resep : "+nama_resep+'\n'+'\n'+"Porsi : "+porsi_resep+" Orang"+'\n'+'\n'+"Tingkat Kesulitan : "+level_resep+'\n'+'\n'+"Bahan : "+'\n'+bahan_resep+'\n'+'\n'+"Tahap : "+'\n'+tahap_resep+'\n'+'\n'+"Lama: "+lama_resep+" Menit"+'\n'+'\n'+kategori);
        alertdialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                insertData();
            }
        });
        alertdialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = alertdialog.create();
        alert.setCanceledOnTouchOutside( true );
        alert.show();
    }

    private void insertData(){
        nama_resep = nama.getText().toString();
        porsi_resep = porsi.getText().toString();
        bahan_resep = bahan.getText().toString();
        tahap_resep = tahap.getText().toString();
        level_resep = radio.getText().toString();
        lama_resep = Integer.parseInt(textLama.getText().toString());
        kategori = "";

        if(cb1.isChecked()){
            kategori += "*Minuman  ";
        }
        if(cb2.isChecked()){
            kategori += "*Kue  ";
        }
        if(cb3.isChecked()){
            kategori += "*Cepat-Saji  ";
        }
        if(cb4.isChecked()){
            kategori += "*Roti  ";
        }
        if(cb5.isChecked()){
            kategori += "*Pizza&Pasta  ";
        }
        if(cb6.isChecked()){
            kategori += "*Barat  ";
        }
        if(cb7.isChecked()){
            kategori += "*Seafood  ";
        }
        if(cb8.isChecked()){
            kategori += "*Nusantara";
        }

        if (isEditMode){ //update data
            dbHelper.update(""+id,
                    ""+nama_resep,
                    ""+imageUri,
                    ""+porsi_resep,
                    ""+bahan_resep,
                    ""+tahap_resep,
                    ""+level_resep,
                    ""+lama_resep,
                    ""+kategori
            );
            Toast.makeText(this,"Data berhasil diupdate", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, DetailResepActivity.class);
            intent.putExtra("id_resep", id);
            startActivity(intent);
        }
        else { //insert data baru ke tabel
            dbHelper.insert(
                    ""+nama_resep,
                    ""+imageUri,
                    ""+porsi_resep,
                    ""+bahan_resep,
                    ""+tahap_resep,
                    ""+level_resep,
                    Integer.valueOf(""+lama_resep),
                    ""+kategori
            );
            Toast.makeText(this,"Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);

            startActivity(intent);
        }
    }
}
