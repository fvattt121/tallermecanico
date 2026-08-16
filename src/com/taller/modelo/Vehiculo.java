package com.taller.modelo;

public class Vehiculo {
    private int id;
    private String placas;
    private String marca;
    private String modelo;
    private int anio;
    private String color;
    private int clienteId;
    private EstatusVehiculo estatus;
    private String rutaFoto; // inventario visual: ruta de imagen del vehiculo

    private boolean activo = true;

    public Vehiculo(int id, String placas, String marca, String modelo, int anio,
                     String color, int clienteId, EstatusVehiculo estatus, String rutaFoto) {
        this(id, placas, marca, modelo, anio, color, clienteId, estatus, rutaFoto, true);
    }

    public Vehiculo(int id, String placas, String marca, String modelo, int anio,
                     String color, int clienteId, EstatusVehiculo estatus, String rutaFoto, boolean activo) {
        this.id = id;
        setPlacas(placas);
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.color = color;
        this.clienteId = clienteId;
        this.estatus = estatus;
        this.rutaFoto = rutaFoto;
        this.activo = activo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getPlacas() { return placas; }
    public void setPlacas(String placas) {
        if (placas == null || placas.trim().isEmpty()) {
            throw new IllegalArgumentException("Las placas son obligatorias");
        }
        this.placas = placas.trim().toUpperCase();
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public EstatusVehiculo getEstatus() { return estatus; }
    public void setEstatus(EstatusVehiculo estatus) { this.estatus = estatus; }

    public String getRutaFoto() { return rutaFoto; }
    public void setRutaFoto(String rutaFoto) { this.rutaFoto = rutaFoto; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return placas + " - " + marca + " " + modelo + " (" + anio + ")" + (activo ? "" : " [Archivado]");
    }
}

