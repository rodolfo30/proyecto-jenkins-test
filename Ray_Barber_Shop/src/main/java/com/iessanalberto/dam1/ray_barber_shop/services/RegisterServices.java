package com.iessanalberto.dam1.ray_barber_shop.services;

public class RegisterServices {

    public void register(String nombre, String usuario, String password, String confirmPassword) throws Exception {
        // En el registro de personal, el nombre también es obligatorio
        if (nombre.isBlank() || usuario.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            throw new Exception("Todos los campos del formulario de registro son obligatorios.");
        }
        if (!password.equals(confirmPassword)) {
            throw new Exception("Las contraseñas no coinciden.");
        }
        if (password.length() < 6) {
            throw new Exception("La contraseña debe ser más segura (mínimo 6 caracteres).");
        }
    }
}