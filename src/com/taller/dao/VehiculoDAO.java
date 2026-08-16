package com.taller.dao;

import com.taller.modelo.EstatusVehiculo;
import com.taller.modelo.Vehiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO {

    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    public int crear(Vehiculo v) {
        String sql = "INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, v.getPlacas());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAnio());
            ps.setString(5, v.getColor());
            ps.setInt(6, v.getClienteId());
            ps.setString(7, v.getEstatus().name());
            ps.setString(8, v.getRutaFoto());
            ps.executeUpdate();
            int id = ConexionBD.obtenerUltimoIdInsertado();
            bitacoraDAO.registrar("CREAR", "Vehiculo recibido: " + v.getPlacas() + " (id " + id + ")");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Error al recibir vehiculo (verifica que las placas no esten repetidas)", e);
        }
    }

    public boolean existePlacas(String placas) {
        if (placas == null || placas.trim().isEmpty()) return false;
        String sql = "SELECT 1 FROM vehiculos WHERE UPPER(placas) = UPPER(?) AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, placas.trim());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar placas", e);
        }
    }

    public Vehiculo obtenerVehiculoActivoEnTaller(String placas) {
        if (placas == null || placas.trim().isEmpty()) return null;
        String sql = "SELECT * FROM vehiculos WHERE UPPER(placas) = UPPER(?) AND estatus != 'LISTO' AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, placas.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar vehículo activo en taller", e);
        }
        return null;
    }


    public void actualizarEstatus(int vehiculoId, EstatusVehiculo nuevoEstatus) {
        String sqlVehiculo = "UPDATE vehiculos SET estatus = ? WHERE id = ?";
        String sqlOrdenes = "UPDATE ordenes SET estatus = ? WHERE vehiculo_id = ? AND activo = 1";
        try (PreparedStatement psV = ConexionBD.getConexion().prepareStatement(sqlVehiculo);
             PreparedStatement psO = ConexionBD.getConexion().prepareStatement(sqlOrdenes)) {
            psV.setString(1, nuevoEstatus.name());
            psV.setInt(2, vehiculoId);
            psV.executeUpdate();

            psO.setString(1, nuevoEstatus.name());
            psO.setInt(2, vehiculoId);
            psO.executeUpdate();

            bitacoraDAO.registrar("ACTUALIZAR", "Vehículo id " + vehiculoId + " y sus órdenes cambiaron de estatus a " + nuevoEstatus.getEtiqueta());
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar estatus", e);
        }
    }

    public List<Vehiculo> listarTodos() {
        return listarTodos(false);
    }

    public List<Vehiculo> listarTodos(boolean incluirArchivados) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = incluirArchivados
            ? "SELECT * FROM vehiculos ORDER BY id DESC"
            : "SELECT * FROM vehiculos WHERE activo = 1 ORDER BY id DESC";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar vehiculos", e);
        }
        return lista;
    }

    private EstatusVehiculo parseEstatus(String str) {
        if (str == null) return EstatusVehiculo.EN_REVISION;
        try {
            return EstatusVehiculo.valueOf(str.trim());
        } catch (Exception e) {
            return EstatusVehiculo.EN_REVISION;
        }
    }

    private Vehiculo mapear(ResultSet rs) throws SQLException {
        return new Vehiculo(
            rs.getInt("id"),
            rs.getString("placas"),
            rs.getString("marca"),
            rs.getString("modelo"),
            rs.getInt("anio"),
            rs.getString("color"),
            rs.getInt("cliente_id"),
            parseEstatus(rs.getString("estatus")),
            rs.getString("ruta_foto"),
            rs.getInt("activo") == 1
        );
    }

    public List<Vehiculo> listarPorCliente(int clienteId) {
        List<Vehiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM vehiculos WHERE cliente_id = ? AND activo = 1 ORDER BY id DESC";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar vehículos del cliente", e);
        }
        return lista;
    }

    public void cambiarEstadoActivo(int id, boolean activo) {
        String sqlVehiculo = "UPDATE vehiculos SET activo = ? WHERE id = ?";
        String sqlOrdenes = "UPDATE ordenes SET activo = ? WHERE vehiculo_id = ?";
        try (PreparedStatement psV = ConexionBD.getConexion().prepareStatement(sqlVehiculo);
             PreparedStatement psO = ConexionBD.getConexion().prepareStatement(sqlOrdenes)) {
            
            psV.setInt(1, activo ? 1 : 0);
            psV.setInt(2, id);
            psV.executeUpdate();
            
            psO.setInt(1, activo ? 1 : 0);
            psO.setInt(2, id);
            psO.executeUpdate();

            bitacoraDAO.registrar(activo ? "RESTAURAR" : "ARCHIVAR", "Vehículo estado activo=" + activo + " (id " + id + ") y órdenes sincronizadas en cascada");
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado activo de vehículo y cascada", e);
        }
    }

    public void eliminar(int id) {
        cambiarEstadoActivo(id, false);
    }

}
