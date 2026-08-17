package com.taller.dao;

import com.taller.modelo.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrdenDAO {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    public int crear(OrdenReparacion o) {
        String sql = "INSERT INTO ordenes (vehiculo_id, mecanico_id, fecha_ingreso, descripcion_problema, estatus) " +
                "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, o.getVehiculoId());
            if (o.getMecanicoId() != null) ps.setInt(2, o.getMecanicoId()); else ps.setNull(2, Types.INTEGER);
            ps.setString(3, o.getFechaIngreso().format(FMT));
            ps.setString(4, o.getDescripcionProblema());
            ps.setString(5, o.getEstatus().name());
            ps.executeUpdate();
            int id = ConexionBD.obtenerUltimoIdInsertado();
            bitacoraDAO.registrar("CREAR", "Orden de reparacion creada (id " + id + ") para vehiculo id " + o.getVehiculoId());
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear orden de reparacion", e);
        }
    }

    public void agregarItem(ItemPresupuesto item) {
        String sql = "INSERT INTO items_presupuesto (orden_id, tipo, descripcion, refaccion_id, precio_unitario, cantidad, costo_fijo, horas) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, item.getOrdenId());
            ps.setString(2, item.getTipo());
            ps.setString(3, item.getDescripcion());
            if (item instanceof ItemRefaccion) {
                ItemRefaccion ir = (ItemRefaccion) item;
                ps.setInt(4, ir.getRefaccionId());
                ps.setDouble(5, ir.getPrecioUnitario());
                ps.setInt(6, ir.getCantidad());
                ps.setNull(7, Types.REAL);
                ps.setNull(8, Types.REAL);
            } else if (item instanceof ItemManoObra) {
                ItemManoObra im = (ItemManoObra) item;
                ps.setNull(4, Types.INTEGER);
                ps.setNull(5, Types.REAL);
                ps.setNull(6, Types.INTEGER);
                ps.setDouble(7, im.getCostoFijo());
                ps.setDouble(8, im.getHoras());
            }
            ps.executeUpdate();
            bitacoraDAO.registrar("CREAR", "Item de presupuesto agregado a orden id " + item.getOrdenId() +
                " (" + item.getTipo() + ": " + item.getDescripcion() + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar item de presupuesto", e);
        }
    }

    public List<ItemPresupuesto> listarItems(int ordenId) {
        List<ItemPresupuesto> lista = new ArrayList<>();
        String sql = "SELECT * FROM items_presupuesto WHERE orden_id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, ordenId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if ("Refaccion".equals(rs.getString("tipo"))) {
                        lista.add(new ItemRefaccion(rs.getInt("id"), ordenId, rs.getInt("refaccion_id"),
                            rs.getString("descripcion"), rs.getDouble("precio_unitario"), rs.getInt("cantidad")));
                    } else {
                        lista.add(new ItemManoObra(rs.getInt("id"), ordenId, rs.getString("descripcion"),
                            rs.getDouble("costo_fijo"), rs.getDouble("horas")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar items de presupuesto", e);
        }
        return lista;
    }

    public void actualizarEstatus(int ordenId, EstatusVehiculo nuevoEstatus) {
        String sqlOrden = "UPDATE ordenes SET estatus = ? WHERE id = ?";
        String sqlVehiculo = "UPDATE vehiculos SET estatus = ? WHERE id = (SELECT vehiculo_id FROM ordenes WHERE id = ?)";
        try (PreparedStatement ps1 = ConexionBD.getConexion().prepareStatement(sqlOrden)) {
            ps1.setString(1, nuevoEstatus.name());
            ps1.setInt(2, ordenId);
            ps1.executeUpdate();

            if (nuevoEstatus != EstatusVehiculo.LISTO) {
                try (PreparedStatement ps2 = ConexionBD.getConexion().prepareStatement(sqlVehiculo)) {
                    ps2.setString(1, nuevoEstatus.name());
                    ps2.setInt(2, ordenId);
                    ps2.executeUpdate();
                }
                bitacoraDAO.registrar("ACTUALIZAR", "Orden id " + ordenId + " y vehículo cambiaron de estatus a " + nuevoEstatus.getEtiqueta());
            } else {
                bitacoraDAO.registrar("ACTUALIZAR", "Orden id " + ordenId + " cambió de estatus a " + nuevoEstatus.getEtiqueta());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estatus de orden", e);
        }
    }

    private EstatusVehiculo parseEstatus(String str) {
        if (str == null) return EstatusVehiculo.EN_REVISION;
        try {
            return EstatusVehiculo.valueOf(str.trim());
        } catch (Exception e) {
            return EstatusVehiculo.EN_REVISION;
        }
    }

    public List<OrdenReparacion> listarTodas() {
        List<OrdenReparacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordenes WHERE activo = 1 ORDER BY id DESC";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             while (rs.next()) {
                 Integer mecanicoId = rs.getObject("mecanico_id") != null ? rs.getInt("mecanico_id") : null;
                 OrdenReparacion o = new OrdenReparacion(rs.getInt("id"), rs.getInt("vehiculo_id"), mecanicoId,
                     LocalDateTime.parse(rs.getString("fecha_ingreso"), FMT),
                     rs.getString("descripcion_problema"),
                     parseEstatus(rs.getString("estatus")));
                 o.setActivo(rs.getInt("activo") == 1);
                 o.setItems(listarItems(o.getId()));
                 lista.add(o);
             }
        } catch (SQLException e) {
             throw new RuntimeException("Error al listar ordenes", e);
        }
        return lista;
    }

    public List<OrdenReparacion> listarTodasIncluyendoOcultas() {
        List<OrdenReparacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordenes ORDER BY id DESC";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
             while (rs.next()) {
                 Integer mecanicoId = rs.getObject("mecanico_id") != null ? rs.getInt("mecanico_id") : null;
                 OrdenReparacion o = new OrdenReparacion(rs.getInt("id"), rs.getInt("vehiculo_id"), mecanicoId,
                     LocalDateTime.parse(rs.getString("fecha_ingreso"), FMT),
                     rs.getString("descripcion_problema"),
                     parseEstatus(rs.getString("estatus")));
                 o.setActivo(rs.getInt("activo") == 1);
                 o.setItems(listarItems(o.getId()));
                 lista.add(o);
             }
        } catch (SQLException e) {
             throw new RuntimeException("Error al listar todas las ordenes", e);
        }
        return lista;
    }

    public List<OrdenReparacion> listarPorCliente(int clienteId) {
        List<OrdenReparacion> lista = new ArrayList<>();
        String sql = "SELECT o.* FROM ordenes o JOIN vehiculos v ON o.vehiculo_id = v.id WHERE v.cliente_id = ? AND o.activo = 1 ORDER BY o.id DESC";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer mecanicoId = rs.getObject("mecanico_id") != null ? rs.getInt("mecanico_id") : null;
                    OrdenReparacion o = new OrdenReparacion(rs.getInt("id"), rs.getInt("vehiculo_id"), mecanicoId,
                        LocalDateTime.parse(rs.getString("fecha_ingreso"), FMT),
                        rs.getString("descripcion_problema"),
                        parseEstatus(rs.getString("estatus")));
                    o.setActivo(rs.getInt("activo") == 1);
                    o.setItems(listarItems(o.getId()));
                    lista.add(o);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar órdenes por cliente", e);
        }
        return lista;
    }

    public List<OrdenReparacion> listarPorMecanico(int mecanicoId) {
        List<OrdenReparacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordenes WHERE mecanico_id = ? AND activo = 1 ORDER BY id DESC";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, mecanicoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer mId = rs.getObject("mecanico_id") != null ? rs.getInt("mecanico_id") : null;
                    OrdenReparacion o = new OrdenReparacion(rs.getInt("id"), rs.getInt("vehiculo_id"), mId,
                        LocalDateTime.parse(rs.getString("fecha_ingreso"), FMT),
                        rs.getString("descripcion_problema"),
                        parseEstatus(rs.getString("estatus")));
                    o.setActivo(rs.getInt("activo") == 1);
                    o.setItems(listarItems(o.getId()));
                    lista.add(o);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar órdenes por mecánico", e);
        }
        return lista;
    }

    public List<OrdenReparacion> listarPorVehiculo(int vehiculoId) {
        List<OrdenReparacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordenes WHERE vehiculo_id = ? AND activo = 1 ORDER BY id DESC";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, vehiculoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer mId = rs.getObject("mecanico_id") != null ? rs.getInt("mecanico_id") : null;
                    OrdenReparacion o = new OrdenReparacion(rs.getInt("id"), rs.getInt("vehiculo_id"), mId,
                        LocalDateTime.parse(rs.getString("fecha_ingreso"), FMT),
                        rs.getString("descripcion_problema"),
                        parseEstatus(rs.getString("estatus")));
                    o.setActivo(rs.getInt("activo") == 1);
                    o.setItems(listarItems(o.getId()));
                    lista.add(o);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar órdenes por vehículo", e);
        }
        return lista;
    }

    public void cambiarActivo(int id, boolean nuevoActivo) {
        String sql = "UPDATE ordenes SET activo = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, nuevoActivo ? 1 : 0);
            ps.setInt(2, id);
            ps.executeUpdate();
            bitacoraDAO.registrar("ACTUALIZAR", "Estado activo de orden #" + id + " cambiado a " + nuevoActivo);
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado de orden", e);
        }
    }

    public void eliminar(int id) {
        // Regresar refacciones al inventario si la orden es eliminada/ocultada
        try {
            List<ItemPresupuesto> items = listarItems(id);
            RefaccionDAO refDAO = new RefaccionDAO();
            List<Refaccion> refacciones = refDAO.listarTodas();
            for (ItemPresupuesto item : items) {
                if (item instanceof ItemRefaccion) {
                    ItemRefaccion ir = (ItemRefaccion) item;
                    refDAO.incrementarStock(ir.getRefaccionId(), ir.getCantidad());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error al devolver stock al eliminar orden: " + ex.getMessage());
        }

        String sqlOrden = "UPDATE ordenes SET activo = 0 WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sqlOrden)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            bitacoraDAO.registrar("ELIMINAR_LOGICO", "Orden de reparación deshabilitada/ocultada (id " + id + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al desactivar orden de reparación", e);
        }
    }

    public void eliminarItem(int itemId) {
        String sql = "DELETE FROM items_presupuesto WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, itemId);
            ps.executeUpdate();
            bitacoraDAO.registrar("ELIMINAR", "Item de presupuesto eliminado (id " + itemId + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar item de presupuesto", e);
        }
    }
}
