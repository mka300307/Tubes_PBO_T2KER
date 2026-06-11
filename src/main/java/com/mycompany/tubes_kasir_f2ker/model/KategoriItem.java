/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.model;

/**
 *
 * @author attau
 */
public class KategoriItem {
    private int id;
    private String nama;

    public KategoriItem(int id, String nama) {
        this.id   = id;
        this.nama = nama;
    }
    
    public KategoriItem(String nama) {
        this.id   = 0;
        this.nama = nama;
    }
    
    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }
    
    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        return nama;
    }
}
