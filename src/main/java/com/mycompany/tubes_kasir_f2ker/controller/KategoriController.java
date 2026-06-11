/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.KategoriItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KategoriController {
    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";
    
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
    
    public List<KategoriItem> getAllKategori(){
        List<KategoriItem> list = new ArrayList<>();
        String sql = "SELECT id_kategori, nama_kategori FROM kategori_produk ORDER BY id_kategori ASC ";
        
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {                
                list.add(new KategoriItem(
                        rs.getInt("id_kategori"), 
                        rs.getString("nama_kategori")
                ));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Gagal Load Kategori: " + e.getMessage(), e);
        }
        return list;
    }
    
    public boolean isKategoriNameTaken(String nama_kategori){
        String sql = "SELECT id_kategori FROM kategori_produk WHERE nama_kategori = ?";
        
        try (Connection conn = getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, nama_kategori);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal cek username: " + e.getMessage(), e);
        }
    }
    
    public List<KategoriItem> searchKategori(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllKategori();

        List<KategoriItem> list = new ArrayList<>();
        String sql = "SELECT id_kategori, nama_kategori FROM kategori_produk "
                   + "WHERE nama_kategori LIKE ? ORDER BY id_kategori ASC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new KategoriItem(
                        rs.getInt("id_kategori"),
                        rs.getString("nama_kategori")
                    ));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal search kategori : " + e.getMessage(), e);
        }
        return list;
    }
    
    public void addKategori(KategoriItem kategori){
        if(isKategoriNameTaken(kategori.getNama())){
            throw new RuntimeException("Kategori sudah ada!");
        }
        
        String sql = "INSERT INTO kategori_produk (nama_kategori) VALUES (?)";
        
        try (Connection  conn = getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kategori.getNama());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw e; 
        } catch (Exception e) {
            throw new RuntimeException("Gagal tambah Kategori: " + e.getMessage(), e);
        }
    }
    
    public void updateKategori(KategoriItem kategori){
        String sql = "UPDATE kategori_produk SET nama_kategori = ? WHERE id_kategori = ?";
        
        try (Connection conn = getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kategori.getNama());
            ps.setInt(2, kategori.getId());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            throw new RuntimeException("Gagal update Kategori: " + e.getMessage(), e);
        }
    }
    
    public void deleteKategori(int id) {
        String sql = "DELETE FROM kategori_produk WHERE id_kategori = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Gagal hapus kategori: " + e.getMessage(), e);
        }
    }

    public int getIdByUsername(String namaKategori) {
        String sql = "SELECT id_kategori FROM kategori_produk WHERE nama_kategori = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, namaKategori);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_kategori");
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal ambil id user: " + e.getMessage(), e);
        }
        return -1;   
    }
}
