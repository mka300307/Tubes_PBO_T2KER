/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.model;

/**
 *
 * @author attau
 */

public class UserItem {

    private int id;
    private String username;
    private String password; // hashed
    private String role;

    // Constructor lengkap (dari DB)
    public UserItem(int id, String username, String role) {
        this.id       = id;
        this.username = username;
        this.role     = role;
    }

    // Constructor untuk insert baru
    public UserItem(String username, String password, String role) {
        this.id       = 0;
        this.username = username;
        this.password = password;
        this.role     = role;
    }

    // Getter
    public int getId()          { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole()     { return role; }

    // Setter
    public void setId(int id)              { this.id = id; }
    public void setUsername(String u)      { this.username = u; }
    public void setPassword(String p)      { this.password = p; }
    public void setRole(String r)          { this.role = r; }

    @Override
    public String toString() {
        return username;
    }
}
