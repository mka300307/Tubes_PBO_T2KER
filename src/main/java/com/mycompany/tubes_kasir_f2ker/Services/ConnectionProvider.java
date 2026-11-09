/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Services;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author attau
 */
public interface ConnectionProvider {

    Connection getConnection() throws SQLException;
}