-- ==============================================================
-- Script de Creación de Base de Datos para Health and Bike
-- Diseñado para MySQL en XAMPP (Puerto 3307 o 3306)
-- Base de datos: healthandbike_laboratorio
-- ==============================================================

CREATE DATABASE IF NOT EXISTS healthandbike_laboratorio CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE healthandbike_laboratorio;

-- 1. Tabla de Usuarios (Clientes / Moteros)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    phone VARCHAR(20),
    role VARCHAR(50) DEFAULT 'CLIENTE',
    address VARCHAR(200),
    balance DECIMAL(12,2) DEFAULT 0.0
);

-- 2. Tabla de Administradores
CREATE TABLE IF NOT EXISTS admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    phone VARCHAR(20),
    department VARCHAR(100),
    access_level VARCHAR(50)
);

-- 3. Tabla de Motos
CREATE TABLE IF NOT EXISTS tipos_moto (
    id_moto INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    cylinder_capacity VARCHAR(50),
    license_plate VARCHAR(20) NOT NULL UNIQUE,
    year INT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- 4. Tabla de Estados y Kilometraje
CREATE TABLE IF NOT EXISTS estados_moto (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    mileage DECIMAL(10,2) NOT NULL,
    date_register VARCHAR(50) NOT NULL,
    status_description TEXT,
    moto_id INT,
    FOREIGN KEY (moto_id) REFERENCES tipos_moto(id_moto) ON DELETE CASCADE
);

-- 5. Tabla de Recordatorios de Mantenimiento
CREATE TABLE IF NOT EXISTS recordatorios (
    id_recordatorio INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    reminder_date VARCHAR(50) NOT NULL,
    status BOOLEAN DEFAULT TRUE,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 6. Tabla de Sedes y Talleres Aliados
CREATE TABLE IF NOT EXISTS ubicaciones_taller (
    id_taller INT AUTO_INCREMENT PRIMARY KEY,
    workshop_name VARCHAR(150) NOT NULL,
    address VARCHAR(200) NOT NULL,
    city VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    latitude DECIMAL(10,6),
    longitude DECIMAL(10,6)
);

-- 7. Tabla de Historiales de Mantenimiento
CREATE TABLE IF NOT EXISTS historiales (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    entry_date VARCHAR(50) NOT NULL,
    departure_date VARCHAR(50),
    total_cost DECIMAL(12,2) DEFAULT 0.0,
    diagnostic TEXT,
    user_id INT,
    taller_id INT,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (taller_id) REFERENCES ubicaciones_taller(id_taller) ON DELETE SET NULL
);

-- 8. Tabla de Arreglos Mecánicos
CREATE TABLE IF NOT EXISTS arreglos (
    id_arreglo INT AUTO_INCREMENT PRIMARY KEY,
    description TEXT NOT NULL,
    labor_cost DECIMAL(12,2) DEFAULT 0.0,
    historial_id INT,
    FOREIGN KEY (historial_id) REFERENCES historiales(id_historial) ON DELETE CASCADE
);

-- 9. Tabla de Costos de Arreglo (Repuestos y Mano de Obra)
CREATE TABLE IF NOT EXISTS costos_arreglo (
    id_costo INT AUTO_INCREMENT PRIMARY KEY,
    spare_parts_cost DECIMAL(12,2) DEFAULT 0.0,
    labor_cost DECIMAL(12,2) DEFAULT 0.0,
    total_amount DECIMAL(12,2) DEFAULT 0.0,
    arreglo_id INT UNIQUE,
    FOREIGN KEY (arreglo_id) REFERENCES arreglos(id_arreglo) ON DELETE CASCADE
);

-- Inserción de Datos Iniciales de Prueba
INSERT IGNORE INTO users (id, name, last_name, email, password, status, phone, role, address, balance) VALUES
(1, 'Carlos', 'Ramírez', 'carlos@mail.com', '123456', 1, '3101234567', 'CLIENTE', 'Calle 10 # 40-20, Medellín', 250000.00),
(2, 'Laura', 'Gómez', 'laura@mail.com', '123456', 1, '3119876543', 'CLIENTE', 'Cra 43A # 12-30, Poblado', 500000.00),
(3, 'Yojan', 'Zapata', 'yojanzapata0@gmail.com', 'admin123', 1, '3000000000', 'ADMIN', 'Medellín Centro', 1000000.00);

INSERT IGNORE INTO admins (id, name, last_name, email, password, status, phone, department, access_level) VALUES
(101, 'Yojan', 'Zapata', 'admin@healthandbike.com', 'admin2026', 1, '3000000000', 'Operaciones & Mantenimiento', 'SUPERADMIN'),
(102, 'Bernardo', 'Mira', 'bernardo.mira@cesde.edu.co', 'cesde2026', 1, '3123456789', 'Docencia & Calidad', 'ADMINISTRADOR');

INSERT IGNORE INTO tipos_moto (id_moto, brand, model, cylinder_capacity, license_plate, year, user_id) VALUES
(1, 'Yamaha', 'MT-09', '890', 'XYZ12A', 2023, 1),
(2, 'Kawasaki', 'Z400', '399', 'ABC34B', 2022, 2),
(3, 'Suzuki', 'Gixxer 250', '249', 'MNO56C', 2024, 1);

INSERT IGNORE INTO estados_moto (id_estado, mileage, date_register, status_description, moto_id) VALUES
(1, 12500.00, '2026-10-15', 'Excelente estado mecánico, cambio de aceite al día', 1),
(2, 24300.00, '2026-11-01', 'Desgaste moderado en pastillas de freno traseras', 2);

INSERT IGNORE INTO recordatorios (id_recordatorio, title, description, reminder_date, status, user_id) VALUES
(1, 'Cambio de Aceite', 'Cambio de aceite Motul 7100 y filtro', '2026-11-20', 1, 1),
(2, 'Revisión Técnico-Mecánica', 'Vence en el CDA Laureles', '2026-12-05', 1, 2),
(3, 'Tensión de Cadena', 'Limpiar, lubricar y tensionar kit de arrastre', '2026-11-15', 1, 1);

INSERT IGNORE INTO ubicaciones_taller (id_taller, workshop_name, address, city, phone, latitude, longitude) VALUES
(1, 'MotoExpertos Poblado', 'Calle 10 # 43E-15', 'Medellín', '3004445566', 6.208500, -75.567800),
(2, 'Taller El Motero Laureles', 'Circular 4ta # 73-10', 'Medellín', '3118889900', 6.244200, -75.591200),
(3, 'SuperBikes Envigado', 'Cra 43 # 35 Sur 20', 'Envigado', '3156667788', 6.171900, -75.586100);

INSERT IGNORE INTO historiales (id_historial, entry_date, departure_date, total_cost, diagnostic, user_id, taller_id) VALUES
(1, '2026-10-10', '2026-10-11', 185000.00, 'Mantenimiento general y cambio de kit de arrastre', 1, 1),
(2, '2026-10-25', '2026-10-26', 95000.00, 'Sincronización y calibración de válvulas', 2, 2);

INSERT IGNORE INTO arreglos (id_arreglo, description, labor_cost, historial_id) VALUES
(1, 'Instalación de piñón, corona y cadena dorada reforzada', 45000.00, 1),
(2, 'Limpieza del cuerpo de aceleración y prueba de inyectores', 55000.00, 2);

INSERT IGNORE INTO costos_arreglo (id_costo, spare_parts_cost, labor_cost, total_amount, arreglo_id) VALUES
(1, 140000.00, 45000.00, 185000.00, 1),
(2, 40000.00, 55000.00, 95000.00, 2);
