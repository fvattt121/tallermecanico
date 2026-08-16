package com.taller.modelo;

// PILAR POO: HERENCIA
public class ItemRefaccion extends ItemPresupuesto {

    private int refaccionId;
    private double precioUnitario;
    private int cantidad;

    public ItemRefaccion(int id, int ordenId, int refaccionId, String descripcion,
                          double precioUnitario, int cantidad) {
        super(id, ordenId, descripcion);
        this.refaccionId = refaccionId;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public int getRefaccionId() { return refaccionId; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getCantidad() { return cantidad; }

    // PILAR POO: POLIMORFISMO -> subtotal = precio * cantidad
    @Override
    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    @Override
    public String getTipo() {
        return "Refaccion";
    }
}
