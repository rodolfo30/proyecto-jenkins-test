package com.iessanalberto.dam1.ray_barber_shop.repositories;

import com.iessanalberto.dam1.ray_barber_shop.models.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginRepository {
    public static Usuario login(String user, String password) throws Exception {
        try (PreparedStatement loginStatement = ConnectionDB.connect().prepareStatement(
                "SELECT * FROM USUARIOS WHERE user = ? AND password = ?")) {
            // Completamos los ? con los datos correspondientes
            loginStatement.setString(1, user);
            loginStatement.setString(2, password);
            // Ejecutamos la sentencia SQL
            ResultSet resultSet = loginStatement.executeQuery();
            if (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getInt("id_user"),
                        resultSet.getString("user"),
                        resultSet.getString("password"),
                        resultSet.getString("rol")
                );
                resultSet.close();
                return usuario;
            } else {
                throw new Exception("Usuario/Contraseña no encontrados");
            }


        } catch (SQLException exception) {
            throw new Exception("Error en la base de datos. \n" +
                    "Informacion del error: " + exception.getMessage());
        }
    }

    public static void insertUser(String user, String password) throws Exception {
        try (PreparedStatement insertStament = ConnectionDB.connect().prepareStatement(
                "INSERT INTO USUARIOS (user,password,rol) Values (?,?,?)"
        )) {
            insertStament.setString(1, user);
            insertStament.setString(2, password);
            insertStament.setString(3, "Usuario");
            insertStament.executeUpdate();
        } catch (Exception exception) {
            throw new Exception("Error en la base de datos\n" +
                    "Informacion del error: " + exception.getMessage());
        }
    }

    public static boolean userExists(String user) throws Exception {
        try (PreparedStatement preparedStatement = ConnectionDB.connect().prepareStatement("SELECT * FROM USUARIOS WHERE user = ?")) {
            // Completamos los ? con los datos correspondientes
            preparedStatement.setString(1, user);
            // Ejecutamos la sentencia SQL
            ResultSet resultSet = preparedStatement.executeQuery();
            // Comprobamos si esta o no y lo pasamos a una variable para cerrar la conexion
            boolean result = resultSet.next();
            resultSet.close();
            return result;
        }catch (Exception exception) {
            throw new Exception("Error en la base de datos\n" +
                    "Informacion del error: " + exception.getMessage());
        }
    }
}
