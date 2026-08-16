package com.taller.modelo;

/**
 * PILAR POO: ABSTRACCION -> representa cualquier concepto que se pueda
 * cobrar dentro de un presupuesto (una refaccion o mano de obra), sin
 * importar de que tipo especifico se trate.
 */
public abstract class ItemPresupuesto {

    private int id;
    private int ordenId;
    private String descripcion;

    public ItemPresupuesto(int id, int ordenId, String descripcion) {
        this.id = id;
        this.ordenId = ordenId;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrdenId() { return ordenId; }
    public void setOrdenId(int ordenId) { this.ordenId = ordenId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    /**
     * PILAR POO: POLIMORFISMO -> cada subtipo calcula su subtotal de forma
     * distinta (refaccion = precio * cantidad; mano de obra = tarifa fija).
     */
    public abstract double calcularSubtotal();

    /** Etiqueta del tipo de item, usada en la GUI. POLIMORFISMO tambien aqui. */
    public abstract String getTipo();
}
