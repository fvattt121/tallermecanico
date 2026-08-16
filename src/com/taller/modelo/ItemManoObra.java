package com.taller.modelo;

// PILAR POO: HERENCIA
public class ItemManoObra extends ItemPresupuesto {

    private double costoFijo;
    private double horas;

    public ItemManoObra(int id, int ordenId, String descripcion, double costoFijo, double horas) {
        super(id, ordenId, descripcion);
        this.costoFijo = costoFijo;
        this.horas = horas;
    }

    public double getCostoFijo() { return costoFijo; }
    public double getHoras() { return horas; }

    // PILAR POO: POLIMORFISMO -> subtotal = tarifa por hora * horas trabajadas
    @Override
    public double calcularSubtotal() {
        return costoFijo * horas;
    }

    @Override
    public String getTipo() {
        return "Mano de obra";
    }
}
