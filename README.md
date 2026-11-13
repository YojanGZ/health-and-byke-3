# Health and Bike - Versión Laboratorio (Java SE Puro con MySQL XAMPP)

Este proyecto es una reestructuración de **Health and Bike** desarrollada en **Java SE puro (sin Spring Boot)** y conectada directamente a **MySQL en XAMPP**, siguiendo la arquitectura, paquetes, patrones y lógica del repositorio de referencia:
🔗 [luciastoreLaboratorio (Prof. Bernardo Mira)](https://github.com/BernardoMira/luciastoreLaboratorio.git)

---

## 🗄️ Base de Datos en MySQL (XAMPP)

- **Nombre de la Base de Datos:** `healthandbike_laboratorio`
- **Puerto de Conexión:** `3307` (por defecto en este entorno XAMPP; con auto-detección y fallback a `3306`).
- **Usuario:** `root`
- **Contraseña:** `""` (vacía por defecto en XAMPP).
- **Script SQL de Creación:** [`schema.sql`](file:///c:/Users/yojan/OneDrive/Escritorio/HealthAndByke_Full_App/HealthAndByke_Laboratorio/schema.sql)
  - Contiene las 9 tablas relacionales (`users`, `admins`, `tipos_moto`, `estados_moto`, `recordatorios`, `ubicaciones_taller`, `historiales`, `arreglos`, `costos_arreglo`) con claves foráneas, restricciones de integridad y datos iniciales de prueba.

---

## 🏛️ Arquitectura del Proyecto

El sistema está organizado en una arquitectura por capas desacoplada mediante interfaces, JDBC nativo y ensamblada con el patrón **Simple Factory / Inyección Manual de Dependencias**:

```
src/main/java/healthandbike/
├── Main.java                          # Punto de entrada (verifica conexión a BD y arranca)
├── config/
│   ├── Config.java                    # Simple Factory: ensambla e inyecta dependencias
│   └── DatabaseConnection.java        # Conector JDBC Singleton para MySQL XAMPP (3307 / 3306)
├── domain/                            # Modelos de dominio (POO pura, herencia y encapsulamiento)
│   ├── Person.java                    # Clase base con atributos comunes
│   ├── User.java                      # Extiende de Person (cliente / motero)
│   ├── Admin.java                     # Extiende de Person (administrador)
│   ├── TipoMoto.java                  # Datos y especificaciones del vehículo
│   ├── EstadoMoto.java                # Kilometraje y estado técnico
│   ├── Recordatorio.java              # Recordatorios de mantenimiento
│   ├── UbicacionTaller.java           # Talleres afiliados y coordenadas
│   ├── Historial.java                 # Registro de ingreso y diagnósticos
│   ├── Arreglos.java                  # Arreglos mecánicos específicos
│   └── CostoArreglo.java              # Desglose de repuestos y mano de obra
├── repository/                        # Capa de Acceso a Datos (DAO / Repository con JDBC nativo)
│   ├── UserRepository.java            # CRUD en tabla 'users' y 'admins'
│   ├── TipoMotoRepository.java        # CRUD en tabla 'tipos_moto'
│   ├── EstadoMotoRepository.java      # CRUD en tabla 'estados_moto'
│   ├── RecordatorioRepository.java    # CRUD en tabla 'recordatorios'
│   ├── UbicacionTallerRepository.java # CRUD en tabla 'ubicaciones_taller'
│   ├── HistorialRepository.java       # CRUD en tabla 'historiales'
│   ├── ArreglosRepository.java        # CRUD en tabla 'arreglos'
│   └── CostoArregloRepository.java    # CRUD en tabla 'costos_arreglo'
├── services/                          # Lógica de negocio (contratos e implementaciones)
│   ├── UserService.java / UserServiceImpl.java
│   ├── AdminService.java / UserAdminService.java / AdminServiceImpl.java
│   ├── TipoMotoService.java / TipoMotoServiceImpl.java
│   ├── EstadoMotoService.java / EstadoMotoServiceImpl.java
│   ├── RecordatorioService.java / RecordatorioServiceImpl.java
│   ├── UbicacionTallerService.java / UbicacionTallerServiceImpl.java
│   ├── HistorialService.java / HistorialServiceImpl.java
│   ├── ArreglosService.java / ArreglosServiceImpl.java
│   ├── CostoArregloService.java / CostoArregloServiceImpl.java
│   └── StateSelector.java             # Selector de estados mediante consola
├── userinterface/
│   └── MenuApp.java                   # Menú interactivo por consola (Admin y Cliente)
├── utils/
│   ├── FormValidation.java            # Scanner centralizado y validaciones robustas
│   ├── EstadoMotoEnum.java
│   ├── EstadoArregloEnum.java
│   └── Role.java
└── view/                              # Capa de presentación por consola
    ├── UserView.java
    ├── AdminView.java
    ├── TipoMotoView.java
    ├── EstadoMotoView.java
    ├── RecordatorioView.java
    ├── UbicacionTallerView.java
    ├── HistorialView.java
    ├── ArreglosView.java
    └── CostoArregloView.java
```

---

## 🚀 Cómo Ejecutar el Proyecto

### Opción 1: En IntelliJ IDEA
1. Abrir **IntelliJ IDEA**.
2. Ir a **File -> Open...** y seleccionar la carpeta:
   `HealthAndByke_Laboratorio`
3. IntelliJ detectará automáticamente el archivo `pom.xml` con la dependencia `mysql-connector-j`.
4. Navegar a `src/main/java/healthandbike/Main.java`.
5. Hacer clic derecho sobre `Main.java` y seleccionar **Run 'Main.main()'**.

### Opción 2: Desde la Terminal (Maven)
```powershell
cd HealthAndByke_Laboratorio
.\mvnw.cmd clean compile
.\mvnw.cmd exec:java -Dexec.mainClass="healthandbike.Main"
```
