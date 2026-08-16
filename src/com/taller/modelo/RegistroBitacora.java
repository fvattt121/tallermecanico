package com.taller.modelo;

import java.time.LocalDateTime;

public class RegistroBitacora {
    private int id;
    private Integer usuarioId;
    private String username;
    private LocalDateTime fechaHora;
    private String accion;   // LOGIN, LOGOUT, CREAR, ACTUALIZAR, ELIMINAR
    private String detalle;

    public RegistroBitacora(int id, Integer usuarioId, String username, LocalDateTime fechaHora,
                             String accion, String detalle) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.username = username;
        this.fechaHora = fechaHora;
        this.accion = accion;
        this.detalle = detalle;
    }

    public int getId() { return id; }
    public Integer getUsuarioId() { return usuarioId; }
    public String getUsername() { return username; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getAccion() { return accion; }
    public String getDetalle() { return detalle; }
}
