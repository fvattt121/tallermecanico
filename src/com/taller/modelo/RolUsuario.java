package com.taller.modelo;

/**
 * Jerarquía de roles del Taller Hotwheels Tam.
 *
 * SUPERADMIN : Control total. Único que puede crear otros SUPERADMIN.
 *              Acceso completo: usuarios, bitácora, personas, órdenes, vehículos, refacciones.
 * GERENTE    : Gestión operativa completa. Puede crear MECANICO, EMPLEADO y CLIENTE.
 *              NO puede crear ni eliminar SUPERADMIN.
 * MECANICO   : Ve y gestiona sus propias órdenes asignadas. Puede cambiar estatus
 *              de órdenes y agregar ítems de presupuesto. No puede eliminar.
 * EMPLEADO   : Recepcionista. Puede registrar vehículos, crear órdenes, ver estatus.
 *              Puede crear usuarios tipo CLIENTE. No puede eliminar registros.
 * CLIENTE    : Solo puede ver el estatus y comentarios de sus propios vehículos.
 *              Acceso de solo lectura a su información.
 */
public enum RolUsuario {
    SUPERADMIN,
    GERENTE,
    MECANICO,
    EMPLEADO,
    CLIENTE
}
