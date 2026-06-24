/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.KategoriItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KategoriService {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<KategoriItem> getAllKategori() {
        List<KategoriItem> list = new ArrayList<>();
        String sql = "SELECT id_kategori, nama_kategori FROM kategori_produk ORDER BY id_kategori ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                list.add(new KategoriItem(rs.getInt("id_kategori"), rs.getString("nama_kategori")));
        } catch (Exception e) {
            throw new RuntimeException("Gagal load kategori: " + e.getMessage(), e);
        }
        return list;
    }

    public List<KategoriItem> searchKategori(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllKategori();
        List<KategoriItem> list = new ArrayList<>();
        String sql = "SELECT id_kategori, nama_kategori FROM kategori_produk WHERE nama_kategori LIKE ? ORDER BY id_kategori ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new KategoriItem(rs.getInt("id_kategori"), rs.getString("nama_kategori")));
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal search kategori: " + e.getMessage(), e);
        }
        return list;
    }

    public void addKategori(KategoriItem kategori) {
        validateNama(kategori.getNama());
        if (isNamaTaken(kategori.getNama()))
            throw new RuntimeException("Kategori '" + kategori.getNama() + "' sudah ada!");

        String sql = "INSERT INTO kategori_produk (nama_kategori) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kategori.getNama().trim());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal tambah kategori: " + e.getMessage(), e);
        }
    }

    public void updateKategori(KategoriItem kategori) {
        if (kategori.getId() <= 0)
            throw new RuntimeException("ID kategori tidak valid.");
        validateNama(kategori.getNama());

        String sql = "UPDATE kategori_produk SET nama_kategori = ? WHERE id_kategori = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kategori.getNama().trim());
            ps.setInt(2, kategori.getId());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal update kategori: " + e.getMessage(), e);
        }
    }

    public void deleteKategori(int id) {
        if (id <= 0)
            throw new RuntimeException("ID kategori tidak valid.");

        String sql = "DELETE FROM kategori_produk WHERE id_kategori = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Gagal hapus kategori: " + e.getMessage(), e);
        }
    }

    public int getIdByNama(String namaKategori) {
        String sql = "SELECT id_kategori FROM kategori_produk WHERE nama_kategori = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaKategori);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_kategori");
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal ambil id kategori: " + e.getMessage(), e);
        }
        return -1;
    }

    // ── Validasi ─────────────────────────────────────────────────────────────

    private void validateNama(String nama) {
        if (nama == null || nama.trim().isEmpty())
            throw new RuntimeException("Nama kategori tidak boleh kosong.");
        if (nama.trim().length() < 2)
            throw new RuntimeException("Nama kategori minimal 2 karakter.");
        if (nama.trim().length() > 100)
            throw new RuntimeException("Nama kategori maksimal 100 karakter.");
    }

    private boolean isNamaTaken(String nama) {
        String sql = "SELECT id_kategori FROM kategori_produk WHERE nama_kategori = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nama.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal cek nama kategori: " + e.getMessage(), e);
        }
    }
}