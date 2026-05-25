package com.iessanalberto.dam1.ray_barber_shop.repositories;

import com.iessanalberto.dam1.ray_barber_shop.models.CitaTbl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CitasRepository {
    public ArrayList<CitaTbl> buscarCitas(String fecha) throws Exception {
        ArrayList<CitaTbl> citas = new ArrayList<>();
        // Añadimos el ORDER BY para que aparezcan cronológicamente
        String sql = "SELECT * FROM CITAS WHERE fecha = ? ORDER BY hora ASC";

        try (Connection connection = ConnectionDB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, fecha);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    citas.add(new CitaTbl(
                            resultSet.getInt("id_citas"),
                            resultSet.getString("fecha"),
                            resultSet.getString("hora"),
                            resultSet.getString("cliente"),
                            resultSet.getString("descripcion")
                    ));
                }
            }
        } catch (SQLException exception) {
            throw new Exception("Error al recuperar citas: " + exception.getMessage());
        }
        return citas;
    }

    public void insertCita(CitaTbl citaTbl) throws Exception {
        String sql = "INSERT INTO CITAS (fecha, hora, cliente, descripcion) VALUES (?,?,?,?)";
        // Al poner el Connection dentro del try, se cierra solo al terminar
        try (Connection conn = ConnectionDB.connect();
             PreparedStatement insertStament = conn.prepareStatement(sql)) {

            insertStament.setString(1, citaTbl.getFecha());
            insertStament.setString(2, citaTbl.getHora());
            insertStament.setString(3, citaTbl.getCliente());
            insertStament.setString(4, citaTbl.getDescripcion());
            insertStament.executeUpdate();
        } catch (SQLException exception) {
            throw new Exception("Error en la base de datos: " + exception.getMessage());
        }
    }
    public void updateCita(CitaTbl citaTbl) throws Exception {
        String sql = "UPDATE CITAS SET fecha = ?, hora = ?, cliente = ?, descripcion = ? WHERE id_citas = ?";
        try (Connection connection = ConnectionDB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            // Asignamos los valores a los ?
            preparedStatement.setString(1, citaTbl.getFecha());
            preparedStatement.setString(2, citaTbl.getHora());
            preparedStatement.setString(3, citaTbl.getCliente());
            preparedStatement.setString(4, citaTbl.getDescripcion());
            preparedStatement.setInt(5, citaTbl.getId_cita());
            // Ejecutamos la sentencia
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            throw new Exception("Error en la base de datos. \nInformacion del error: " + exception.getMessage());
        }
    }

    public void deleteCita(int idCita) throws Exception {
        String sql = "DELETE FROM CITAS WHERE id_citas = ?";
        try (Connection connection = ConnectionDB.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1, idCita);
            preparedStatement.executeUpdate();
        }catch (SQLException exception){
            throw new Exception("Error en la base de datos. \nInformacion del error: " + exception.getMessage());
        }
    }
    public boolean existeCita(String fecha, String hora) throws Exception {
        String sql = "SELECT COUNT(*) FROM CITAS WHERE fecha = ? AND hora = ?";
        try (Connection conn = ConnectionDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setString(2, hora);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new Exception("Error al verificar disponibilidad: " + e.getMessage());
        }
        return false;
    }

    public boolean existeCitaParaEditar(String fecha, String hora, int idActual) throws Exception {
        String sql = "SELECT COUNT(*) FROM CITAS WHERE fecha = ? AND hora = ? AND id_citas <> ?";
        try (Connection conn = ConnectionDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setInt(3, idActual);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new Exception("Error al verificar disponibilidad: " + e.getMessage());
        }
        return false;
    }
}
