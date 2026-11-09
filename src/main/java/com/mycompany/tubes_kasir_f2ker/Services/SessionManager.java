/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

import com.mycompany.tubes_kasir_f2ker.model.SessionUser;
import com.mycompany.tubes_kasir_f2ker.model.UserItem;

/**
 *
 * @author attau
 */
public class SessionManager {

    public void setSession(UserItem user) {
        SessionUser.id = user.getId();
        SessionUser.username = user.getUsername();
        SessionUser.role = user.getRole();
    }

    public void clearSession() {
        SessionUser.id = 0;
        SessionUser.username = null;
        SessionUser.role = null;
    }
}
