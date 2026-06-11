/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.UserItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ─── GET ALL ───────────────────────────────────────────────
    public List<UserItem> getAllUser() {
        List<UserItem> list = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users ORDER BY id ASC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new UserItem(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role")
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal load user: " + e.getMessage(), e);
        }
        return list;
    }

    // ─── SEARCH ────────────────────────────────────────────────
    public List<UserItem> searchUser(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllUser();

        List<UserItem> list = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users "
                   + "WHERE username LIKE ? ORDER BY id ASC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new UserItem(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("role")
                    ));
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal search user: " + e.getMessage(), e);
        }
        return list;
    }

    // ─── GET ID BY USERNAME ────────────────────────────────────
    public int getIdByUsername(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id");
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal ambil id user: " + e.getMessage(), e);
        }
        return -1;
    }

    // ─── CEK USERNAME DUPLIKAT ─────────────────────────────────
    public boolean isUsernameTaken(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (Exception e) {
            throw new RuntimeException("Gagal cek username: " + e.getMessage(), e);
        }
    }

    // ─── INSERT ────────────────────────────────────────────────
    public void addUser(UserItem user) {
        if (isUsernameTaken(user.getUsername())) {
            throw new RuntimeException("Username sudah digunakan!");
        }

        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String sql  = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, hash);
            ps.setString(3, user.getRole());
            ps.executeUpdate();

        } catch (RuntimeException e) {
            throw e; // lempar ulang pesan duplikat
        } catch (Exception e) {
            throw new RuntimeException("Gagal tambah user: " + e.getMessage(), e);
        }
    }

    // ─── UPDATE (dengan atau tanpa ganti password) ─────────────
    public void updateUser(UserItem user, String newPassword) {
        try (Connection conn = getConnection()) {

            PreparedStatement ps;
            if (newPassword != null && !newPassword.isEmpty()) {
                String hash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                ps = conn.prepareStatement(
                    "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?"
                );
                ps.setString(1, user.getUsername());
                ps.setString(2, hash);
                ps.setString(3, user.getRole());
                ps.setInt(4, user.getId());
            } else {
                ps = conn.prepareStatement(
                    "UPDATE users SET username = ?, role = ? WHERE id = ?"
                );
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getRole());
                ps.setInt(3, user.getId());
            }

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            throw new RuntimeException("Gagal update user: " + e.getMessage(), e);
        }
    }

    // ─── DELETE ────────────────────────────────────────────────
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Gagal hapus user: " + e.getMessage(), e);
        }
    }
}
