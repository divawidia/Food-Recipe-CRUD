package com.example.praktikum.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.praktikum.Resep;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "resep";

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TB = "CREATE TABLE resep (id INTEGER PRIMARY KEY autoincrement," +
                "nama_resep TEXT NOT NULL," +
                "foto TEXT NOT NULL," +
                "porsi TEXT NOT NULL," +
                "level TEXT NOT NULL," +
                "lama INTEGER NOT NULL," +
                "bahan TEXT NOT NULL," +
                "tahap TEXT NOT NULL," +
                "kategori TEXT NOT NULL)";
        db.execSQL(SQL_CREATE_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS resep");
        onCreate(db);
    }

    // mengambil semua data resep
    public ArrayList <Resep> getResep() {
        ArrayList<Resep> arrayList = new ArrayList<>();
        // query select
//        String QUERY = "SELECT * FROM resep";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM resep", null);

        //looping semua baris dan menambahkan ke list
        if (cursor.moveToFirst()){
            do {
                Resep resep = new Resep();
                resep.setId(cursor.getString(0));
                resep.setNama_resep(cursor.getString(1));
                resep.setFoto_resep(cursor.getString(2));
                resep.setPorsi_resep(cursor.getString(3));
                resep.setLevel(cursor.getString(4));
                resep.setLama(cursor.getInt(5));
                resep.setBahan_resep(cursor.getString(6));
                resep.setTahap_resep(cursor.getString(7));
                resep.setKategori(cursor.getString(8));
                arrayList.add(resep);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    public boolean dataKosong(){
        SQLiteDatabase database = this.getReadableDatabase();
        long jumlahRows = DatabaseUtils.queryNumEntries(database, "resep");
        if (jumlahRows == 0){
            return true;
        }else {
            return false;
        }
    }

    //memasukan data input ke db
    public void insert (String nama_resep, String foto, String porsi, String bahan, String tahap, String level, Integer lama, String kategori){
        SQLiteDatabase database = this.getWritableDatabase();
        String QUERY = "INSERT INTO resep (nama_resep, foto,  porsi, level, lama, bahan, tahap, kategori)" +
                "VALUES ('"+nama_resep+"', '"+foto+"', '"+porsi+"', '"+level+"', '"+lama+"', '"+bahan+"', '"+tahap+"', '"+kategori+"')";
        database.execSQL(QUERY);
    }

    //mengupdate data
    public void update(String id, String nama_resep, String foto, String porsi, String bahan, String tahap, String level, String lama, String kategori){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("nama_resep", nama_resep);
        values.put("foto", foto);
        values.put("porsi", porsi);
        values.put("level", level);
        values.put("lama", lama);
        values.put("bahan", bahan);
        values.put("tahap", tahap);
        values.put("kategori", kategori);

        database.update("resep", values, "id = ?", new String[]{id});

        database.close();
    }

    // menghapus data
    public void delete(String id){
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete("resep", "id = ?", new String[]{id});
        database.close();
    }
}
