package com.iessanalberto.dam1.ray_barber_shop.services;

import com.iessanalberto.dam1.ray_barber_shop.models.Usuario;
import com.iessanalberto.dam1.ray_barber_shop.repositories.LoginRepository;

public class LoginServices {

    public Usuario login(String user, String password) throws Exception {
        if (user.isBlank() || password.isBlank()) {
            throw new Exception("Debes introducir el usuario y la contraseña para entrar.");
        }
        return LoginRepository.login(user, password);
    }

    public void register(String user, String password, String repeatPassword) throws Exception {
        // Todos los campos obligatorios en registro
        if (user.isBlank() || password.isBlank() || repeatPassword.isBlank()) {
            throw new Exception("Para registrarse, todos los campos son obligatorios.");
        }
        if (!password.equals(repeatPassword)) {
            throw new Exception("Las contraseñas no coinciden.");
        }
        if (password.length() < 6) {
            throw new Exception("La contraseña debe tener al menos 6 caracteres.");
        }
        if (LoginRepository.userExists(user)) {
            throw new Exception("El usuario ya existe en la base de datos.");
        }
        LoginRepository.insertUser(user, password);
    }
}