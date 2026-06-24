/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.DetailTransaksiItem;
import com.mycompany.tubes_kasir_f2ker.model.ProdukItem;
import com.mycompany.tubes_kasir_f2ker.model.TransaksiItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransaksiService {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<ProdukItem> getProdukAktif() {
        List<ProdukItem> list = new ArrayList<>();
        String sql = "SELECT id_produk, nama_produk, harga, stok FROM produk WHERE stok > 0 ORDER BY nama_produk ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new ProdukItem(
                    rs.getInt("id_produk"), rs.getString("nama_produk"),
                    0, "", rs.getDouble("harga"), rs.getInt("stok")
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal load produk: " + e.getMessage(), e);
        }
        return list;
    }

    public List<TransaksiItem> getAllTransaksi() {
        List<TransaksiItem> list = new ArrayList<>();
        String sql = "SELECT t.id_transaksi, t.id_user, u.username, t.total_transaksi, t.tanggal "
                   + "FROM transaksi t JOIN users u ON t.id_user = u.id ORDER BY t.tanggal DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapTransaksi(rs));
        } catch (Exception e) {
            throw new RuntimeException("Gagal load transaksi: " + e.getMessage(), e);
        }
        return list;
    }

    public List<TransaksiItem> getTransaksiByUser(int idUser) {
        if (idUser <= 0)
            throw new RuntimeException("ID user tidak valid.");
        List<TransaksiItem> list = new ArrayList<>();
        String sql = "SELECT t.id_transaksi, t.id_user, u.username, t.total_transaksi, t.tanggal "
                   + "FROM transaksi t JOIN users u ON t.id_user = u.id "
                   + "WHERE t.id_user = ? ORDER BY t.tanggal DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapTransaksi(rs));
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal load transaksi: " + e.getMessage(), e);
        }
        return list;
    }

    public List<DetailTransaksiItem> getDetailTransaksi(int idTransaksi) {
        if (idTransaksi <= 0)
            throw new RuntimeException("ID transaksi tidak valid.");
        List<DetailTransaksiItem> list = new ArrayList<>();
        String sql = "SELECT dt.id_detail, dt.id_transaksi, dt.id_produk, "
                   + "p.nama_produk, p.harga, dt.jumlah, dt.subtotal "
                   + "FROM detail_transaksi dt JOIN produk p ON dt.id_produk = p.id_produk "
                   + "WHERE dt.id_transaksi = ? ORDER BY dt.id_detail ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DetailTransaksiItem(
                        rs.getInt("id_detail"), rs.getInt("id_transaksi"),
                        rs.getInt("id_produk"), rs.getString("nama_produk"),
                        rs.getDouble("harga"), rs.getInt("jumlah"), rs.getDouble("subtotal")
                    ));
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal load detail transaksi: " + e.getMessage(), e);
        }
        return list;
    }

    public List<DetailTransaksiItem> tambahItem(
            List<DetailTransaksiItem> listSementara,
            List<Integer> listIdProduk,
            ProdukItem produk, int jumlah) {

        if (jumlah <= 0)
            throw new IllegalArgumentException("Jumlah harus lebih dari 0!");
        if (jumlah > produk.getStok())
            throw new IllegalArgumentException("Stok tidak cukup! Stok tersedia: " + produk.getStok());

        for (int i = 0; i < listIdProduk.size(); i++) {
            if (listIdProduk.get(i) == produk.getId()) {
                int jumlahBaru = listSementara.get(i).getJumlah() + jumlah;
                if (jumlahBaru > produk.getStok())
                    throw new IllegalArgumentException("Total jumlah melebihi stok! Stok tersedia: " + produk.getStok());
                listSementara.get(i).setJumlah(jumlahBaru);
                listSementara.get(i).setSubtotal(jumlahBaru * produk.getHarga());
                return listSementara;
            }
        }

        listSementara.add(new DetailTransaksiItem(
            0, produk.getId(), produk.getNama(),
            produk.getHarga(), jumlah, jumlah * produk.getHarga()
        ));
        listIdProduk.add(produk.getId());
        return listSementara;
    }

    public List<DetailTransaksiItem> updateItem(
            List<DetailTransaksiItem> listSementara,
            List<Integer> listIdProduk,
            int selectedRow, ProdukItem produkBaru, int jumlahBaru) {

        if (selectedRow < 0 || selectedRow >= listSementara.size())
            throw new IllegalArgumentException("Pilih item dari tabel terlebih dahulu!");
        if (jumlahBaru <= 0)
            throw new IllegalArgumentException("Jumlah harus lebih dari 0!");
        if (jumlahBaru > produkBaru.getStok())
            throw new IllegalArgumentException("Stok tidak cukup! Stok tersedia: " + produkBaru.getStok());

        DetailTransaksiItem item = listSementara.get(selectedRow);
        item.setIdProduk(produkBaru.getId());
        item.setNamaProduk(produkBaru.getNama());
        item.setHargaSatuan(produkBaru.getHarga());
        item.setJumlah(jumlahBaru);
        item.setSubtotal(jumlahBaru * produkBaru.getHarga());
        listIdProduk.set(selectedRow, produkBaru.getId());
        return listSementara;
    }

    public List<DetailTransaksiItem> deleteItem(
            List<DetailTransaksiItem> listSementara,
            List<Integer> listIdProduk, int selectedRow) {

        if (selectedRow < 0 || selectedRow >= listSementara.size())
            throw new IllegalArgumentException("Pilih item dari tabel terlebih dahulu!");
        listSementara.remove(selectedRow);
        listIdProduk.remove(selectedRow);
        return listSementara;
    }

    public double hitungTotal(List<DetailTransaksiItem> listSementara) {
        return listSementara.stream().mapToDouble(DetailTransaksiItem::getSubtotal).sum();
    }

    public double hitungKembalian(double uangBayar, double total) {
        if (uangBayar < total)
            throw new IllegalArgumentException(
                "Uang pembeli kurang! Total belanja: Rp " + String.format("%,.0f", total));
        return uangBayar - total;
    }

    public int simpanTransaksi(int idUser, double total, List<DetailTransaksiItem> listDetail) {
        if (idUser <= 0)
            throw new RuntimeException("ID user tidak valid.");
        if (listDetail == null || listDetail.isEmpty())
            throw new RuntimeException("Keranjang belanja kosong!");
        if (total <= 0)
            throw new RuntimeException("Total transaksi tidak valid.");

        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            PreparedStatement psTransaksi = conn.prepareStatement(
                "INSERT INTO transaksi (id_user, total_transaksi, tanggal) VALUES (?, ?, NOW())",
                Statement.RETURN_GENERATED_KEYS
            );
            psTransaksi.setInt(1, idUser);
            psTransaksi.setDouble(2, total);
            psTransaksi.executeUpdate();

            ResultSet rs = psTransaksi.getGeneratedKeys();
            if (!rs.next()) throw new SQLException("Gagal mengambil ID transaksi.");
            int idTransaksi = rs.getInt(1);

            PreparedStatement psDetail = conn.prepareStatement(
                "INSERT INTO detail_transaksi (id_transaksi, id_produk, jumlah, subtotal) VALUES (?, ?, ?, ?)"
            );
            PreparedStatement psStok = conn.prepareStatement(
                "UPDATE produk SET stok = stok - ? WHERE id_produk = ?"
            );

            for (DetailTransaksiItem detail : listDetail) {
                psDetail.setInt(1, idTransaksi);
                psDetail.setInt(2, detail.getIdProduk());
                psDetail.setInt(3, detail.getJumlah());
                psDetail.setDouble(4, detail.getSubtotal());
                psDetail.addBatch();

                psStok.setInt(1, detail.getJumlah());
                psStok.setInt(2, detail.getIdProduk());
                psStok.addBatch();
            }

            psDetail.executeBatch();
            psStok.executeBatch();
            conn.commit();
            return idTransaksi;

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            throw new RuntimeException("Gagal simpan transaksi: " + e.getMessage(), e);
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    public void deleteTransaksi(int idTransaksi) {
        if (idTransaksi <= 0)
            throw new RuntimeException("ID transaksi tidak valid.");
        String sql = "DELETE FROM transaksi WHERE id_transaksi = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTransaksi);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Gagal hapus transaksi: " + e.getMessage(), e);
        }
    }

    public List<Object[]> getDashboardTransaksi(String keyword) {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT t.id_transaksi, u.username, SUM(dt.jumlah) AS total_barang, "
                   + "t.total_transaksi, t.tanggal "
                   + "FROM transaksi t "
                   + "JOIN users u ON t.id_user = u.id "
                   + "JOIN detail_transaksi dt ON t.id_transaksi = dt.id_transaksi "
                   + "WHERE u.username LIKE ? "
                   + "GROUP BY t.id_transaksi ORDER BY t.tanggal DESC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (keyword == null ? "" : keyword) + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Object[]{
                        rs.getInt("id_transaksi"),
                        rs.getString("username"),
                        rs.getInt("total_barang"),
                        rs.getDouble("total_transaksi"),
                        rs.getString("tanggal")
                    });
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal load dashboard transaksi: " + e.getMessage(), e);
        }
        return list;
    }

    private TransaksiItem mapTransaksi(ResultSet rs) throws Exception {
        return new TransaksiItem(
            rs.getInt("id_transaksi"), rs.getInt("id_user"),
            rs.getString("username"), rs.getDouble("total_transaksi"), rs.getString("tanggal")
        );
    }
    
    
}
