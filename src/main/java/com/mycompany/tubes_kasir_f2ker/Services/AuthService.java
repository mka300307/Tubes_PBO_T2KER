package com.mycompany.tubes_kasir_f2ker.Services;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author attau
 */

import com.mycompany.tubes_kasir_f2ker.model.SessionUser;
import com.mycompany.tubes_kasir_f2ker.model.UserItem;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class AuthService {

    private static final String URL  = "jdbc:mysql://localhost:3306/db_kasir";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public UserItem login(String username, String password) {
        validateInput(username, password);

        String sql = "SELECT id, username, password, role FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) throw new RuntimeException("Username tidak ditemukan!");

                String hashDiDB = rs.getString("password");
                if (!BCrypt.checkpw(password, hashDiDB))
                    throw new RuntimeException("Password salah!");

                UserItem user = new UserItem(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("role")
                );

                // Simpan ke session
                SessionUser.id       = user.getId();
                SessionUser.username = user.getUsername();
                SessionUser.role     = user.getRole();

                return user;
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Gagal koneksi ke database: " + e.getMessage(), e);
        }
    }

    // ── Validasi ─────────────────────────────────────────────────────────────

    private void validateInput(String username, String password) {
        if (username == null || username.trim().isEmpty())
            throw new RuntimeException("Username tidak boleh kosong!");
        if (password == null || password.isEmpty())
            throw new RuntimeException("Password tidak boleh kosong!");
    }
}