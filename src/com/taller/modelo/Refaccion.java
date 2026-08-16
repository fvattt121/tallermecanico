package com.taller.modelo;

public class Refaccion {
    private int id;
    private String nombre;
    private double precioUnitario;
    private int stock;
    private String rutaFoto;

    private boolean activo = true;

    public Refaccion(int id, String nombre, double precioUnitario, int stock) {
        this(id, nombre, precioUnitario, stock, null, true);
    }

    public Refaccion(int id, String nombre, double precioUnitario, int stock, String rutaFoto) {
        this(id, nombre, precioUnitario, stock, rutaFoto, true);
    }

    public Refaccion(int id, String nombre, double precioUnitario, int stock, String rutaFoto, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        setPrecioUnitario(precioUnitario);
        setStock(stock);
        this.rutaFoto = rutaFoto;
        this.activo = activo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario < 0) throw new IllegalArgumentException("El precio no puede ser negativo");
        this.precioUnitario = precioUnitario;
    }

    public int getStock() { return stock; }
    public void setStock(int stock) {
        if (stock < 0) throw new IllegalArgumentException("El stock no puede ser negativo");
        this.stock = stock;
    }

    public String getRutaFoto() { return rutaFoto; }
    public void setRutaFoto(String rutaFoto) { this.rutaFoto = rutaFoto; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + " ($" + precioUnitario + ") - Stock: " + stock + (activo ? "" : " [Archivado]");
    }
}

