package healthandbike;

import healthandbike.config.Config;
import healthandbike.config.DatabaseConnection;
import healthandbike.userinterface.MenuApp;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando Health and Bike (Versión Laboratorio - Java SE Puro)...");
        System.out.print("Verificando conexión con MySQL en XAMPP: ");
        if (DatabaseConnection.testConnection()) {
            System.out.println("✓ CONECTADO EXITOSAMENTE (BD: healthandbike_laboratorio)");
        } else {
            System.out.println("✗ ERROR DE CONEXIÓN. Por favor inicie el servicio MySQL en el panel de XAMPP.");
        }

        MenuApp menuApp = Config.createMenuApp();
        menuApp.showMainMenu();
    }
}
