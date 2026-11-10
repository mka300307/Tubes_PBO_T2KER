/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tubes_kasir_f2ker.Validator;

/**
 *
 * @author bapti
 */
public interface IValidator<T> {
    void validate(T target) throws RuntimeException;
}
