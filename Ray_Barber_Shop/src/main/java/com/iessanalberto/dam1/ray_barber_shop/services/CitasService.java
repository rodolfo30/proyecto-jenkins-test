package com.iessanalberto.dam1.ray_barber_shop.services;

import com.iessanalberto.dam1.ray_barber_shop.models.CitaTbl;
import com.iessanalberto.dam1.ray_barber_shop.repositories.CitasRepository;
import java.time.LocalDate;
import java.util.ArrayList;

public class CitasService {
    private CitasRepository citasRepository = new CitasRepository();

    public ArrayList<CitaTbl> getCitasByFecha(String fecha) throws Exception {
        return citasRepository.buscarCitas(fecha);
    }

    public void crearCita(CitaTbl citaTbl) throws Exception {
        // Validación: Descripción NO se comprueba (puede ir vacía)
        if (citaTbl.getFecha() == null || citaTbl.getFecha().isBlank() ||
                citaTbl.getHora() == null || citaTbl.getHora().isBlank() ||
                citaTbl.getCliente() == null || citaTbl.getCliente().isBlank()) {
            throw new Exception("Error: La fecha, la hora y el nombre del cliente son obligatorios.");
        }

        // Validación de fecha pasada
        LocalDate fechaCita = LocalDate.parse(citaTbl.getFecha());
        if (fechaCita.isBefore(LocalDate.now())) {
            throw new Exception("No puedes programar una cita para una fecha que ya ha pasado.");
        }

        // Validación de duplicados
        if (citasRepository.existeCita(citaTbl.getFecha(), citaTbl.getHora())) {
            throw new Exception("Ya existe una cita programada para esa fecha y hora.");
        }

        citasRepository.insertCita(citaTbl);
    }

    public void updateCita(CitaTbl citaTbl) throws Exception {
        // Validación en edición (Descripción opcional)
        if (citaTbl.getFecha() == null || citaTbl.getFecha().isBlank() ||
                citaTbl.getHora() == null || citaTbl.getHora().isBlank() ||
                citaTbl.getCliente() == null || citaTbl.getCliente().isBlank()) {
            throw new Exception("Error: No puedes dejar la fecha, la hora o el cliente vacíos.");
        }

        if (citasRepository.existeCitaParaEditar(citaTbl.getFecha(), citaTbl.getHora(), citaTbl.getId_cita())) {
            throw new Exception("No se puede actualizar: El horario ya está ocupado por otra cita.");
        }

        citasRepository.updateCita(citaTbl);
    }

    public void deleteCita(int idCita) throws Exception {
        citasRepository.deleteCita(idCita);
    }
}
