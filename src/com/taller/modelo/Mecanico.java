package com.taller.modelo;

/**
 * PILAR POO: HERENCIA -> Mecanico extiende Persona.
 */
public class Mecanico extends Persona {

    private String especialidad;
    private boolean disponible;

    public Mecanico(int id, String nombre, String telefono, String email, String especialidad, boolean disponible) {
        super(id, nombre, telefono, email);
        this.especialidad = especialidad == null ? "General" : especialidad.trim();
        this.disponible = disponible;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    // PILAR POO: POLIMORFISMO -> comportamiento distinto al de Cliente.
    @Override
    public String getRolDescriptivo() {
        return "Mecanico (" + especialidad + ")";
    }
}
