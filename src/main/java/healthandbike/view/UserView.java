package healthandbike.view;

import healthandbike.domain.User;
import healthandbike.services.UserService;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class UserView {

    private final UserService userService;

    public UserView(UserService userService) {
        this.userService = userService;
    }

    public User createUser() {
        System.out.println("\n--- REGISTRO DE USUARIO / CLIENTE ---");
        int id = FormValidation.validateInt("Ingrese ID único del usuario (o 0 para autogenerar):");
        String name = FormValidation.validateString("Ingrese Nombre:");
        String lastName = FormValidation.validateString("Ingrese Apellido:");
        
        String email;
        while (true) {
            email = FormValidation.validateString("Ingrese Correo Electrónico:");
            if (FormValidation.validateEmail(email)) {
                break;
            }
            System.out.println("Formato de correo inválido (ej. usuario@mail.com).");
        }

        String password = FormValidation.validateString("Ingrese Contraseña:");
        String phone = FormValidation.validateString("Ingrese Teléfono:");
        String address = FormValidation.validateString("Ingrese Dirección de Residencia:");
        double balance = FormValidation.validateDouble("Ingrese Saldo Inicial en Billetera:");

        User user = new User(id, name, lastName, email, password, true, phone, "CLIENTE", address, balance);
        User saved = userService.createUser(user);
        System.out.println("✓ Usuario registrado exitosamente: " + saved);
        return saved;
    }

    public void getUserById(int id) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            System.out.println("\n✓ Perfil Encontrado:");
            System.out.println(userOpt.get());
        } else {
            System.out.println("✗ No se encontró usuario con ID: " + id);
        }
    }

    public void getAllUsers() {
        System.out.println("\n--- LISTADO DE USUARIOS REGISTRADOS ---");
        List<User> list = userService.getAllUsers();
        if (list.isEmpty()) {
            System.out.println("No hay usuarios registrados actualmente.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void deleteUser(int id) {
        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            System.out.println("✓ Usuario con ID " + id + " eliminado con éxito.");
        } else {
            System.out.println("✗ No se pudo eliminar: Usuario no encontrado con ID " + id);
        }
    }
}
