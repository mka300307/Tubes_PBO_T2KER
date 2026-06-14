/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.UserItem;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<UserItem> getAllUser() {
        List<UserItem> list = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users ORDER BY id ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                list.add(new UserItem(rs.getInt("id"), rs.getString("username"), rs.getString("role")));
        } catch (Exception e) {
            throw new RuntimeException("Gagal load user: " + e.getMessage(), e);
        }
        return list;
    }

    public List<UserItem> searchUser(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return getAllUser();
        List<UserItem> list = new ArrayList<>();
        String sql = "SELECT id, username, role FROM users WHERE username LIKE ? ORDER BY id ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new UserItem(rs.getInt("id"), rs.getString("username"), rs.getString("role")));
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal search user: " + e.getMessage(), e);
        }
        return list;
    }

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

    public void addUser(UserItem user) {
        validateUser(user.getUsername(), user.getPassword());
        if (isUsernameTaken(user.getUsername()))
            throw new RuntimeException("Username sudah digunakan!");

        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        String sql  = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername().trim());
            ps.setString(2, hash);
            ps.setString(3, user.getRole());
            ps.executeUpdate();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal tambah user: " + e.getMessage(), e);
        }
    }

    public void updateUser(UserItem user, String newPassword) {
        if (user.getId() <= 0)
            throw new RuntimeException("ID user tidak valid.");
        validateUsername(user.getUsername());

        try (Connection conn = getConnection()) {
            PreparedStatement ps;
            if (newPassword != null && !newPassword.isEmpty()) {
                validatePassword(newPassword);
                String hash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                ps = conn.prepareStatement(
                    "UPDATE users SET username = ?, password = ?, role = ? WHERE id = ?");
                ps.setString(1, user.getUsername().trim());
                ps.setString(2, hash);
                ps.setString(3, user.getRole());
                ps.setInt(4, user.getId());
            } else {
                ps = conn.prepareStatement(
                    "UPDATE users SET username = ?, role = ? WHERE id = ?");
                ps.setString(1, user.getUsername().trim());
                ps.setString(2, user.getRole());
                ps.setInt(3, user.getId());
            }
            ps.executeUpdate();
            ps.close();
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal update user: " + e.getMessage(), e);
        }
    }

    public void deleteUser(int id) {
        if (id <= 0)
            throw new RuntimeException("ID user tidak valid.");
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Gagal hapus user: " + e.getMessage(), e);
        }
    }

    // ── Validasi ─────────────────────────────────────────────────────────────

    private void validateUser(String username, String password) {
        validateUsername(username);
        validatePassword(password);
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty())
            throw new RuntimeException("Username tidak boleh kosong.");
        if (username.trim().length() < 3)
            throw new RuntimeException("Username minimal 3 karakter.");
        if (username.trim().length() > 50)
            throw new RuntimeException("Username maksimal 50 karakter.");
        if (!username.trim().matches("[a-zA-Z0-9_]+"))
            throw new RuntimeException("Username hanya boleh mengandung huruf, angka, dan underscore.");
    }

    private void validatePassword(String password) {
        if (password == null || password.isEmpty())
            throw new RuntimeException("Password tidak boleh kosong.");
        if (password.length() < 6)
            throw new RuntimeException("Password minimal 6 karakter.");
    }

    private boolean isUsernameTaken(String username) {
        String sql = "SELECT id FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            throw new RuntimeException("Gagal cek username: " + e.getMessage(), e);
        }
    }
}