/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukService {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<ProdukItem> getAllProduk() {
        List<ProdukItem> list = new ArrayList<>();
        String sql = "SELECT p.id_produk, p.nama_produk, p.id_kategori, "
                   + "k.nama_kategori, p.harga, p.stok "
                   + "FROM produk p "
                   + "LEFT JOIN kategori_produk k ON p.id_kategori = k.id_kategori "
                   + "ORDER BY p.id_produk ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRow(rs));
        } catch (Exception e) {
            throw new RuntimeException("Gagal load produk: " + e.getMessage(), e);
        }
        return list;
    }

    public List<ProdukItem> searchProduk(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllProduk();
        List<ProdukItem> list = new ArrayList<>();
        String sql = "SELECT p.id_produk, p.nama_produk, p.id_kategori, "
                   + "k.nama_kategori, p.harga, p.stok "
                   + "FROM produk p "
                   + "LEFT JOIN kategori_produk k ON p.id_kategori = k.id_kategori "
                   + "WHERE p.nama_produk LIKE ? OR k.nama_kategori LIKE ? "
                   + "ORDER BY p.id_produk ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.trim() + "%");
            ps.setString(2, "%" + keyword.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal search produk: " + e.getMessage(), e);
        }
        return list;
    }

    public void addProduk(ProdukItem produk) {
        validateProduk(produk);
        String sql = "INSERT INTO produk (nama_produk, id_kategori, harga, stok) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produk.getNama().trim());
            ps.setInt(2, produk.getIdKategori());
            ps.setDouble(3, produk.getHarga());
            ps.setInt(4, produk.getStok());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal tambah produk: " + e.getMessage(), e);
        }
    }

    public void updateProduk(ProdukItem produk) {
        if (produk.getId() <= 0)
            throw new RuntimeException("ID produk tidak valid.");
        validateProduk(produk);
        String sql = "UPDATE produk SET nama_produk = ?, id_kategori = ?, harga = ?, stok = ? WHERE id_produk = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, produk.getNama().trim());
            ps.setInt(2, produk.getIdKategori());
            ps.setDouble(3, produk.getHarga());
            ps.setInt(4, produk.getStok());
            ps.setInt(5, produk.getId());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal update produk: " + e.getMessage(), e);
        }
    }

    public void deleteProduk(int id) {
        if (id <= 0)
            throw new RuntimeException("ID produk tidak valid.");
        String sql = "DELETE FROM produk WHERE id_produk = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Gagal hapus produk: " + e.getMessage(), e);
        }
    }

    // ── Validasi ─────────────────────────────────────────────────────────────

    private void validateProduk(ProdukItem produk) {
        if (produk.getNama() == null || produk.getNama().trim().isEmpty())
            throw new RuntimeException("Nama produk tidak boleh kosong.");
        if (produk.getNama().trim().length() < 2)
            throw new RuntimeException("Nama produk minimal 2 karakter.");
        if (produk.getNama().trim().length() > 100)
            throw new RuntimeException("Nama produk maksimal 100 karakter.");
        if (produk.getIdKategori() <= 0)
            throw new RuntimeException("Kategori produk tidak valid.");
        if (produk.getHarga() <= 0)
            throw new RuntimeException("Harga produk harus lebih dari 0.");
        if (produk.getStok() < 0)
            throw new RuntimeException("Stok produk tidak boleh negatif.");
    }

    private ProdukItem mapRow(ResultSet rs) throws Exception {
        return new ProdukItem(
            rs.getInt("id_produk"),
            rs.getString("nama_produk"),
            rs.getInt("id_kategori"),
            rs.getString("nama_kategori"),
            rs.getDouble("harga"),
            rs.getInt("stok")
        );
    }
}