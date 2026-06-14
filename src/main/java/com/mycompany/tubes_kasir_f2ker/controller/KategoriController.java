/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */


import com.mycompany.tubes_kasir_f2ker.Services.KategoriService;
import com.mycompany.tubes_kasir_f2ker.model.KategoriItem;
import java.util.List;

public class KategoriController {

    private final KategoriService service = new KategoriService();

    public List<KategoriItem> getAllKategori() {
        return service.getAllKategori();
    }

    public List<KategoriItem> searchKategori(String keyword) {
        return service.searchKategori(keyword);
    }

    public void addKategori(KategoriItem kategori) {
        service.addKategori(kategori);
    }

    public void updateKategori(KategoriItem kategori) {
        service.updateKategori(kategori);
    }

    public void deleteKategori(int id) {
        service.deleteKategori(id);
    }

    public int getIdByNama(String namaKategori) {
        return service.getIdByNama(namaKategori);
    }
}