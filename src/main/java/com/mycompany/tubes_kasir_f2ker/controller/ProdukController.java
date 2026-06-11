/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdukController {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ─── GET ALL ───────────────────────────────────────────────
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

            while (rs.next()) {
                list.add(new ProdukItem(
                    rs.getInt("id_produk"),
                    rs.getString("nama_produk"),
                    rs.getInt("id_kategori"),
                    rs.getString("nama_kategori"),
                    rs.getDouble("harga"),
                    rs.getInt("stok")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal load produk: " + e.getMessage(), e);
        }
        return list;
    }

    // ─── SEARCH ────────────────────────────────────────────────
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

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ProdukItem(
                        rs.getInt("id_produk"),
                        rs.getString("nama_produk"),
                        rs.getInt("id_kategori"),
                        rs.getString("nama_kategori"),
                        rs.getDouble("harga"),
                        rs.getInt("stok")
                    ));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal search produk: " + e.getMessage(), e);
        }
        return list;
    }

    // ─── INSERT ────────────────────────────────────────────────
    public void addProduk(ProdukItem produk) {
        String sql = "INSERT INTO produk (nama_produk, id_kategori, harga, stok) "
                   + "VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produk.getNama());
            ps.setInt(2, produk.getIdKategori());
            ps.setDouble(3, produk.getHarga());
            ps.setInt(4, produk.getStok());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Gagal tambah produk: " + e.getMessage(), e);
        }
    }

    // ─── UPDATE ────────────────────────────────────────────────
    public void updateProduk(ProdukItem produk) {
        String sql = "UPDATE produk SET nama_produk = ?, id_kategori = ?, "
                   + "harga = ?, stok = ? WHERE id_produk = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produk.getNama());
            ps.setInt(2, produk.getIdKategori());
            ps.setDouble(3, produk.getHarga());
            ps.setInt(4, produk.getStok());
            ps.setInt(5, produk.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Gagal update produk: " + e.getMessage(), e);
        }
    }

    // ─── DELETE ────────────────────────────────────────────────
    public void deleteProduk(int idProduk) {
        String sql = "DELETE FROM produk WHERE id_produk = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProduk);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Gagal hapus produk: " + e.getMessage(), e);
        }
    }
}