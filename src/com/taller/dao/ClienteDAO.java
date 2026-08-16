package com.taller.dao;

import com.taller.modelo.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    public int crear(Cliente c) {
        String sql = "INSERT INTO clientes (nombre, telefono, email, direccion) VALUES (?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.executeUpdate();
            int id = ConexionBD.obtenerUltimoIdInsertado();
            
            // Auto-vincular cualquier usuario sin persona_id cuyo username coincida con el email del cliente
            if (c.getEmail() != null && !c.getEmail().trim().isEmpty()) {
                vincularUsuarioSinPersona(id, c.getEmail().trim());
            }

            bitacoraDAO.registrar("CREAR", "Cliente registrado: " + c.getNombre() + " (id " + id + ")");
            return id;
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear cliente", e);
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE activo = 1 ORDER BY nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes", e);
        }
        return lista;
    }

    /** Lista clientes activos + opcionalmente los archivados. */
    public List<Cliente> listarTodosConArchivados(boolean incluirArchivados) {
        List<Cliente> lista = new ArrayList<>();
        String sql = incluirArchivados
            ? "SELECT * FROM clientes ORDER BY activo DESC, nombre"
            : "SELECT * FROM clientes WHERE activo = 1 ORDER BY nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes con archivados", e);
        }
        return lista;
    }

    /** Clientes activos que aún NO tienen cuenta de usuario activa vinculada. */
    public List<Cliente> listarSinCuenta() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT c.* FROM clientes c " +
                     "WHERE c.activo = 1 " +
                     "AND NOT EXISTS (" +
                     "  SELECT 1 FROM usuarios u WHERE u.persona_id = c.id AND u.activo = 1 AND u.rol = 'CLIENTE'" +
                     ") ORDER BY c.nombre";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar clientes sin cuenta", e);
        }
        return lista;
    }

    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente", e);
        }
        return null;
    }

    public Cliente buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) return null;
        String sql = "SELECT * FROM clientes WHERE email = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, email.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar cliente por email", e);
        }
        return null;
    }

    /**
     * Verifica si ya existe un cliente activo con ese email.
     * @param email Email a verificar
     * @param excludeId ID del cliente actual a excluir (usar 0 al crear, propio ID al actualizar)
     */
    public boolean existeEmail(String email, int excludeId) {
        if (email == null || email.trim().isEmpty()) return false;
        String sql = excludeId > 0
            ? "SELECT 1 FROM clientes WHERE email = ? AND activo = 1 AND id != ?"
            : "SELECT 1 FROM clientes WHERE email = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, email.trim());
            if (excludeId > 0) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar email de cliente", e);
        }
    }

    public boolean existeTelefono(String telefono, int excludeId) {
        if (telefono == null || telefono.trim().isEmpty()) return false;
        String cleanTel = telefono.replaceAll("[\\s()\\-]+", "");
        String sql = excludeId > 0
            ? "SELECT 1 FROM clientes WHERE REPLACE(REPLACE(REPLACE(REPLACE(telefono, ' ', ''), '-', ''), '(', ''), ')', '') = ? AND activo = 1 AND id != ?"
            : "SELECT 1 FROM clientes WHERE REPLACE(REPLACE(REPLACE(REPLACE(telefono, ' ', ''), '-', ''), '(', ''), ')', '') = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, cleanTel);
            if (excludeId > 0) ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar teléfono de cliente", e);
        }
    }


    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente c = new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("telefono"),
            rs.getString("email"), rs.getString("direccion"));
        c.setActivo(rs.getInt("activo") == 1);
        return c;
    }

    public void actualizar(Cliente c) {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, email = ?, direccion = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setInt(5, c.getId());
            ps.executeUpdate();

            if (c.getEmail() != null && !c.getEmail().trim().isEmpty()) {
                vincularUsuarioSinPersona(c.getId(), c.getEmail().trim());
            }

            bitacoraDAO.registrar("ACTUALIZAR", "Cliente actualizado: " + c.getNombre() + " (id " + c.getId() + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    private void vincularUsuarioSinPersona(int clienteId, String email) {
        String sql = "UPDATE usuarios SET persona_id = ? WHERE username = ? AND persona_id IS NULL";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            ps.setString(2, email);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                bitacoraDAO.registrar("VINCULAR", "Usuario (" + email + ") auto-vinculado a cliente ID " + clienteId);
            }
        } catch (SQLException e) {
            System.err.println("Error al auto-vincular cliente a usuario: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        cambiarEstadoActivo(id, false);
    }

    /**
     * Archiva o restaura un cliente y todo lo asociado en cascada:
     * vehiculos -> ordenes.
     */
    public void cambiarEstadoActivo(int id, boolean activo) {
        String sqlCliente   = "UPDATE clientes SET activo = ? WHERE id = ?";
        String sqlVehiculos = "UPDATE vehiculos SET activo = ? WHERE cliente_id = ?";
        String sqlOrdenes   = "UPDATE ordenes SET activo = ? WHERE vehiculo_id IN (SELECT id FROM vehiculos WHERE cliente_id = ?)";
        String sqlUsuario   = "UPDATE usuarios SET activo = ? WHERE persona_id = ? AND rol = 'CLIENTE'";
        try (PreparedStatement psC = ConexionBD.getConexion().prepareStatement(sqlCliente);
             PreparedStatement psV = ConexionBD.getConexion().prepareStatement(sqlVehiculos);
             PreparedStatement psO = ConexionBD.getConexion().prepareStatement(sqlOrdenes);
             PreparedStatement psU = ConexionBD.getConexion().prepareStatement(sqlUsuario)) {

            int val = activo ? 1 : 0;

            psO.setInt(1, val); psO.setInt(2, id); psO.executeUpdate();
            psV.setInt(1, val); psV.setInt(2, id); psV.executeUpdate();
            psC.setInt(1, val); psC.setInt(2, id); psC.executeUpdate();
            psU.setInt(1, val); psU.setInt(2, id); psU.executeUpdate();

            String accion = activo ? "RESTAURAR" : "ELIMINAR_LOGICO";
            bitacoraDAO.registrar(accion, "Cliente id=" + id + " activo=" + activo + " (cascada vehiculos+ordenes+usuario)");
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado activo de cliente y cascada", e);
        }
    }
}
