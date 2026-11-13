package healthandbike.view;

import healthandbike.domain.TipoMoto;
import healthandbike.domain.User;
import healthandbike.services.TipoMotoService;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class TipoMotoView {

    private final TipoMotoService tipoMotoService;

    public TipoMotoView(TipoMotoService tipoMotoService) {
        this.tipoMotoService = tipoMotoService;
    }

    public TipoMoto createMoto(int userId) {
        System.out.println("\n--- REGISTRO DE VEHÍCULO / MOTO ---");
        int id = FormValidation.validateInt("Ingrese ID único de la moto (o 0 para autogenerar):");
        String brand = FormValidation.validateString("Ingrese Marca (ej. Yamaha, Honda, Suzuki):");
        String model = FormValidation.validateString("Ingrese Modelo (ej. MT-09, Gixxer, CB 190):");
        String cc = FormValidation.validateString("Ingrese Cilindraje (cc):");
        String plate = FormValidation.validateString("Ingrese Número de Placa (ej. ABC12D):").toUpperCase();
        int year = FormValidation.validateInt("Ingrese Año del Modelo:");

        if (userId <= 0) {
            userId = FormValidation.validateInt("Ingrese el ID del Propietario (User ID):");
        }

        User owner = new User();
        owner.setId(userId);

        TipoMoto moto = new TipoMoto(id, brand, model, cc, plate, year, owner);
        TipoMoto saved = tipoMotoService.createMoto(moto);
        System.out.println("✓ Moto registrada exitosamente: " + saved);
        return saved;
    }

    public void getMotoById(int id) {
        Optional<TipoMoto> motoOpt = tipoMotoService.getMotoById(id);
        if (motoOpt.isPresent()) {
            System.out.println("\n✓ Datos de la Moto:\n" + motoOpt.get());
        } else {
            System.out.println("✗ No se encontró moto con ID: " + id);
        }
    }

    public void getMotoByPlate(String plate) {
        Optional<TipoMoto> motoOpt = tipoMotoService.getMotoByPlate(plate.toUpperCase());
        if (motoOpt.isPresent()) {
            System.out.println("\n✓ Moto Encontrada:\n" + motoOpt.get());
        } else {
            System.out.println("✗ No se encontró ninguna moto con la placa: " + plate);
        }
    }

    public void getMotosByUserId(int userId) {
        System.out.println("\n--- MOTOS DEL USUARIO ID: " + userId + " ---");
        List<TipoMoto> list = tipoMotoService.getMotosByUserId(userId);
        if (list.isEmpty()) {
            System.out.println("El usuario no tiene motos registradas.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getAllMotos() {
        System.out.println("\n--- LISTADO GENERAL DE MOTOS ---");
        List<TipoMoto> list = tipoMotoService.getAllMotos();
        if (list.isEmpty()) {
            System.out.println("No hay motos registradas en el sistema.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void deleteMoto(int id) {
        boolean deleted = tipoMotoService.deleteMoto(id);
        if (deleted) {
            System.out.println("✓ Moto con ID " + id + " eliminada exitosamente.");
        } else {
            System.out.println("✗ Moto no encontrada con ID: " + id);
        }
    }
}
