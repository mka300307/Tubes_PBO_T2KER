package com.mycompany.tubes_kasir_f2ker.Services;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Menangani proses hashing password menggunakan BCrypt.
 */
public class PasswordHasher {

    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
