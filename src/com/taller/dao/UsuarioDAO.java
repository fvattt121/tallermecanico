package com.taller.dao;

import com.taller.modelo.RolUsuario;
import com.taller.modelo.Usuario;
import com.taller.util.SeguridadUtil;

import java.sql.*;

public class UsuarioDAO {

    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    public Usuario autenticar(String username, String clave) {
        // Normalizar dominio a minúsculas antes de autenticar
        username = SeguridadUtil.normalizeEmail(username);
        String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario u = mapear(rs);
                    if (u.verificarClave(clave)) {
                        if (u.getRol() == RolUsuario.CLIENTE && u.getPersonaId() == null) {
                            autoVincular(u);
                        }
                        return u;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al autenticar usuario", e);
        }
        return null;
    }

    public boolean autoVincular(Usuario u) {
        if (u == null || u.getPersonaId() != null) return false;
        String sqlSearch = "SELECT id FROM clientes WHERE (LOWER(email) = LOWER(?) OR LOWER(nombre) = LOWER(?)) AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sqlSearch)) {
            String uname = u.getUsername().trim();
            String prefix = uname.contains("@") ? uname.substring(0, uname.indexOf("@")) : uname;
            ps.setString(1, uname);
            ps.setString(2, prefix);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int clienteId = rs.getInt("id");
                    String sqlUpdate = "UPDATE usuarios SET persona_id = ? WHERE id = ?";
                    try (PreparedStatement psUp = ConexionBD.getConexion().prepareStatement(sqlUpdate)) {
                        psUp.setInt(1, clienteId);
                        psUp.setInt(2, u.getId());
                        psUp.executeUpdate();
                    }
                    u.setPersonaId(clienteId);
                    bitacoraDAO.registrar("VINCULAR", "Usuario " + u.getUsername() + " vinculado automáticamente a cliente ID " + clienteId);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autovincular usuario a cliente: " + e.getMessage());
        }
        return false;
    }

    public boolean existeUsername(String username) {
        if (username == null || username.trim().isEmpty()) return false;
        // Normalizar dominio antes de comparar
        String normalized = SeguridadUtil.normalizeEmail(username);
        // Verificar TODOS los usuarios (incluyendo eliminados) para evitar re-registro con mismo correo
        String sql = "SELECT 1 FROM usuarios WHERE username = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, normalized);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar username", e);
        }
    }

    public Usuario registrar(String username, String claveTextoPlano, RolUsuario rol, Integer personaId) {
        // Normalizar dominio a minúsculas antes de guardar
        username = SeguridadUtil.normalizeEmail(username);

        String sql = "INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES (?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, SeguridadUtil.hashClave(claveTextoPlano));
            ps.setString(3, rol.name());
            if (personaId != null) ps.setInt(4, personaId); else ps.setNull(4, Types.INTEGER);
            ps.executeUpdate();
            int nuevoId = ConexionBD.obtenerUltimoIdInsertado();
            bitacoraDAO.registrar("CREAR", "Nuevo usuario registrado: " + username + " (rol " + rol + ")");
            return new Usuario(nuevoId, username, SeguridadUtil.hashClave(claveTextoPlano), rol, personaId);
        } catch (SQLException e) {
            throw new RuntimeException("Error al registrar usuario", e);
        }
    }

    public java.util.List<Usuario> listarSinPersona(RolUsuario rol) {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol = ? AND persona_id IS NULL AND activo = 1 ORDER BY username";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, rol.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios sin persona", e);
        }
        return lista;
    }

    public Usuario buscarPorPersona(RolUsuario rol, int personaId) {
        String sql = "SELECT * FROM usuarios WHERE rol = ? AND persona_id = ? AND activo = 1";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, rol.name());
            ps.setInt(2, personaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por persona", e);
        }
        return null;
    }

    public Usuario buscarPorPersonaConArchivados(RolUsuario rol, int personaId) {
        String sql = "SELECT * FROM usuarios WHERE rol = ? AND persona_id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, rol.name());
            ps.setInt(2, personaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por persona con archivados", e);
        }
        return null;
    }

    public void desvincularPersona(RolUsuario rol, int personaId) {
        String sql = "UPDATE usuarios SET persona_id = NULL WHERE rol = ? AND persona_id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setString(1, rol.name());
            ps.setInt(2, personaId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al desvincular persona de usuarios", e);
        }
    }

    public void vincularPersona(int usuarioId, int personaId) {
        String sql = "UPDATE usuarios SET persona_id = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            ps.setInt(1, personaId);
            ps.setInt(2, usuarioId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al vincular persona a usuario", e);
        }
    }



    public java.util.List<Usuario> listarTodos() {
        return listarTodos(false);
    }

    public java.util.List<Usuario> listarTodos(boolean incluirArchivados) {
        java.util.List<Usuario> lista = new java.util.ArrayList<>();
        String sql = incluirArchivados
            ? "SELECT * FROM usuarios ORDER BY rol, username"
            : "SELECT * FROM usuarios WHERE activo = 1 ORDER BY rol, username";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }
        return lista;
    }

    public void eliminar(int id) {
        cambiarEstadoActivo(id, false);
    }

    public void cambiarEstadoActivo(int id, boolean activo) {
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(
                "UPDATE usuarios SET activo = ? WHERE id = ?")) {
            ps.setInt(1, activo ? 1 : 0);
            ps.setInt(2, id);
            ps.executeUpdate();
            String accion = activo ? "RESTAURAR" : "ELIMINAR_LOGICO";
            bitacoraDAO.registrar(accion, "Cuenta de usuario modificada: activo=" + activo + " (id=" + id + ")");
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado activo del usuario", e);
        }
    }

    private Usuario mapear(ResultSet rs) throws SQLException {
        Integer personaId = rs.getObject("persona_id") != null ? rs.getInt("persona_id") : null;
        Usuario u = new Usuario(
            rs.getInt("id"), rs.getString("username"), rs.getString("clave_hash"),
            RolUsuario.valueOf(rs.getString("rol")), personaId
        );
        u.setActivo(rs.getInt("activo") == 1);
        return u;
    }
}
