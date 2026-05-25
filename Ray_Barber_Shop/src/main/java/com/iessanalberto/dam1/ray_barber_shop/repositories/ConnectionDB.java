package com.iessanalberto.dam1.ray_barber_shop.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private final static String URL = "jdbc:mysql://localhost:3309/PelosBD?serverTimezone=UTC";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";

    public static Connection connect() throws Exception {
        try {
            // No guardamos la conexión en una variable estática para evitar conexiones cerradas
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            throw new Exception("Error al conectar con la BD: " + exception.getMessage());
        }
    }
}
