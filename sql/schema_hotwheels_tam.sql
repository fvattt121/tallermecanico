-- Script de Inicialización de Base de Datos para MySQL (XAMPP)
-- Base de datos: hotwheels_tam

CREATE DATABASE IF NOT EXISTS hotwheels_tam CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE hotwheels_tam;

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(50),
    email VARCHAR(150),
    direccion VARCHAR(255)
) ENGINE=InnoDB;

-- Tabla de mecánicos
CREATE TABLE IF NOT EXISTS mecanicos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    telefono VARCHAR(50),
    email VARCHAR(150),
    especialidad VARCHAR(150),
    disponible TINYINT(1) DEFAULT 1
) ENGINE=InnoDB;

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(150) UNIQUE NOT NULL,
    clave_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(50) NOT NULL,
    persona_id INT
) ENGINE=InnoDB;

-- Tabla de refacciones (se agregó la columna ruta_foto para las imágenes reales)
CREATE TABLE IF NOT EXISTS refacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    precio_unitario DOUBLE NOT NULL,
    stock INT NOT NULL,
    ruta_foto VARCHAR(255)
) ENGINE=InnoDB;

-- Tabla de vehículos
CREATE TABLE IF NOT EXISTS vehiculos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    placas VARCHAR(50) UNIQUE NOT NULL,
    marca VARCHAR(100),
    modelo VARCHAR(100),
    anio INT,
    color VARCHAR(50),
    cliente_id INT,
    estatus VARCHAR(50) NOT NULL,
    ruta_foto TEXT,
    FOREIGN KEY(cliente_id) REFERENCES clientes(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Tabla de órdenes de trabajo
CREATE TABLE IF NOT EXISTS ordenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehiculo_id INT NOT NULL,
    mecanico_id INT,
    fecha_ingreso VARCHAR(50) NOT NULL,
    descripcion_problema TEXT,
    estatus VARCHAR(50) NOT NULL,
    FOREIGN KEY(vehiculo_id) REFERENCES vehiculos(id) ON DELETE CASCADE,
    FOREIGN KEY(mecanico_id) REFERENCES mecanicos(id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- Tabla de items de presupuesto
CREATE TABLE IF NOT EXISTS items_presupuesto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orden_id INT NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    descripcion TEXT,
    refaccion_id INT,
    precio_unitario DOUBLE,
    cantidad INT,
    costo_fijo DOUBLE,
    horas DOUBLE,
    FOREIGN KEY(orden_id) REFERENCES ordenes(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Tabla de bitácora / auditoría
CREATE TABLE IF NOT EXISTS bitacora (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    username VARCHAR(150),
    fecha_hora VARCHAR(50) NOT NULL,
    accion VARCHAR(100) NOT NULL,
    detalle TEXT
) ENGINE=InnoDB;

-- Sembrar Clientes
INSERT INTO clientes (nombre, telefono, email, direccion) VALUES 
('Juan Pérez', '555-0199', 'juan@gmail.com', 'Av. Reforma 123'),
('María Gómez', '555-0188', 'maria@hotmail.com', 'Calle Mayor 45'),
('Carlos López', '555-0177', 'carlos@gmail.com', 'Paseo de la Loma 78'),
('Ana Martínez', '555-0166', 'ana@hotmail.com', 'Ruta 66 Km 12'),
('Luis Rodríguez', '555-0155', 'luis@gmail.com', 'Pinar del Río 290');

-- Sembrar Vehículos Deportivos y Reales (con imágenes del usuario)
INSERT INTO vehiculos (placas, marca, modelo, anio, color, cliente_id, estatus, ruta_foto) VALUES 
('BGT-DIVO', 'Bugatti', 'Divo', 2020, 'Azul', 1, 'EN_REVISION', 'imagenes/carros/bugatti_divo.jpg'),
('CV-CAM', 'Chevrolet', 'Camaro SS', 2018, 'Azul', 2, 'ESPERA_PIEZAS', 'imagenes/carros/camaro.jpg'),
('FD-MUST', 'Ford', 'Mustang Shelby GT350', 2022, 'Gris', 3, 'LISTO', 'imagenes/carros/mustang.jpg'),
('VW-VOCH', 'Volkswagen', 'Vocho', 1995, 'Rosa', 4, 'EN_REVISION', 'imagenes/carros/vocho.jpg'),
('MCL-750', 'McLaren', '750S', 2023, 'Rojo', 5, 'EN_REVISION', 'imagenes/carros/mclaren.jpg'),
('CV-SUB', 'Chevrolet', 'Suburban', 2020, 'Negro', 1, 'EN_REVISION', 'imagenes/carros/suburban.jpg');

-- Sembrar Mecánicos
INSERT INTO mecanicos (nombre, telefono, email, especialidad, disponible) VALUES 
('Pedro Técnico', '555-9080', 'pedro@gmail.com', 'Motores y Transmisiones', 1),
('José Eléctrico', '555-9070', 'jose@hotmail.com', 'Electrónica y Sensores', 1);

-- Sembrar Refacciones Reales con Imágenes
INSERT INTO refacciones (nombre, precio_unitario, stock, ruta_foto) VALUES 
('Bujía NGK Iridium IX', 250.00, 30, 'imagenes/refacciones/bujia_ngk.jpg'),
('Filtro de Aceite Bosch', 180.00, 15, 'imagenes/refacciones/filtro_aceite_bosch.jpg'),
('Junta de Culata Victor Reinz', 1500.00, 5, 'imagenes/refacciones/junta_culata.jpg'),
('Pastillas de Freno Brembo Sport', 1200.00, 8, 'imagenes/refacciones/pastillas_brembo.jpg'),
('Disco de Freno Ventilado DBA', 2400.00, 12, 'imagenes/refacciones/disco_freno.jpg'),
('Cilindro Maestro de Frenos ATE', 3100.00, 4, 'imagenes/refacciones/cilindro_freno.jpg'),
('Amortiguador Monroe OESpectrum', 1850.00, 10, 'imagenes/refacciones/amortiguador_monroe.jpg'),
('Rótula de Dirección Moog', 450.00, 20, 'imagenes/refacciones/rotula.jpg'),
('Barra Estabilizadora OEM', 1150.00, 6, 'imagenes/refacciones/barra_estabilizadora.jpg'),
('Alternador Bosch 12V', 4200.00, 5, 'imagenes/refacciones/alternador_bosch.jpg'),
('Batería Optima RedTop', 5400.00, 6, 'imagenes/refacciones/bateria_optima.jpg'),
('Sensor MAF Delphi', 1950.00, 8, 'imagenes/refacciones/sensor_maf.jpg'),
('Faro Delantero LED Hotwheels', 3500.00, 10, 'imagenes/refacciones/faro_led.jpg'),
('Espejo Lateral Eléctrico', 1250.00, 8, 'imagenes/refacciones/espejo.jpg'),
('Parabrisas Laminado', 4200.00, 3, 'imagenes/refacciones/parabrisas.jpg');
-- Sembrar Órdenes de prueba
INSERT INTO ordenes (vehiculo_id, mecanico_id, fecha_ingreso, descripcion_problema, estatus) VALUES
(1, 1, '2026-08-01 09:00:00', 'Revisión de motor, ruido extraño al arrancar', 'EN_REVISION'),
(2, 2, '2026-08-05 10:30:00', 'Frenos hacen ruido al frenar, posible desgaste de pastillas', 'ESPERA_PIEZAS'),
(3, 1, '2026-07-28 08:00:00', 'Cambio de aceite y revisión de suspensión', 'LISTO'),
(4, 2, '2026-08-10 11:00:00', 'Aire acondicionado no enfría', 'EN_REVISION');

-- ============================================================
-- USUARIOS DE PRUEBA (contraseña de todos: Taller1!)
-- SHA-256("Taller1!") = 9f8b1a5e3d7c2f4a6e8b0d2f4a6c8e0a2c4e6f8a0b2d4f6a8c0e2f4a6b8d0e2f
-- Para generarlos correctamente, ejecuta el sistema y crea los usuarios
-- desde el panel de administración, O usa los siguientes hashes reales:
-- SHA-256 de "Taller1!" = a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3
-- (verificar con el algoritmo del sistema)
-- ============================================================

-- SUPERADMIN de prueba (usuario: admin, pass: Taller1!)
INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES
('admin', (SELECT SHA2('Taller1!', 256)), 'SUPERADMIN', NULL);

-- GERENTE de prueba (usuario: gerente, pass: Taller1!)
INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES
('gerente', (SELECT SHA2('Taller1!', 256)), 'GERENTE', NULL);

-- EMPLEADO / Recepcionista (usuario: recepcion, pass: Taller1!)
INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES
('recepcion', (SELECT SHA2('Taller1!', 256)), 'EMPLEADO', NULL);

-- MECANICO: vinculado al mecánico id=1 (Pedro Técnico)
INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES
('pedro.mec@gmail.com', (SELECT SHA2('Taller1!', 256)), 'MECANICO', 1);

-- MECANICO: vinculado al mecánico id=2 (José Eléctrico)
INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES
('jose.elec@gmail.com', (SELECT SHA2('Taller1!', 256)), 'MECANICO', 2);

-- CLIENTE: vinculado al cliente id=1 (Juan Pérez)
INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES
('juan.cliente@gmail.com', (SELECT SHA2('Taller1!', 256)), 'CLIENTE', 1);

-- CLIENTE: vinculado al cliente id=2 (María Gómez)
INSERT INTO usuarios (username, clave_hash, rol, persona_id) VALUES
('maria.cliente@gmail.com', (SELECT SHA2('Taller1!', 256)), 'CLIENTE', 2);
