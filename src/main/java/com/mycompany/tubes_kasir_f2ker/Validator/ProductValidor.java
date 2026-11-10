/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Validator;

/**
 *
 * @author bapti
 */
import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;

public class ProductValidor implements IValidator<ProdukItem> {
    @Override
    public void validate(ProdukItem produk) {
        if (produk.getNama() == null || produk.getNama().trim().isEmpty())
            throw new RuntimeException("Nama produk tidak boleh kosong.");
        if (produk.getNama().trim().length() < 2)
            throw new RuntimeException("Nama produk minimal 2 karakter.");
        if (produk.getHarga() <= 0)
            throw new RuntimeException("Harga produk harus lebih dari 0.");
        if (produk.getStok() < 0)
            throw new RuntimeException("Stok produk tidak boleh negatif.");
    }
}
