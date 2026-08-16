package com.taller.modelo;

/**
 * PILAR POO: ENCAPSULAMIENTO -> la contrasena nunca se expone en texto
 * claro fuera de la clase; solo se compara internamente (verificarClave).
 */
public class Usuario {

    private int id;
    private String username;
    private String claveHash;
    private RolUsuario rol;
    private Integer personaId; // referencia a Cliente o Mecanico (puede ser null para admin)

    public Usuario(int id, String username, String claveHash, RolUsuario rol, Integer personaId) {
        this.id = id;
        this.username = username;
        this.claveHash = claveHash;
        this.rol = rol;
        this.personaId = personaId;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    // El hash solo se expone para que el DAO lo persista; nunca se compara fuera de esta clase.
    public String getClaveHash() {
        return claveHash;
    }

    public boolean verificarClave(String claveIngresada) {
        return this.claveHash.equals(com.taller.util.SeguridadUtil.hashClave(claveIngresada));
    }
}
