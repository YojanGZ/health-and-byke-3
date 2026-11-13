package healthandbike.services;

import healthandbike.domain.Arreglos;

import java.util.List;
import java.util.Optional;

public interface ArreglosService {

    Arreglos createArreglo(Arreglos arreglo);
    Optional<Arreglos> getArregloById(int id);
    List<Arreglos> getAllArreglos();
    List<Arreglos> getArreglosByHistorialId(int historialId);
    Arreglos updateArreglo(Arreglos arreglo);
    boolean deleteArreglo(int id);
}
