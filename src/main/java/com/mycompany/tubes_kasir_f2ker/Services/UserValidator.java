package com.mycompany.tubes_kasir_f2ker.Services;

/**
 * Menangani validasi data pengguna sebelum diproses oleh UserService.
 */
public class UserValidator {

    public static void validateUser(String username, String password) {
        validateUsername(username);
        validatePassword(password);
    }

    public static void validateUsername(String username) {
        if (username == null || username.trim().isEmpty())
            throw new RuntimeException("Username tidak boleh kosong.");
        if (username.trim().length() < 3)
            throw new RuntimeException("Username minimal 3 karakter.");
        if (username.trim().length() > 50)
            throw new RuntimeException("Username maksimal 50 karakter.");
        if (!username.trim().matches("[a-zA-Z0-9_]+"))
            throw new RuntimeException("Username hanya boleh mengandung huruf, angka, dan underscore.");
    }

    public static void validatePassword(String password) {
        if (password == null || password.isEmpty())
            throw new RuntimeException("Password tidak boleh kosong.");
        if (password.length() < 6)
            throw new RuntimeException("Password minimal 6 karakter.");
    }
}
