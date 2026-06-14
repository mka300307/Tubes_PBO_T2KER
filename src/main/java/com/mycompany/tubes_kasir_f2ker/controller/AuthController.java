/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */
import com.mycompany.tubes_kasir_f2ker.Services.AuthService;
import com.mycompany.tubes_kasir_f2ker.model.UserItem;

public class AuthController {

    private final AuthService service = new AuthService();

    public UserItem login(String username, String password) {
        return service.login(username, password);
    }
}