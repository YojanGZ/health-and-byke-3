package healthandbike.view;

import healthandbike.domain.Recordatorio;
import healthandbike.domain.User;
import healthandbike.services.RecordatorioService;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class RecordatorioView {

    private final RecordatorioService recordatorioService;

    public RecordatorioView(RecordatorioService recordatorioService) {
        this.recordatorioService = recordatorioService;
    }

    public Recordatorio createRecordatorio(int userId) {
        System.out.println("\n--- CREAR RECORDATORIO DE MANTENIMIENTO ---");
        int id = FormValidation.validateInt("Ingrese ID único del recordatorio (o 0 para autogenerar):");
        if (userId <= 0) {
            userId = FormValidation.validateInt("Ingrese el ID de Usuario:");
        }
        String title = FormValidation.validateString("Ingrese Título (ej. Cambio de Aceite, SOAT):");
        String desc = FormValidation.validateString("Ingrese Descripción o Detalles:");
        String date = FormValidation.validateString("Ingrese Fecha Límite (YYYY-MM-DD):");

        User user = new User();
        user.setId(userId);

        Recordatorio rec = new Recordatorio(id, title, desc, date, true, user);
        Recordatorio saved = recordatorioService.createRecordatorio(rec);
        System.out.println("✓ Recordatorio creado exitosamente: " + saved);
        return saved;
    }

    public void getRecordatoriosByUserId(int userId) {
        System.out.println("\n--- RECORDATORIOS ACTIVOS PARA USUARIO ID: " + userId + " ---");
        List<Recordatorio> list = recordatorioService.getRecordatoriosByUserId(userId);
        if (list.isEmpty()) {
            System.out.println("No hay recordatorios pendientes.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getAllRecordatorios() {
        System.out.println("\n--- LISTADO GENERAL DE RECORDATORIOS ---");
        List<Recordatorio> list = recordatorioService.getAllRecordatorios();
        if (list.isEmpty()) {
            System.out.println("No hay recordatorios registrados.");
        } else {
            list.forEach(System.out::println);
        }
    }
}
