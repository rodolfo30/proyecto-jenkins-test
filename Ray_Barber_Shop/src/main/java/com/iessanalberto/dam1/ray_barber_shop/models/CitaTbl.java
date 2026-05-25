package com.iessanalberto.dam1.ray_barber_shop.models;

public class CitaTbl {
    private int id_cita;
    private String fecha;
    private String hora;
    private String cliente;
    private String descripcion;


    // Constructor con ID (para leer de BD)
    public CitaTbl(int id_cita ,String fecha, String hora, String cliente, String descripcion) {
        this.id_cita = id_cita;
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.descripcion = descripcion;
    }
    // Constructor sin ID (para búsquedas o inserciones)
    public CitaTbl(String fecha, String hora, String cliente, String descripcion) {
        this.fecha = fecha;
        this.hora = hora;
        this.cliente = cliente;
        this.descripcion = descripcion;
}
        public int getId_cita() {return id_cita;}

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
