package healthandbike.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // Configuración para XAMPP MySQL
    // En este entorno XAMPP corre en el puerto 3307; si no responde, intenta el 3306 estándar.
    private static final String DB_NAME = "healthandbike_laboratorio";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String PARAMS = "?useSSL=false&serverTimezone=America/Bogota&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";

    private static String activeUrl = null;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Advertencia: No se encontró el driver JDBC de MySQL: " + e.getMessage());
        }
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (activeUrl != null) {
            return DriverManager.getConnection(activeUrl, USER, PASSWORD);
        }

        // 1. Probar primero el puerto 3307 (XAMPP en este sistema)
        String url3307 = "jdbc:mysql://localhost:3307/" + DB_NAME + PARAMS;
        try {
            Connection conn = DriverManager.getConnection(url3307, USER, PASSWORD);
            activeUrl = url3307;
            return conn;
        } catch (SQLException e1) {
            // 2. Si falla, probar puerto estándar 3306
            String url3306 = "jdbc:mysql://localhost:3306/" + DB_NAME + PARAMS;
            try {
                Connection conn = DriverManager.getConnection(url3306, USER, PASSWORD);
                activeUrl = url3306;
                return conn;
            } catch (SQLException e2) {
                // Lanzar la excepción original con detalles claros
                throw new SQLException("No se pudo conectar a MySQL en localhost:3307 ni en 3306. Verifique que XAMPP esté activo.\nDetalle: " + e1.getMessage());
            }
        }
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos XAMPP: " + e.getMessage());
            return false;
        }
    }
}
