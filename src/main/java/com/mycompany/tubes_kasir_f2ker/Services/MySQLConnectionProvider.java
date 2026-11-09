/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author attau
 */
public class MySQLConnectionProvider implements ConnectionProvider {
    private final String url;
    private final String username;
    private final String password;

    public MySQLConnectionProvider(
            String url,
            String username,
            String password) {

        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
            url, username, password
        );
    }
}
