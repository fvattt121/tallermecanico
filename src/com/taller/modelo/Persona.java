package com.taller.modelo;

/**
 * Clase abstracta que representa a una persona dentro del sistema.
 * PILAR POO: ABSTRACCION -> modela el concepto general "persona" sin
 * permitir instanciarla directamente; solo tiene sentido a traves de
 * sus subclases concretas (Cliente, Mecanico).
 */

public abstract class Persona {

    // PILAR POO: ENCAPSULAMIENTO -> atributos privados, acceso controlado
    // mediante getters/setters con validacion.
    private int id;
    private String nombre;
    private String telefono;
    private String email;

    public Persona(int id, String nombre, String telefono, String email) {
        this.id = id;
        setNombre(nombre);
        setTelefono(telefono);
        setEmail(email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacio");
        }
        this.nombre = nombre.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono == null ? "" : telefono.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    /**
     * Metodo abstracto: cada subclase decide como se describe a si misma.
     * PILAR POO: ABSTRACCION + base para POLIMORFISMO.
     */
    public abstract String getRolDescriptivo();

    /**
     * PILAR POO: POLIMORFISMO -> el comportamiento de resumen() depende
     * de la implementacion concreta de getRolDescriptivo() en cada subclase,
     * aunque se invoque siempre a traves de una referencia Persona.
     */
    public String resumen() {
        return String.format("[%s] %s | Tel: %s | %s", getRolDescriptivo(), nombre, telefono, email);
    }

    @Override
    public String toString() {
        return nombre;
    }
}
