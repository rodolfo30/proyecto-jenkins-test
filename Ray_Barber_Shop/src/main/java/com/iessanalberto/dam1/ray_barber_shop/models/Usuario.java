package com.iessanalberto.dam1.ray_barber_shop.models;

public class Usuario {
    private int id_user;
    private String user;
    private String password;
    private String rol;

    public Usuario(int id_user, String user, String password, String rol) {
        this.id_user = id_user;
        this.user = user;
        this.password = password;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
}
