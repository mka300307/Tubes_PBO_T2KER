/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.model;

/**
 *
 * @author attau
 */

public class TransaksiItem {

    private int idTransaksi;
    private int idUser;
    private String namaUser;
    private double totalTransaksi;
    private String tanggal;

    // Constructor lengkap (untuk fetch dari DB)
    public TransaksiItem(int idTransaksi, int idUser, String namaUser,
                         double totalTransaksi, String tanggal) {
        this.idTransaksi    = idTransaksi;
        this.idUser         = idUser;
        this.namaUser       = namaUser;
        this.totalTransaksi = totalTransaksi;
        this.tanggal        = tanggal;
    }

    // Constructor tanpa id (untuk insert baru)
    public TransaksiItem(int idUser, String namaUser,
                         double totalTransaksi, String tanggal) {
        this.idTransaksi    = 0;
        this.idUser         = idUser;
        this.namaUser       = namaUser;
        this.totalTransaksi = totalTransaksi;
        this.tanggal        = tanggal;
    }

    public int getIdTransaksi()        { return idTransaksi; }
    public int getIdUser()             { return idUser; }
    public String getNamaUser()        { return namaUser; }
    public double getTotalTransaksi()  { return totalTransaksi; }
    public String getTanggal()         { return tanggal; }

    public void setIdTransaksi(int idTransaksi)           { this.idTransaksi = idTransaksi; }
    public void setIdUser(int idUser)                     { this.idUser = idUser; }
    public void setNamaUser(String namaUser)              { this.namaUser = namaUser; }
    public void setTotalTransaksi(double totalTransaksi)  { this.totalTransaksi = totalTransaksi; }
    public void setTanggal(String tanggal)                { this.tanggal = tanggal; }

    @Override
    public String toString() {
        return "Transaksi #" + idTransaksi + " - " + namaUser;
    }
}