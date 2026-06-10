/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker;

/**
 *
 * @author attau
 */
public class SessionUser {
    public static int id;
    public static String username;
    public static String role;

    public static void logout() {
        id = 0;
        username = null;
        role = null;
    }
}