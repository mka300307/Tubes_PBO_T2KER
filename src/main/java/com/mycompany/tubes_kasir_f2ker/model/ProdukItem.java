/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.model;

/**
 *
 * @author attau
 */

public class ProdukItem {
    
    private int id;
    private String nama;
    private int idKategori;
    private String namaKategori;
    private double harga;
    private int stok;

    public ProdukItem(int id, String nama, int idKategori, String namaKategori, double harga, int stok) {
        this.id           = id;
        this.nama         = nama;
        this.idKategori   = idKategori;
        this.namaKategori = namaKategori;
        this.harga        = harga;
        this.stok         = stok;
    }

    public ProdukItem(String nama, int idKategori, String namaKategori, double harga, int stok) {
        this.id           = 0;
        this.nama         = nama;
        this.idKategori   = idKategori;
        this.namaKategori = namaKategori;
        this.harga        = harga;
        this.stok         = stok;
    }

    public int getId()             { return id; }
    public String getNama()        { return nama; }
    public int getIdKategori()     { return idKategori; }
    public String getNamaKategori(){ return namaKategori; }
    public double getHarga()       { return harga; }
    public int getStok()           { return stok; }

    public void setId(int id)                    { this.id = id; }
    public void setNama(String nama)             { this.nama = nama; }
    public void setIdKategori(int idKategori)    { this.idKategori = idKategori; }
    public void setNamaKategori(String nama)     { this.namaKategori = nama; }
    public void setHarga(double harga)           { this.harga = harga; }
    public void setStok(int stok)                { this.stok = stok; }

    @Override
    public String toString() {
        return nama;
    }
}
