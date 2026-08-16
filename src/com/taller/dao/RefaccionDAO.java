package com.taller.dao;

import com.taller.modelo.Refaccion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RefaccionDAO {

    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    /**
     * Verifica si ya existe una refacción activa con ese nombre (ignorando mayúsculas).
     * @param nombre Nombre a verificar
     * @param excludeId ID a excluir (usar 0 al crear, el propio ID al actualizar)
     */
    public boolean existeNombre(String nombre, int excludeId) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        String sql = excludeId > 0
            ? "SELECT 1 FROM refacciones WHERE nombre = ? AND activo = 1 AND id != ?"
            : "SELECT 1 FROM refacciones WHERE nombre = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, nombre.trim());
            if (excludeId > 0) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar nombre de refacción", e);
        }
    }

    public int crear(Refaccion r) {
        String sql = "INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES (?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setDouble(2, r.getPrecioUnitario());
            ps.setInt(3, r.getStock());
            ps.setString(4, r.getRutaFoto());
            ps.executeUpdate();
            int id = ConexionBD.obtenerUltimoIdInsertado();
            bitacoraDAO.registrar("CREAR", "Refaccion registrada: " + r.getNombre() + " (id " + id + ")");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear refaccion", e);
        }
    }

    public void actualizarStock(int refaccionId, int nuevoStock) {
        String sql = "UPDATE refacciones SET stock = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, Math.max(0, nuevoStock));
            ps.setInt(2, refaccionId);
            ps.executeUpdate();
            bitacoraDAO.registrar("ACTUALIZAR", "Stock de refaccion id " + refaccionId + " actualizado a " + nuevoStock);
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar stock", e);
        }
    }

    public boolean descontarStock(int refaccionId, int cantidad) {
        if (cantidad <= 0) return false;
        String sql = "UPDATE refacciones SET stock = stock - ? WHERE id = ? AND stock >= ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, refaccionId);
            ps.setInt(3, cantidad);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                bitacoraDAO.registrar("ACTUALIZAR", "Stock descontado -" + cantidad + " en refacción ID " + refaccionId);
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al descontar stock de refacción", e);
        }
        return false;
    }

    public void incrementarStock(int refaccionId, int cantidad) {
        if (cantidad <= 0) return;
        String sql = "UPDATE refacciones SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, refaccionId);
            ps.executeUpdate();
            bitacoraDAO.registrar("ACTUALIZAR", "Stock incrementado +" + cantidad + " en refacción ID " + refaccionId);
        } catch (SQLException e) {
            throw new RuntimeException("Error al incrementar stock de refacción", e);
        }
    }

    public List<Refaccion> listarTodas() {
        return listarTodas(false);
    }

    public List<Refaccion> listarTodas(boolean incluirArchivadas) {
        List<Refaccion> lista = new ArrayList<>();
        String sql = incluirArchivadas
            ? "SELECT * FROM refacciones ORDER BY nombre"
            : "SELECT * FROM refacciones WHERE activo = 1 ORDER BY nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar refacciones", e);
        }
        return lista;
    }

    public Refaccion buscarPorId(int id) {
        String sql = "SELECT * FROM refacciones WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar refacción por ID", e);
        }
        return null;
    }

    private Refaccion mapear(ResultSet rs) throws SQLException {
        return new Refaccion(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getDouble("precio_unitario"),
            rs.getInt("stock"),
            rs.getString("ruta_foto"),
            rs.getInt("activo") == 1
        );
    }

    public void actualizar(Refaccion r) {
        String sql = "UPDATE refacciones SET nombre = ?, precio_unitario = ?, stock = ?, ruta_foto = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setDouble(2, r.getPrecioUnitario());
            ps.setInt(3, r.getStock());
            ps.setString(4, r.getRutaFoto());
            ps.setInt(5, r.getId());
            ps.executeUpdate();
            bitacoraDAO.registrar("ACTUALIZAR", "Refacción actualizada: " + r.getNombre() + " (id " + r.getId() + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar refacción", e);
        }
    }

    public void cambiarEstadoActivo(int id, boolean activo) {
        String sql = "UPDATE refacciones SET activo = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, activo ? 1 : 0);
            ps.setInt(2, id);
            ps.executeUpdate();
            bitacoraDAO.registrar(activo ? "RESTAURAR" : "ARCHIVAR", "Refacción estado activo=" + activo + " (id " + id + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado activo de refacción", e);
        }
    }

    public void eliminar(int id) {
        cambiarEstadoActivo(id, false);
    }

}
