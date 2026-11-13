package healthandbike.services;

import healthandbike.utils.EstadoArregloEnum;
import healthandbike.utils.EstadoMotoEnum;
import healthandbike.utils.FormValidation;

public class StateSelector {

    public EstadoMotoEnum selectEstadoMoto() {
        System.out.println("Seleccione el estado de la moto: ");
        System.out.println("1. DISPONIBLE  2. EN_MANTENIMIENTO  3. FUERA_DE_SERVICIO");
        int option = FormValidation.validateInt("Opción:");

        switch (option) {
            case 1:
                return EstadoMotoEnum.DISPONIBLE;
            case 2:
                return EstadoMotoEnum.EN_MANTENIMIENTO;
            case 3:
                return EstadoMotoEnum.FUERA_DE_SERVICIO;
            default:
                System.out.println("Opción no válida. Se asigna por defecto: DISPONIBLE.");
                return EstadoMotoEnum.DISPONIBLE;
        }
    }

    public EstadoArregloEnum selectEstadoArreglo() {
        System.out.println("Seleccione el estado del arreglo: ");
        System.out.println("1. PENDIENTE  2. EN_PROCESO  3. COMPLETADO  4. CANCELADO");
        int option = FormValidation.validateInt("Opción:");

        switch (option) {
            case 1:
                return EstadoArregloEnum.PENDIENTE;
            case 2:
                return EstadoArregloEnum.EN_PROCESO;
            case 3:
                return EstadoArregloEnum.COMPLETADO;
            case 4:
                return EstadoArregloEnum.CANCELADO;
            default:
                System.out.println("Opción no válida. Se asigna por defecto: PENDIENTE.");
                return EstadoArregloEnum.PENDIENTE;
        }
    }
}
