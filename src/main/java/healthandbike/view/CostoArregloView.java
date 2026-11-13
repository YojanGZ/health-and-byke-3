package healthandbike.view;

import healthandbike.domain.Arreglos;
import healthandbike.domain.CostoArreglo;
import healthandbike.services.CostoArregloService;
import healthandbike.utils.FormValidation;

import java.util.List;
import java.util.Optional;

public class CostoArregloView {

    private final CostoArregloService costoArregloService;

    public CostoArregloView(CostoArregloService costoArregloService) {
        this.costoArregloService = costoArregloService;
    }

    public CostoArreglo createCosto(int arregloId) {
        System.out.println("\n--- REGISTRO / CÁLCULO DE COSTO DE ARREGLO ---");
        int id = FormValidation.validateInt("Ingrese ID único del costo (o 0 para autogenerar):");
        if (arregloId <= 0) {
            arregloId = FormValidation.validateInt("Ingrese el ID del Arreglo asociado:");
        }
        double spare = FormValidation.validateDouble("Ingrese Costo de Repuestos / Insumos ($):");
        double labor = FormValidation.validateDouble("Ingrese Costo de Mano de Obra ($):");
        double total = spare + labor;
        System.out.println("Cálculo automático de Total: $" + total);

        Arreglos arreglo = new Arreglos();
        arreglo.setIdArreglo(arregloId);

        CostoArreglo costo = new CostoArreglo(id, spare, labor, total, arreglo);
        CostoArreglo saved = costoArregloService.createCosto(costo);
        System.out.println("✓ Costo registrado exitosamente: " + saved);
        return saved;
    }

    public void getCostoByArregloId(int arregloId) {
        Optional<CostoArreglo> opt = costoArregloService.getCostoByArregloId(arregloId);
        if (opt.isPresent()) {
            System.out.println("\n✓ Costo del Arreglo ID " + arregloId + ":\n" + opt.get());
        } else {
            System.out.println("✗ No se ha registrado costo para el arreglo ID: " + arregloId);
        }
    }

    public void getAllCostos() {
        System.out.println("\n--- LISTADO GENERAL DE COSTOS DE ARREGLOS ---");
        List<CostoArreglo> list = costoArregloService.getAllCostos();
        if (list.isEmpty()) {
            System.out.println("No hay costos registrados.");
        } else {
            list.forEach(System.out::println);
        }
    }
}
