package com.taller.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrdenReparacion {
    private int id;
    private int vehiculoId;
    private Integer mecanicoId;
    private LocalDateTime fechaIngreso;
    private String descripcionProblema;
    private EstatusVehiculo estatus;
    private boolean activo = true; // false = archivada (oculta para roles no-admin)
    private List<ItemPresupuesto> items = new ArrayList<>();

    public OrdenReparacion(int id, int vehiculoId, Integer mecanicoId, LocalDateTime fechaIngreso,
                            String descripcionProblema, EstatusVehiculo estatus) {
        this.id = id;
        this.vehiculoId = vehiculoId;
        this.mecanicoId = mecanicoId;
        this.fechaIngreso = fechaIngreso;
        this.descripcionProblema = descripcionProblema;
        this.estatus = estatus;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getVehiculoId() { return vehiculoId; }

    public Integer getMecanicoId() { return mecanicoId; }
    public void setMecanicoId(Integer mecanicoId) { this.mecanicoId = mecanicoId; }

    public LocalDateTime getFechaIngreso() { return fechaIngreso; }

    public String getDescripcionProblema() { return descripcionProblema; }

    public EstatusVehiculo getEstatus() { return estatus; }
    public void setEstatus(EstatusVehiculo estatus) { this.estatus = estatus; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public List<ItemPresupuesto> getItems() { return items; }
    public void setItems(List<ItemPresupuesto> items) { this.items = items; }

    /**
     * PILAR POO: POLIMORFISMO -> recorre la lista de ItemPresupuesto (tipo
     * abstracto) y llama a calcularSubtotal() sin saber si cada elemento es
     * un ItemRefaccion o un ItemManoObra; el metodo correcto se resuelve
     * en tiempo de ejecucion segun el tipo real del objeto.
     */
    public double calcularTotal() {
        double total = 0;
        for (ItemPresupuesto item : items) {
            total += item.calcularSubtotal();
        }
        return total;
    }
}
