package healthandbike.config;

import healthandbike.repository.*;
import healthandbike.services.*;
import healthandbike.userinterface.MenuApp;
import healthandbike.view.*;

public class Config {

    public static MenuApp createMenuApp() {
        // Esto es un patrón Simple Factory / Manual Dependency Injection.
        // Se encarga de crear los objetos necesarios para la aplicación y devolver un objeto MenuApp
        // completamente ensamblado con todas sus dependencias.
        //
        // Ventajas:
        // 1. Evita instanciar objetos directamente en el Main.
        // 2. Centraliza la creación y configuración de objetos en un solo lugar.
        // 3. Facilita cambiar cualquier implementación concreta (por ejemplo, cambiar una implementación en memoria
        //    por una basada en archivos o JDBC) sin tocar el Main ni las interfaces.
        // 4. Cumple con el principio de Inversión de Dependencias (DIP) de SOLID y las directrices de Programación Orientada a Objetos.

        // 1. Capa de Repositorios (Persistencia de Datos)
        UserRepository userRepository = new UserRepository();
        TipoMotoRepository tipoMotoRepository = new TipoMotoRepository(userRepository);
        EstadoMotoRepository estadoMotoRepository = new EstadoMotoRepository(tipoMotoRepository);
        RecordatorioRepository recordatorioRepository = new RecordatorioRepository(userRepository);
        UbicacionTallerRepository tallerRepository = new UbicacionTallerRepository();
        HistorialRepository historialRepository = new HistorialRepository(userRepository, tallerRepository);
        ArreglosRepository arreglosRepository = new ArreglosRepository(historialRepository);
        CostoArregloRepository costoArregloRepository = new CostoArregloRepository(arreglosRepository);

        // 2. Capa de Servicios (Lógica de Negocio con Inyección Manual)
        UserService userService = new UserServiceImpl(userRepository);
        AdminServiceImpl adminService = new AdminServiceImpl(userRepository);
        TipoMotoService tipoMotoService = new TipoMotoServiceImpl(tipoMotoRepository, userRepository);
        EstadoMotoService estadoMotoService = new EstadoMotoServiceImpl(estadoMotoRepository, tipoMotoRepository);
        RecordatorioService recordatorioService = new RecordatorioServiceImpl(recordatorioRepository, userRepository);
        UbicacionTallerService tallerService = new UbicacionTallerServiceImpl(tallerRepository);
        HistorialService historialService = new HistorialServiceImpl(historialRepository, userRepository, tallerRepository);
        ArreglosService arreglosService = new ArreglosServiceImpl(arreglosRepository, historialRepository);
        CostoArregloService costoArregloService = new CostoArregloServiceImpl(costoArregloRepository, arreglosRepository);

        // 3. Capa de Vistas (Interacción con el usuario vía consola)
        UserView userView = new UserView(userService);
        AdminView adminView = new AdminView(adminService);
        TipoMotoView tipoMotoView = new TipoMotoView(tipoMotoService);
        EstadoMotoView estadoMotoView = new EstadoMotoView(estadoMotoService);
        RecordatorioView recordatorioView = new RecordatorioView(recordatorioService);
        UbicacionTallerView tallerView = new UbicacionTallerView(tallerService);
        HistorialView historialView = new HistorialView(historialService);
        ArreglosView arreglosView = new ArreglosView(arreglosService);
        CostoArregloView costoArregloView = new CostoArregloView(costoArregloService);

        // 4. Orquestador de Interfaz de Usuario
        return new MenuApp(
                userView,
                adminView,
                tipoMotoView,
                estadoMotoView,
                recordatorioView,
                tallerView,
                historialView,
                arreglosView,
                costoArregloView
        );
    }
}
