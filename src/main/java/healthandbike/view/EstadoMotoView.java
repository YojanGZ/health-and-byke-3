package healthandbike.view;

import healthandbike.domain.EstadoMoto;
import healthandbike.domain.TipoMoto;
import healthandbike.services.EstadoMotoService;
import healthandbike.services.StateSelector;
import healthandbike.utils.EstadoMotoEnum;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class EstadoMotoView {

    private final EstadoMotoService estadoMotoService;
    private final StateSelector stateSelector = new StateSelector();

    public EstadoMotoView(EstadoMotoService estadoMotoService) {
        this.estadoMotoService = estadoMotoService;
    }

    public EstadoMoto createEstado(int motoId) {
        System.out.println("\n--- REGISTRO DE ESTADO / KILOMETRAJE DE MOTO ---");
        int id = FormValidation.validateInt("Ingrese ID único del estado (o 0 para autogenerar):");
        if (motoId <= 0) {
            motoId = FormValidation.validateInt("Ingrese el ID de la Moto:");
        }
        double mileage = FormValidation.validateDouble("Ingrese Kilometraje Actual (ej. 15400.5):");
        String date = FormValidation.validateString("Ingrese Fecha del Registro (YYYY-MM-DD):");
        
        EstadoMotoEnum estadoEnum = stateSelector.selectEstadoMoto();
        String desc = FormValidation.validateString("Ingrese Observaciones o Diagnóstico del Estado:");
        String fullDesc = "[" + estadoEnum.name() + "] " + desc;

        TipoMoto moto = new TipoMoto();
        moto.setIdMoto(motoId);

        EstadoMoto estado = new EstadoMoto(id, mileage, date, fullDesc, moto);
        EstadoMoto saved = estadoMotoService.createEstado(estado);
        System.out.println("✓ Estado registrado con éxito: " + saved);
        return saved;
    }

    public void getEstadosByMotoId(int motoId) {
        System.out.println("\n--- HISTORIAL DE ESTADOS PARA MOTO ID: " + motoId + " ---");
        List<EstadoMoto> list = estadoMotoService.getEstadosByMotoId(motoId);
        if (list.isEmpty()) {
            System.out.println("No hay reportes de estado para esta moto.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void getAllEstados() {
        System.out.println("\n--- LISTADO GENERAL DE ESTADOS REGISTRADOS ---");
        List<EstadoMoto> list = estadoMotoService.getAllEstados();
        if (list.isEmpty()) {
            System.out.println("No hay registros de estado.");
        } else {
            list.forEach(System.out::println);
        }
    }
}
