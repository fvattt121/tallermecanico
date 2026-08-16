package com.taller.modelo;

/**
 * PILAR POO: HERENCIA -> Cliente extiende Persona y reutiliza sus atributos
 * (id, nombre, telefono, email) y su comportamiento comun.
 */
public class Cliente extends Persona {

    private String direccion;

    public Cliente(int id, String nombre, String telefono, String email, String direccion) {
        super(id, nombre, telefono, email);
        this.direccion = direccion == null ? "" : direccion.trim();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion == null ? "" : direccion.trim();
    }

    // PILAR POO: POLIMORFISMO -> sobreescritura especifica para Cliente.
    @Override
    public String getRolDescriptivo() {
        return "Cliente";
    }
}
