package com.taller.dao;

import com.taller.modelo.RegistroBitacora;
import com.taller.modelo.Usuario;
import com.taller.util.Sesion;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO dedicado al modulo de auditoria/bitacora. Cualquier accion relevante
 * (login, alta, baja, modificacion) pasa por aqui, cumpliendo el requisito
 * de "registrar absolutamente todo lo que pasa en el sistema".
 */
public class BitacoraDAO {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void registrar(String accion, String detalle) {
        Usuario u = Sesion.getUsuarioActual();
        Integer usuarioId = (u != null) ? u.getId() : null;
        String username = (u != null) ? u.getUsername() : "sistema";

        String sql = "INSERT INTO bitacora (usuario_id, username, fecha_hora, accion, detalle) VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = ConexionBD.getConexion().prepareStatement(sql)) {
            if (usuarioId != null) ps.setInt(1, usuarioId); else ps.setNull(1, Types.INTEGER);
            ps.setString(2, username);
            ps.setString(3, LocalDateTime.now().format(FMT));
            ps.setString(4, accion);
            ps.setString(5, detalle);
            ps.executeUpdate();
        } catch (SQLException e) {
            // La auditoria nunca debe tumbar la operacion principal, solo se informa por consola.
            System.err.println("No se pudo registrar en bitacora: " + e.getMessage());
        }
    }

    public List<RegistroBitacora> listarTodos() {
        List<RegistroBitacora> lista = new ArrayList<>();
        String sql = "SELECT * FROM bitacora ORDER BY id DESC";
        try (Statement st = ConexionBD.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Integer usuarioId = rs.getObject("usuario_id") != null ? rs.getInt("usuario_id") : null;
                lista.add(new RegistroBitacora(
                    rs.getInt("id"), usuarioId, rs.getString("username"),
                    LocalDateTime.parse(rs.getString("fecha_hora"), FMT),
                    rs.getString("accion"), rs.getString("detalle")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar bitacora", e);
        }
        return lista;
    }

    /** Resumen del estatus actual del sistema (conteos por tabla). */
    public String obtenerEstatusActual() {
        StringBuilder sb = new StringBuilder();
        try (Statement st = ConexionBD.getConexion().createStatement()) {
            sb.append("Clientes: ").append(contar(st, "clientes")).append("\n");
            sb.append("Mecanicos: ").append(contar(st, "mecanicos")).append("\n");
            sb.append("Vehiculos: ").append(contar(st, "vehiculos")).append("\n");
            sb.append("Ordenes de reparacion: ").append(contar(st, "ordenes")).append("\n");
            sb.append("Refacciones registradas: ").append(contar(st, "refacciones")).append("\n");
            sb.append("Eventos en bitacora: ").append(contar(st, "bitacora")).append("\n");
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular estatus del sistema", e);
        }
        return sb.toString();
    }

    private int contar(Statement st, String tabla) throws SQLException {
        String sql = "bitacora".equals(tabla) ? "SELECT COUNT(*) FROM bitacora" : "SELECT COUNT(*) FROM " + tabla + " WHERE activo = 1";
        try (ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
