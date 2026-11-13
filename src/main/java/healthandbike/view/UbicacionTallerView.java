package healthandbike.view;

import healthandbike.domain.UbicacionTaller;
import healthandbike.services.UbicacionTallerService;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class UbicacionTallerView {

    private final UbicacionTallerService tallerService;

    public UbicacionTallerView(UbicacionTallerService tallerService) {
        this.tallerService = tallerService;
    }

    public UbicacionTaller createTaller() {
        System.out.println("\n--- REGISTRO DE TALLER / SEDE ---");
        int id = FormValidation.validateInt("Ingrese ID único del taller (o 0 para autogenerar):");
        String name = FormValidation.validateString("Ingrese Nombre del Taller:");
        String address = FormValidation.validateString("Ingrese Dirección:");
        String city = FormValidation.validateString("Ingrese Ciudad (ej. Medellín, Envigado, Bello):");
        String phone = FormValidation.validateString("Ingrese Teléfono:");
        double lat = FormValidation.validateDouble("Ingrese Latitud (ej. 6.2085):");
        double lon = FormValidation.validateDouble("Ingrese Longitud (ej. -75.5678):");

        UbicacionTaller taller = new UbicacionTaller(id, name, address, city, phone, lat, lon);
        UbicacionTaller saved = tallerService.createTaller(taller);
        System.out.println("✓ Taller registrado exitosamente: " + saved);
        return saved;
    }

    public void getAllTalleres() {
        System.out.println("\n--- RED DE TALLERES ALIADOS ---");
        List<UbicacionTaller> list = tallerService.getAllTalleres();
        if (list.isEmpty()) {
            System.out.println("No hay talleres registrados.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getTalleresByCity(String city) {
        System.out.println("\n--- TALLERES EN LA CIUDAD DE: " + city + " ---");
        List<UbicacionTaller> list = tallerService.getTalleresByCity(city);
        if (list.isEmpty()) {
            System.out.println("No se encontraron talleres en esa ciudad.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getTallerById(int id) {
        Optional<UbicacionTaller> opt = tallerService.getTallerById(id);
        if (opt.isPresent()) {
            System.out.println("\n✓ Taller Encontrado:\n" + opt.get());
        } else {
            System.out.println("✗ No existe taller con ID: " + id);
        }
    }
}
