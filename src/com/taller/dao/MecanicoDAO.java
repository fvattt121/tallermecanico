package com.taller.dao;

import com.taller.modelo.Mecanico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MecanicoDAO {

    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    /**
     * Verifica si ya existe un mecánico activo con ese email (ignorando mayúsculas).
     * @param email Email a verificar
     * @param excludeId ID a excluir (0 al crear, propio ID al actualizar)
     */
    public boolean existeEmail(String email, int excludeId) {
        if (email == null || email.trim().isEmpty()) return false;
        String sql = excludeId > 0
            ? "SELECT 1 FROM mecanicos WHERE email = ? AND activo = 1 AND id != ?"
            : "SELECT 1 FROM mecanicos WHERE email = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, email.trim());
            if (excludeId > 0) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar email de mecánico", e);
        }
    }

    public boolean existeTelefono(String telefono, int excludeId) {
        if (telefono == null || telefono.trim().isEmpty()) return false;
        String cleanTel = telefono.replaceAll("[\\s()\\-]+", "");
        String sql = excludeId > 0
            ? "SELECT 1 FROM mecanicos WHERE REPLACE(REPLACE(REPLACE(REPLACE(telefono, ' ', ''), '-', ''), '(', ''), ')', '') = ? AND activo = 1 AND id != ?"
            : "SELECT 1 FROM mecanicos WHERE REPLACE(REPLACE(REPLACE(REPLACE(telefono, ' ', ''), '-', ''), '(', ''), ')', '') = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, cleanTel);
            if (excludeId > 0) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar teléfono de mecánico", e);
        }
    }


    public int crear(Mecanico m) {
        String sql = "INSERT INTO mecanicos (nombre, telefono, email, especialidad, disponible) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getTelefono());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getEspecialidad());
            ps.setInt(5, m.isDisponible() ? 1 : 0);
            ps.executeUpdate();
            int id = ConexionBD.obtenerUltimoIdInsertado();
            bitacoraDAO.registrar("CREAR", "Mecanico registrado: " + m.getNombre() + " (id " + id + ")");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear mecanico", e);
        }
    }

    public List<Mecanico> listarTodos() {
        List<Mecanico> lista = new ArrayList<>();
        String sql = "SELECT * FROM mecanicos WHERE activo = 1 ORDER BY nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mecanicos", e);
        }
        return lista;
    }

    /** Lista mecánicos activos + opcionalmente los archivados. */
    public List<Mecanico> listarTodosConArchivados(boolean incluirArchivados) {
        List<Mecanico> lista = new ArrayList<>();
        String sql = incluirArchivados
            ? "SELECT * FROM mecanicos ORDER BY activo DESC, nombre"
            : "SELECT * FROM mecanicos WHERE activo = 1 ORDER BY nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mecánicos con archivados", e);
        }
        return lista;
    }

    /** Mecánicos activos que aún NO tienen cuenta de usuario activa vinculada. */
    public List<Mecanico> listarSinCuenta() {
        List<Mecanico> lista = new ArrayList<>();
        String sql = "SELECT m.* FROM mecanicos m " +
                     "WHERE m.activo = 1 " +
                     "AND NOT EXISTS (" +
                     "  SELECT 1 FROM usuarios u WHERE u.persona_id = m.id AND u.activo = 1 AND u.rol = 'MECANICO'" +
                     ") ORDER BY m.nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar mecánicos sin cuenta", e);
        }
        return lista;
    }

    public Mecanico buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        String sql = "SELECT * FROM mecanicos WHERE email = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar mecánico por email", e);
        }
        return null;
    }

    private Mecanico mapear(ResultSet rs) throws SQLException {
        Mecanico m = new Mecanico(rs.getInt("id"), rs.getString("nombre"), rs.getString("telefono"),
            rs.getString("email"), rs.getString("especialidad"), rs.getInt("disponible") == 1);
        m.setActivo(rs.getInt("activo") == 1);
        return m;
    }

    public void actualizar(Mecanico m) {
        String sql = "UPDATE mecanicos SET nombre = ?, telefono = ?, email = ?, especialidad = ?, disponible = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, m.getNombre());
            ps.setString(2, m.getTelefono());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getEspecialidad());
            ps.setInt(5, m.isDisponible() ? 1 : 0);
            ps.setInt(6, m.getId());
            ps.executeUpdate();
            bitacoraDAO.registrar("ACTUALIZAR", "Mecánico actualizado: " + m.getNombre() + " (id " + m.getId() + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar mecánico", e);
        }
    }

    public boolean tieneOrdenesActivas(int mecanicoId) {
        String sql = "SELECT COUNT(*) FROM ordenes WHERE mecanico_id = ? AND activo = 1 AND estatus IN ('EN_REVISION','ESPERA_PIEZAS')";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, mecanicoId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar órdenes activas del mecánico: " + e.getMessage());
        }
        return false;
    }

    public void eliminar(int id) {
        cambiarEstadoActivo(id, false);
    }

    /** Archiva o restaura un mecánico (borrado lógico). */
    public void cambiarEstadoActivo(int id, boolean activo) {
        String sql = "UPDATE mecanicos SET activo = ?, disponible = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, activo ? 1 : 0);
            ps.setInt(2, activo ? 1 : 0); // Si se restaura, vuelve a estar disponible
            ps.setInt(3, id);
            ps.executeUpdate();
            String accion = activo ? "RESTAURAR" : "ELIMINAR_LOGICO";
            bitacoraDAO.registrar(accion, "Mecánico id=" + id + " activo=" + activo);
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado activo de mecánico", e);
        }
    }
}
