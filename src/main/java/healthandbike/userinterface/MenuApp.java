package healthandbike.userinterface;

import healthandbike.utils.FormValidation;
import healthandbike.view.*;

public class MenuApp {

    private final UserView userView;
    private final AdminView adminView;
    private final TipoMotoView tipoMotoView;
    private final EstadoMotoView estadoMotoView;
    private final RecordatorioView recordatorioView;
    private final UbicacionTallerView tallerView;
    private final HistorialView historialView;
    private final ArreglosView arreglosView;
    private final CostoArregloView costoArregloView;

    public MenuApp(UserView userView,
                   AdminView adminView,
                   TipoMotoView tipoMotoView,
                   EstadoMotoView estadoMotoView,
                   RecordatorioView recordatorioView,
                   UbicacionTallerView tallerView,
                   HistorialView historialView,
                   ArreglosView arreglosView,
                   CostoArregloView costoArregloView) {
        this.userView = userView;
        this.adminView = adminView;
        this.tipoMotoView = tipoMotoView;
        this.estadoMotoView = estadoMotoView;
        this.recordatorioView = recordatorioView;
        this.tallerView = tallerView;
        this.historialView = historialView;
        this.arreglosView = arreglosView;
        this.costoArregloView = costoArregloView;
    }

    public void showMainMenu() {
        System.out.println("=================================================");
        System.out.println("   BIENVENIDO A HEALTH AND BIKE - LABORATORIO    ");
        System.out.println(" (Sistema de Gestión y Cuidado - MySQL XAMPP)   ");
        System.out.println("=================================================");

        int init = FormValidation.validateInt("Presione 1 para iniciar la aplicación, o 0 para salir:");

        while (init != 0) {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Registrar Usuario");
            System.out.println("2. Iniciar Sesión / Acceder por Perfil");
            System.out.println("3. Salir");
            int option = FormValidation.validateInt("Seleccione una opción:");

            switch (option) {
                case 1:
                    System.out.println("\nSeleccione tipo de cuenta a registrar:");
                    System.out.println("1. Cliente / Motero  2. Administrador");
                    int userType = FormValidation.validateInt("Seleccione opción:");
                    if (userType == 1) {
                        userView.createUser();
                    } else if (userType == 2) {
                        adminView.createAdmin();
                    } else {
                        System.out.println("Opción no válida.");
                    }
                    break;

                case 2:
                    System.out.println("\nSeleccione perfil de acceso:");
                    System.out.println("1. Administrador del Sistema");
                    System.out.println("2. Cliente / Propietario de Moto");
                    int profileOption = FormValidation.validateInt("Seleccione opción:");
                    if (profileOption == 1) {
                        profileSelector("admin");
                    } else if (profileOption == 2) {
                        profileSelector("customer");
                    } else {
                        System.out.println("Perfil no válido.");
                    }
                    break;

                case 3:
                    System.out.println("Saliendo de la aplicación Health and Bike. ¡Hasta pronto!");
                    init = 0;
                    break;

                default:
                    System.out.println("Opción no válida, por favor seleccione una opción correcta.");
            }
        }
    }

    public void profileSelector(String profile) {
        if ("admin".equalsIgnoreCase(profile)) {
            showMenuAdmin();
        } else if ("customer".equalsIgnoreCase(profile)) {
            showMenuCustomer();
        }
    }

    public void showMenuAdmin() {
        while (true) {
            System.out.println("\n=================================");
            System.out.println("       MENÚ ADMINISTRADOR        ");
            System.out.println("=================================");
            System.out.println("1. Gestionar Clientes y Usuarios");
            System.out.println("2. Gestionar Motos y Estados");
            System.out.println("3. Gestionar Recordatorios");
            System.out.println("4. Gestionar Red de Talleres");
            System.out.println("5. Gestionar Historiales de Mantenimiento");
            System.out.println("6. Gestionar Arreglos y Costos");
            System.out.println("7. Volver al Menú Principal");

            int option = FormValidation.validateInt("Seleccione opción:");

            switch (option) {
                case 1:
                    menuAdminUsuarios();
                    break;
                case 2:
                    menuAdminMotos();
                    break;
                case 3:
                    menuAdminRecordatorios();
                    break;
                case 4:
                    menuAdminTalleres();
                    break;
                case 5:
                    menuAdminHistoriales();
                    break;
                case 6:
                    menuAdminArreglosYCostos();
                    break;
                case 7:
                    System.out.println("Regresando al Menú Principal...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void menuAdminUsuarios() {
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN: USUARIOS ---");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Ver todos los clientes");
            System.out.println("3. Buscar cliente por ID");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Ver administradores");
            System.out.println("6. Volver");
            int opt = FormValidation.validateInt("Seleccione opción:");
            if (opt == 1) userView.createUser();
            else if (opt == 2) adminView.getAllUsers();
            else if (opt == 3) {
                int id = FormValidation.validateInt("Ingrese ID a buscar:");
                userView.getUserById(id);
            } else if (opt == 4) {
                int id = FormValidation.validateInt("Ingrese ID a eliminar:");
                userView.deleteUser(id);
            } else if (opt == 5) adminView.getAllAdmins();
            else if (opt == 6) break;
            else System.out.println("Opción no válida.");
        }
    }

    private void menuAdminMotos() {
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN: MOTOS Y ESTADOS ---");
            System.out.println("1. Registrar nueva moto");
            System.out.println("2. Ver todas las motos");
            System.out.println("3. Buscar moto por placa");
            System.out.println("4. Registrar nuevo estado/kilometraje para una moto");
            System.out.println("5. Ver todos los reportes de estado");
            System.out.println("6. Volver");
            int opt = FormValidation.validateInt("Seleccione opción:");
            if (opt == 1) tipoMotoView.createMoto(0);
            else if (opt == 2) tipoMotoView.getAllMotos();
            else if (opt == 3) {
                String placa = FormValidation.validateString("Ingrese placa:");
                tipoMotoView.getMotoByPlate(placa);
            } else if (opt == 4) estadoMotoView.createEstado(0);
            else if (opt == 5) estadoMotoView.getAllEstados();
            else if (opt == 6) break;
            else System.out.println("Opción no válida.");
        }
    }

