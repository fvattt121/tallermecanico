package com.taller.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ConexionBD {

    private static final String URL_SERVER = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String URL_DB = "jdbc:mysql://localhost:3306/hotwheels_tam?useSSL=false&allowPublicKeyRetrieval=true";
    private static Connection conexion;

    private ConexionBD() { }

    public static synchronized Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Asegurar que la base de datos existe en el servidor local de MySQL
                try (Connection connServer = DriverManager.getConnection(URL_SERVER, "root", "")) {
                    try (Statement st = connServer.createStatement()) {
                        st.execute("CREATE DATABASE IF NOT EXISTS hotwheels_tam CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;");
                    }
                }
                
                conexion = DriverManager.getConnection(URL_DB, "root", "");
            }
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo conectar a la base de datos MySQL (asegúrate de iniciar XAMPP)", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver JDBC de MySQL", e);
        }
        return conexion;
    }

    public static int obtenerUltimoIdInsertado() {
        try (Statement st = getConexion().createStatement();
             ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")) {
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo obtener el último id insertado", e);
        }
    }

    public static void inicializarEsquema() {
        String[] sentencias = {
            "CREATE TABLE IF NOT EXISTS clientes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(150) NOT NULL, telefono VARCHAR(50), email VARCHAR(150), direccion VARCHAR(255), " +
                "activo TINYINT(1) DEFAULT 1) ENGINE=InnoDB",

            "CREATE TABLE IF NOT EXISTS mecanicos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(150) NOT NULL, telefono VARCHAR(50), email VARCHAR(150), " +
                "especialidad VARCHAR(150), disponible TINYINT(1) DEFAULT 1, " +
                "activo TINYINT(1) DEFAULT 1) ENGINE=InnoDB",

            "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(150) UNIQUE NOT NULL, clave_hash VARCHAR(255) NOT NULL, " +
                "rol VARCHAR(50) NOT NULL, persona_id INT, " +
                "activo TINYINT(1) DEFAULT 1) ENGINE=InnoDB",

            "CREATE TABLE IF NOT EXISTS refacciones (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nombre VARCHAR(150) NOT NULL, precio_unitario DOUBLE NOT NULL, stock INT NOT NULL, " +
                "ruta_foto VARCHAR(255), " +
                "activo TINYINT(1) DEFAULT 1) ENGINE=InnoDB",

            "CREATE TABLE IF NOT EXISTS vehiculos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "placas VARCHAR(50) NOT NULL, marca VARCHAR(100), modelo VARCHAR(100), anio INT, " +
                "color VARCHAR(50), cliente_id INT, estatus VARCHAR(50) NOT NULL, ruta_foto TEXT, " +
                "activo TINYINT(1) DEFAULT 1, " +
                "FOREIGN KEY(cliente_id) REFERENCES clientes(id) ON DELETE SET NULL) ENGINE=InnoDB",

            "CREATE TABLE IF NOT EXISTS ordenes (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "vehiculo_id INT NOT NULL, mecanico_id INT, " +
                "fecha_ingreso VARCHAR(50) NOT NULL, descripcion_problema TEXT, estatus VARCHAR(50) NOT NULL, " +
                "activo TINYINT(1) DEFAULT 1, " +
                "FOREIGN KEY(vehiculo_id) REFERENCES vehiculos(id) ON DELETE CASCADE, " +
                "FOREIGN KEY(mecanico_id) REFERENCES mecanicos(id) ON DELETE SET NULL) ENGINE=InnoDB",

            "CREATE TABLE IF NOT EXISTS items_presupuesto (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "orden_id INT NOT NULL, tipo VARCHAR(50) NOT NULL, descripcion TEXT, " +
                "refaccion_id INT, precio_unitario DOUBLE, cantidad INT, " +
                "costo_fijo DOUBLE, horas DOUBLE, " +
                "FOREIGN KEY(orden_id) REFERENCES ordenes(id) ON DELETE CASCADE) ENGINE=InnoDB",

            "CREATE TABLE IF NOT EXISTS bitacora (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "usuario_id INT, username VARCHAR(150), fecha_hora VARCHAR(50) NOT NULL, " +
                "accion VARCHAR(100) NOT NULL, detalle TEXT) ENGINE=InnoDB"
        };

        String[] migracionesActivo = {
            "ALTER TABLE clientes ADD COLUMN IF NOT EXISTS activo TINYINT(1) DEFAULT 1",
            "ALTER TABLE mecanicos ADD COLUMN IF NOT EXISTS activo TINYINT(1) DEFAULT 1",
            "ALTER TABLE usuarios ADD COLUMN IF NOT EXISTS activo TINYINT(1) DEFAULT 1",
            "ALTER TABLE refacciones ADD COLUMN IF NOT EXISTS activo TINYINT(1) DEFAULT 1",
            "ALTER TABLE vehiculos ADD COLUMN IF NOT EXISTS activo TINYINT(1) DEFAULT 1",
            "ALTER TABLE ordenes ADD COLUMN IF NOT EXISTS activo TINYINT(1) DEFAULT 1"
        };

        try (Statement st = getConexion().createStatement()) {
            for (String sql : sentencias) {
                st.execute(sql);
            }
            for (String sqlMig : migracionesActivo) {
                try {
                    st.execute(sqlMig);
                } catch (SQLException ignored) {
                    // Por si la versión de MariaDB/MySQL no soporta IF NOT EXISTS en ADD COLUMN
                }
            }
            // Migración automática de roles (mantiene compatibilidad con versiones anteriores)
            st.execute("UPDATE usuarios SET rol = 'SUPERADMIN' WHERE rol = 'ADMINISTRADOR'");
            st.execute("UPDATE usuarios SET rol = 'GERENTE'    WHERE rol = 'ENCARGADO'");
            // IMPORTANTE: solo eliminar roles que ya no existen en el sistema.
            // Se conservan: SUPERADMIN, GERENTE, EMPLEADO, MECANICO, CLIENTE
            st.execute("DELETE FROM usuarios WHERE rol NOT IN ('SUPERADMIN','GERENTE','EMPLEADO','MECANICO','CLIENTE')");
        } catch (SQLException e) {
            throw new RuntimeException("Error creando el esquema de la base de datos MySQL", e);
        }
    }

    public static void sembrarDatosDefecto() {
        try (Statement st = getConexion().createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM vehiculos")) {
            rs.next();
            if (rs.getInt(1) > 0) {
                return;
            }
            
            // Clientes
            st.execute("INSERT INTO clientes (nombre, telefono, email, direccion) VALUES ('Juan Pérez', '555-0199', 'juan@gmail.com', 'Av. Reforma 123')");
            st.execute("INSERT INTO clientes (nombre, telefono, email, direccion) VALUES ('María Gómez', '555-0188', 'maria@hotmail.com', 'Calle Mayor 45')");
            st.execute("INSERT INTO clientes (nombre, telefono, email, direccion) VALUES ('Carlos López', '555-0177', 'carlos@gmail.com', 'Paseo de la Loma 78')");
            st.execute("INSERT INTO clientes (nombre, telefono, email, direccion) VALUES ('Ana Martínez', '555-0166', 'ana@hotmail.com', 'Ruta 66 Km 12')");
            st.execute("INSERT INTO clientes (nombre, telefono, email, direccion) VALUES ('Luis Rodríguez', '555-0155', 'luis@gmail.com', 'Pinar del Río 290')");
            
            // Vehículos reales
            st.execute("INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) " +
                       "VALUES ('BGT-DIVO', 'Bugatti', 'Divo', 2020, 'Azul', 1, 'EN_REVISION', 'imagenes/carros/bugatti_divo.jpg')");
            st.execute("INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) " +
                       "VALUES ('CV-CAM', 'Chevrolet', 'Camaro SS', 2018, 'Azul', 2, 'ESPERA_PIEZAS', 'imagenes/carros/camaro.jpg')");
            st.execute("INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) " +
                       "VALUES ('FD-MUST', 'Ford', 'Mustang Shelby GT350', 2022, 'Gris', 3, 'LISTO', 'imagenes/carros/mustang.jpg')");
            st.execute("INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) " +
                       "VALUES ('VW-VOCH', 'Volkswagen', 'Vocho', 1995, 'Rosa', 4, 'EN_REVISION', 'imagenes/carros/vocho.jpg')");
            st.execute("INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) " +
                       "VALUES ('MCL-750', 'McLaren', '750S', 2023, 'Rojo', 5, 'EN_REVISION', 'imagenes/carros/mclaren.jpg')");
            st.execute("INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) " +
                       "VALUES ('CV-SUB', 'Chevrolet', 'Suburban', 2020, 'Negro', 1, 'EN_REVISION', 'imagenes/carros/suburban.jpg')");
            
            // Mecánicos
            st.execute("INSERT INTO mecanicos (nombre, telefono, email, especialidad, disponible) VALUES ('Pedro Técnico', '555-9080', 'pedro@gmail.com', 'Motores y Transmisiones', 1)");
            st.execute("INSERT INTO mecanicos (nombre, telefono, email, especialidad, disponible) VALUES ('José Eléctrico', '555-9070', 'jose@hotmail.com', 'Electrónica y Sensores', 1)");
            
            // Refacciones Reales con Imágenes Reales
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Bujía NGK Iridium IX', 250.00, 30, 'imagenes/refacciones/bujia_ngk.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Filtro de Aceite Bosch', 180.00, 15, 'imagenes/refacciones/filtro_aceite_bosch.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Junta de Culata Victor Reinz', 1500.00, 5, 'imagenes/refacciones/junta_culata.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Pastillas de Freno Brembo Sport', 1200.00, 8, 'imagenes/refacciones/pastillas_brembo.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Disco de Freno Ventilado DBA', 2400.00, 12, 'imagenes/refacciones/disco_freno.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Cilindro Maestro de Frenos ATE', 3100.00, 4, 'imagenes/refacciones/cilindro_freno.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Amortiguador Monroe OESpectrum', 1850.00, 10, 'imagenes/refacciones/amortiguador_monroe.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Rótula de Dirección Moog', 450.00, 20, 'imagenes/refacciones/rotula.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Barra Estabilizadora OEM', 1150.00, 6, 'imagenes/refacciones/barra_estabilizadora.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Alternador Bosch 12V', 4200.00, 5, 'imagenes/refacciones/alternador_bosch.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Batería Optima RedTop', 5400.00, 6, 'imagenes/refacciones/bateria_optima.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Sensor MAF Delphi', 1950.00, 8, 'imagenes/refacciones/sensor_maf.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Faro Delantero LED Hotwheels', 3500.00, 10, 'imagenes/refacciones/faro_led.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Espejo Lateral Eléctrico', 1250.00, 8, 'imagenes/refacciones/espejo.jpg')");
            st.execute("INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES ('Parabrisas Laminado', 4200.00, 3, 'imagenes/refacciones/parabrisas.jpg')");
            
        } catch (SQLException e) {
            System.err.println("Error al sembrar datos de prueba: " + e.getMessage());
        }
    }
}




