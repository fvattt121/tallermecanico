package com.taller.modelo;

public enum EstatusVehiculo {
    EN_REVISION("En revision"),
    ESPERA_PIEZAS("En espera de piezas"),
    LISTO("Listo");

    private final String etiqueta;

    EstatusVehiculo(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
