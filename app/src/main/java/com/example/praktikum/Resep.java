package com.example.praktikum;

import android.os.Parcel;
import android.os.Parcelable;

public class Resep implements Parcelable {
    String id;
    String nama_resep;
    String porsi_resep;
    String bahan_resep;
    String tahap_resep;
    String level;
    int lama;
    String kategori;
    String foto_resep;


    public Resep(String id, String nama_resep, String foto_resep, String porsi_resep, String bahan_resep, String tahap_resep, String level, Integer lama, String kategori) {
        this.id = id;
        this.nama_resep = nama_resep;
        this.foto_resep = foto_resep;
        this.porsi_resep = porsi_resep;
        this.bahan_resep = bahan_resep;
        this.tahap_resep = tahap_resep;
        this.level = level;
        this.lama = lama;
        this.kategori = kategori;
    }

    protected Resep(Parcel in) {
        nama_resep = in.readString();
        porsi_resep = in.readString();
        bahan_resep = in.readString();
        tahap_resep = in.readString();
        level = in.readString();
        lama = in.readInt();
        kategori = in.readString();
    }

    public static final Creator<Resep> CREATOR = new Creator<Resep>() {
        @Override
        public Resep createFromParcel(Parcel in) {
            return new Resep(in);
        }

        @Override
        public Resep[] newArray(int size) {
            return new Resep[size];
        }
    };

    public Resep() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_resep() {
        return nama_resep;
    }

    public void setNama_resep(String nama_resep) {
        this.nama_resep = nama_resep;
    }

    public String getPorsi_resep() {
        return porsi_resep;
    }

    public void setPorsi_resep(String porsi_resep) {
        this.porsi_resep = porsi_resep;
    }

    public String getBahan_resep() {
        return bahan_resep;
    }

    public void setBahan_resep(String bahan_resep) {
        this.bahan_resep = bahan_resep;
    }

    public String getTahap_resep() {
        return tahap_resep;
    }

    public void setTahap_resep(String tahap_resep) {
        this.tahap_resep = tahap_resep;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getLama() {
        return lama;
    }

    public void setLama(int lama) {
        this.lama = lama;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getFoto_resep() {
        return foto_resep;
    }

    public void setFoto_resep(String foto_resep) {
        this.foto_resep = foto_resep;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama_resep);
        parcel.writeString(porsi_resep);
        parcel.writeString(bahan_resep);
        parcel.writeString(tahap_resep);
        parcel.writeString(level);
        parcel.writeInt(lama);
        parcel.writeString(kategori);
    }
}
