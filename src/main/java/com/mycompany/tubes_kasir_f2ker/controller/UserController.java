/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.controller;

/**
 *
 * @author attau
 */


import com.mycompany.tubes_kasir_f2ker.Services.UserService;
import com.mycompany.tubes_kasir_f2ker.model.UserItem;
import java.util.List;

public class UserController {

    private final UserService service = new UserService();

    public List<UserItem> getAllUser() {
        return service.getAllUser();
    }

    public List<UserItem> searchUser(String keyword) {
        return service.searchUser(keyword);
    }

    public int getIdByUsername(String username) {
        return service.getIdByUsername(username);
    }

    public void addUser(UserItem user) {
        service.addUser(user);
    }

    public void updateUser(UserItem user, String newPassword) {
        service.updateUser(user, newPassword);
    }

    public void deleteUser(int id) {
        service.deleteUser(id);
    }
}
