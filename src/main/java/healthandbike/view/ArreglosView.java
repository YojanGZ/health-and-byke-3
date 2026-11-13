package healthandbike.view;

import healthandbike.domain.Arreglos;
import healthandbike.domain.Historial;
import healthandbike.services.ArreglosService;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class ArreglosView {

    private final ArreglosService arreglosService;

    public ArreglosView(ArreglosService arreglosService) {
        this.arreglosService = arreglosService;
    }

    public Arreglos createArreglo(int historialId) {
        System.out.println("\n--- REGISTRO DE ARREGLO MECÁNICO ---");
        int id = FormValidation.validateInt("Ingrese ID único del arreglo (o 0 para autogenerar):");
        if (historialId <= 0) {
            historialId = FormValidation.validateInt("Ingrese el ID del Historial asociado:");
        }
        String desc = FormValidation.validateString("Ingrese Descripción del Arreglo:");
        double labor = FormValidation.validateDouble("Ingrese Costo de Mano de Obra:");

        Historial historial = new Historial();
        historial.setIdHistorial(historialId);

        Arreglos arreglo = new Arreglos(id, desc, labor, historial);
        Arreglos saved = arreglosService.createArreglo(arreglo);
        System.out.println("✓ Arreglo registrado exitosamente: " + saved);
        return saved;
    }

    public void getArreglosByHistorialId(int historialId) {
        System.out.println("\n--- ARREGLOS DEL HISTORIAL ID: " + historialId + " ---");
        List<Arreglos> list = arreglosService.getArreglosByHistorialId(historialId);
        if (list.isEmpty()) {
            System.out.println("No hay arreglos detallados para este historial.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getAllArreglos() {
        System.out.println("\n--- LISTADO GENERAL DE ARREGLOS ---");
        List<Arreglos> list = arreglosService.getAllArreglos();
        if (list.isEmpty()) {
            System.out.println("No hay arreglos registrados en el sistema.");
        } else {
            list.forEach(System.out::println);
        }
    }
}
