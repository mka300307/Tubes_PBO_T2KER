/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.model;

/**
 *
 * @author attau
 */
public class DetailTransaksiItem {

    private int idDetail;
    private int idTransaksi;
    private int idProduk;
    private String namaProduk;
    private double hargaSatuan;
    private int jumlah;
    private double subtotal;

    // Constructor lengkap (untuk fetch dari DB)
    public DetailTransaksiItem(int idDetail, int idTransaksi, int idProduk,
                                String namaProduk, double hargaSatuan,
                                int jumlah, double subtotal) {
        this.idDetail     = idDetail;
        this.idTransaksi  = idTransaksi;
        this.idProduk     = idProduk;
        this.namaProduk   = namaProduk;
        this.hargaSatuan  = hargaSatuan;
        this.jumlah       = jumlah;
        this.subtotal     = subtotal;
    }

    // Constructor tanpa id (untuk insert baru)
    public DetailTransaksiItem(int idTransaksi, int idProduk,
                                String namaProduk, double hargaSatuan,
                                int jumlah, double subtotal) {
        this.idDetail     = 0;
        this.idTransaksi  = idTransaksi;
        this.idProduk     = idProduk;
        this.namaProduk   = namaProduk;
        this.hargaSatuan  = hargaSatuan;
        this.jumlah       = jumlah;
        this.subtotal     = subtotal;
    }

    public int getIdDetail()          { return idDetail; }
    public int getIdTransaksi()       { return idTransaksi; }
    public int getIdProduk()          { return idProduk; }
    public String getNamaProduk()     { return namaProduk; }
    public double getHargaSatuan()    { return hargaSatuan; }
    public int getJumlah()            { return jumlah; }
    public double getSubtotal()       { return subtotal; }

    public void setIdDetail(int idDetail)             { this.idDetail = idDetail; }
    public void setIdTransaksi(int idTransaksi)       { this.idTransaksi = idTransaksi; }
    public void setIdProduk(int idProduk)             { this.idProduk = idProduk; }
    public void setNamaProduk(String namaProduk)      { this.namaProduk = namaProduk; }
    public void setHargaSatuan(double hargaSatuan)    { this.hargaSatuan = hargaSatuan; }
    public void setJumlah(int jumlah)                 { this.jumlah = jumlah; }
    public void setSubtotal(double subtotal)          { this.subtotal = subtotal; }

    @Override
    public String toString() {
        return namaProduk + " x" + jumlah;
    }
}
