package healthandbike.view;

import healthandbike.domain.Historial;
import healthandbike.domain.UbicacionTaller;
import healthandbike.domain.User;
import healthandbike.services.HistorialService;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class HistorialView {

    private final HistorialService historialService;

    public HistorialView(HistorialService historialService) {
        this.historialService = historialService;
    }

    public Historial createHistorial(int userId) {
        System.out.println("\n--- REGISTRO DE INGRESO A TALLER / HISTORIAL ---");
        int id = FormValidation.validateInt("Ingrese ID único del historial (o 0 para autogenerar):");
        if (userId <= 0) {
            userId = FormValidation.validateInt("Ingrese el ID de Usuario:");
        }
        int tallerId = FormValidation.validateInt("Ingrese el ID del Taller:");
        String entryDate = FormValidation.validateString("Ingrese Fecha de Entrada (YYYY-MM-DD):");
        String departureDate = FormValidation.validateString("Ingrese Fecha Estimada/Real de Salida (YYYY-MM-DD):");
        String diag = FormValidation.validateString("Ingrese Diagnóstico Mecánico:");
        double totalCost = FormValidation.validateDouble("Ingrese Costo Total Inicial:");

        User user = new User();
        user.setId(userId);
        UbicacionTaller taller = new UbicacionTaller();
        taller.setIdTaller(tallerId);

        Historial historial = new Historial(id, entryDate, departureDate, totalCost, diag, user, taller);
        Historial saved = historialService.createHistorial(historial);
        System.out.println("✓ Historial registrado con éxito: " + saved);
        return saved;
    }

    public void getHistorialesByUserId(int userId) {
        System.out.println("\n--- HISTORIAL DE INGRESOS A TALLER (USUARIO ID: " + userId + ") ---");
        List<Historial> list = historialService.getHistorialesByUserId(userId);
        if (list.isEmpty()) {
            System.out.println("No hay mantenimientos registrados para este usuario.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getAllHistoriales() {
        System.out.println("\n--- LISTADO GENERAL DE HISTORIALES ---");
        List<Historial> list = historialService.getAllHistoriales();
        if (list.isEmpty()) {
            System.out.println("No hay registros en el historial general.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getHistorialById(int id) {
        Optional<Historial> opt = historialService.getHistorialById(id);
        if (opt.isPresent()) {
            System.out.println("\n✓ Historial Encontrado:\n" + opt.get());
        } else {
            System.out.println("✗ No existe historial con ID: " + id);
        }
    }
}
