package healthandbike.view;

import healthandbike.domain.Admin;
import healthandbike.domain.User;
import healthandbike.services.AdminServiceImpl;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class AdminView {

    private final AdminServiceImpl adminService;

    public AdminView(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    public Admin createAdmin() {
        System.out.println("\n--- REGISTRO DE ADMINISTRADOR ---");
        int id = FormValidation.validateInt("Ingrese ID único del administrador (o 0 para autogenerar):");
        String name = FormValidation.validateString("Ingrese Nombre:");
        String lastName = FormValidation.validateString("Ingrese Apellido:");
        String email = FormValidation.validateString("Ingrese Correo Electrónico Corporativo:");
        String password = FormValidation.validateString("Ingrese Contraseña:");
        String phone = FormValidation.validateString("Ingrese Teléfono:");
        String department = FormValidation.validateString("Ingrese Departamento / Área (ej. Mantenimiento, Calidad):");
        String accessLevel = FormValidation.validateString("Ingrese Nivel de Acceso (ej. SUPERADMIN, MODERADOR):");

        Admin admin = new Admin(id, name, lastName, email, password, true, phone, department, accessLevel);
        Admin saved = adminService.createAdmin(admin);
        System.out.println("✓ Administrador registrado exitosamente: " + saved);
        return saved;
    }

    public void getAllAdmins() {
        System.out.println("\n--- LISTADO DE ADMINISTRADORES ---");
        List<Admin> list = adminService.getAllAdmins();
        if (list.isEmpty()) {
            System.out.println("No hay administradores registrados.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getAllUsers() {
        System.out.println("\n--- GESTIÓN ADMINISTRATIVA: LISTADO DE CLIENTES ---");
        List<User> list = adminService.getAllUsers();
        if (list.isEmpty()) {
            System.out.println("No hay clientes en el sistema.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getAdminById(int id) {
        Optional<Admin> opt = adminService.getAdminById(id);
        if (opt.isPresent()) {
            System.out.println("\n✓ Administrador Encontrado: " + opt.get());
        } else {
            System.out.println("✗ Administrador no encontrado con ID: " + id);
        }
    }
}
