/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.Services.TransaksiService;
import com.mycompany.tubes_kasir_f2ker.model.DetailTransaksiItem;
import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;
import com.mycompany.tubes_kasir_f2ker.model.TransaksiItem;
import java.util.List;

public class TransaksiController {

    private final TransaksiService service = new TransaksiService();

    public List<ProdukItem> getProdukAktif() {
        return service.getProdukAktif();
    }

    public List<TransaksiItem> getAllTransaksi() {
        return service.getAllTransaksi();
    }

    public List<TransaksiItem> getTransaksiByUser(int idUser) {
        return service.getTransaksiByUser(idUser);
    }

    public List<DetailTransaksiItem> getDetailTransaksi(int idTransaksi) {
        return service.getDetailTransaksi(idTransaksi);
    }

    public List<DetailTransaksiItem> tambahItem(
            List<DetailTransaksiItem> listSementara,
            List<Integer> listIdProduk,
            ProdukItem produk, int jumlah) {
        return service.tambahItem(listSementara, listIdProduk, produk, jumlah);
    }

    public List<DetailTransaksiItem> updateItem(
            List<DetailTransaksiItem> listSementara,
            List<Integer> listIdProduk,
            int selectedRow, ProdukItem produkBaru, int jumlahBaru) {
        return service.updateItem(listSementara, listIdProduk, selectedRow, produkBaru, jumlahBaru);
    }

    public List<DetailTransaksiItem> deleteItem(
            List<DetailTransaksiItem> listSementara,
            List<Integer> listIdProduk, int selectedRow) {
        return service.deleteItem(listSementara, listIdProduk, selectedRow);
    }

    public double hitungTotal(List<DetailTransaksiItem> listSementara) {
        return service.hitungTotal(listSementara);
    }

    public double hitungKembalian(double uangBayar, double total) {
        return service.hitungKembalian(uangBayar, total);
    }

    public int simpanTransaksi(int idUser, double total, List<DetailTransaksiItem> listDetail) {
        return service.simpanTransaksi(idUser, total, listDetail);
    }

    public void deleteTransaksi(int idTransaksi) {
        service.deleteTransaksi(idTransaksi);
    }

    public List<Object[]> getDashboardTransaksi(String keyword) {
        return service.getDashboardTransaksi(keyword);
    }
}
