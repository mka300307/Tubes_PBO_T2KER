/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

import com.mycompany.tubes_kasir_f2ker.model.UserItem;
/**
 *
 * @author bapti
 */
public interface IAuthService {
    UserItem login(String username, String password);
}