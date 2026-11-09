/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

import com.mycompany.tubes_kasir_f2ker.model.DetailTransaksiItem;
import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;
import java.util.List;

/**
 *
 * @author attau
 */
public class KeranjangService {
    public double hitungTotal(
            List<DetailTransaksiItem> items) {
        return items.stream().mapToDouble(DetailTransaksiItem::getSubtotal).sum();
    }

    public double hitungKembalian(
            double uangBayar, double total) {

        if (uangBayar < total) {
            throw new IllegalArgumentException(
                "Uang pembeli kurang!");
        }

        return uangBayar - total;
    }

    public void tambahItem(
            List<DetailTransaksiItem> items,
            List<Integer> idProduk,
            ProdukItem produk,
            int jumlah) {
        
        if (jumlah <= 0)
            throw new IllegalArgumentException("Jumlah harus lebih dari 0!");
        if (jumlah > produk.getStok())
            throw new IllegalArgumentException("Stok tidak cukup!");

        for (int i = 0; i < idProduk.size(); i++) {
            if (idProduk.get(i) == produk.getId()) {
                int jumlahBaru =items.get(i).getJumlah() + jumlah;
                if (jumlahBaru > produk.getStok())
                    throw new IllegalArgumentException(
                            "Stok tidak mencukupi!");
                items.get(i).setJumlah(jumlahBaru);
                items.get(i).setSubtotal(jumlahBaru * produk.getHarga());
                return;
            }
        }

        items.add(new DetailTransaksiItem(
                0, produk.getId(), produk.getNama(),
                produk.getHarga(), jumlah,
                jumlah * produk.getHarga()
            ));
        }

}