    private void menuAdminRecordatorios() {
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN: RECORDATORIOS ---");
            System.out.println("1. Crear recordatorio");
            System.out.println("2. Ver todos los recordatorios");
            System.out.println("3. Volver");
            int opt = FormValidation.validateInt("Seleccione opción:");
            if (opt == 1) recordatorioView.createRecordatorio(0);
            else if (opt == 2) recordatorioView.getAllRecordatorios();
            else if (opt == 3) break;
            else System.out.println("Opción no válida.");
        }
    }

    private void menuAdminTalleres() {
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN: TALLERES ALIADOS ---");
            System.out.println("1. Registrar nuevo taller");
            System.out.println("2. Ver todos los talleres");
            System.out.println("3. Buscar talleres por ciudad");
            System.out.println("4. Volver");
            int opt = FormValidation.validateInt("Seleccione opción:");
            if (opt == 1) tallerView.createTaller();
            else if (opt == 2) tallerView.getAllTalleres();
            else if (opt == 3) {
                String ciudad = FormValidation.validateString("Ingrese nombre de la ciudad:");
                tallerView.getTalleresByCity(ciudad);
            } else if (opt == 4) break;
            else System.out.println("Opción no válida.");
        }
    }

    private void menuAdminHistoriales() {
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN: HISTORIALES DE MANTENIMIENTO ---");
            System.out.println("1. Registrar nuevo ingreso a taller");
            System.out.println("2. Ver todos los historiales");
            System.out.println("3. Buscar historial por ID");
            System.out.println("4. Volver");
            int opt = FormValidation.validateInt("Seleccione opción:");
            if (opt == 1) historialView.createHistorial(0);
            else if (opt == 2) historialView.getAllHistoriales();
            else if (opt == 3) {
                int id = FormValidation.validateInt("Ingrese ID del historial:");
                historialView.getHistorialById(id);
            } else if (opt == 4) break;
            else System.out.println("Opción no válida.");
        }
    }

    private void menuAdminArreglosYCostos() {
        while (true) {
            System.out.println("\n--- ADMINISTRACIÓN: ARREGLOS Y COSTOS ---");
            System.out.println("1. Registrar arreglo mecánico a un historial");
            System.out.println("2. Ver todos los arreglos");
            System.out.println("3. Registrar o calcular costo de arreglo");
            System.out.println("4. Ver todos los costos");
            System.out.println("5. Volver");
            int opt = FormValidation.validateInt("Seleccione opción:");
            if (opt == 1) arreglosView.createArreglo(0);
            else if (opt == 2) arreglosView.getAllArreglos();
            else if (opt == 3) costoArregloView.createCosto(0);
            else if (opt == 4) costoArregloView.getAllCostos();
            else if (opt == 5) break;
            else System.out.println("Opción no válida.");
        }
    }

    public void showMenuCustomer() {
        int myUserId = FormValidation.validateInt("Ingrese su ID de cliente para identificarse:");

        while (true) {
            System.out.println("\n=================================");
            System.out.println("     PANEL CLIENTE / MOTERO      ");
            System.out.println("=================================");
            System.out.println("1. Ver mi Perfil");
            System.out.println("2. Mis Motos (Ver / Registrar)");
            System.out.println("3. Registrar Kilometraje / Estado de mi Moto");
            System.out.println("4. Mis Recordatorios de Mantenimiento");
            System.out.println("5. Red de Talleres Aliados");
            System.out.println("6. Mi Historial de Mantenimientos");
            System.out.println("7. Volver al Menú Principal");

            int option = FormValidation.validateInt("Seleccione opción:");

            switch (option) {
                case 1:
                    userView.getUserById(myUserId);
                    break;
                case 2:
                    System.out.println("\n1. Ver mis motos  2. Registrar nueva moto");
                    int subOpt = FormValidation.validateInt("Seleccione:");
                    if (subOpt == 1) {
                        tipoMotoView.getMotosByUserId(myUserId);
                    } else if (subOpt == 2) {
                        tipoMotoView.createMoto(myUserId);
                    }
                    break;
                case 3:
                    int motoId = FormValidation.validateInt("Ingrese el ID de su moto para registrar estado:");
                    estadoMotoView.createEstado(motoId);
                    break;
                case 4:
                    System.out.println("\n1. Ver mis recordatorios  2. Agregar recordatorio");
                    int recOpt = FormValidation.validateInt("Seleccione:");
                    if (recOpt == 1) {
                        recordatorioView.getRecordatoriosByUserId(myUserId);
                    } else if (recOpt == 2) {
                        recordatorioView.createRecordatorio(myUserId);
                    }
                    break;
                case 5:
                    System.out.println("\n1. Ver todos los talleres  2. Buscar por ciudad");
                    int tOpt = FormValidation.validateInt("Seleccione:");
                    if (tOpt == 1) {
                        tallerView.getAllTalleres();
                    } else if (tOpt == 2) {
                        String city = FormValidation.validateString("Ingrese la ciudad:");
                        tallerView.getTalleresByCity(city);
                    }
                    break;
                case 6:
                    historialView.getHistorialesByUserId(myUserId);
                    break;
                case 7:
                    System.out.println("Saliendo del panel de usuario...");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
