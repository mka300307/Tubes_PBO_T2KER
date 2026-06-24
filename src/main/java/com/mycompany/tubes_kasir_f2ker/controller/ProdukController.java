/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */
import com.mycompany.tubes_kasir_f2ker.Services.ProdukService;
import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;
import java.util.List;

public class ProdukController {

    private final ProdukService service = new ProdukService();

    public List<ProdukItem> getAllProduk() {
        return service.getAllProduk();
    }

    public List<ProdukItem> searchProduk(String keyword) {
        return service.searchProduk(keyword);
    }

    public void addProduk(ProdukItem produk) {
        service.addProduk(produk);
    }

    public void updateProduk(ProdukItem produk) {
        service.updateProduk(produk);
    }

    public void deleteProduk(int id) {
        service.deleteProduk(id);
    }
}