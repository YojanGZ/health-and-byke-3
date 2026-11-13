package healthandbike.services;

import healthandbike.domain.UbicacionTaller;

import java.util.List;
import java.util.Optional;

public interface UbicacionTallerService {

    UbicacionTaller createTaller(UbicacionTaller taller);
    Optional<UbicacionTaller> getTallerById(int id);
    List<UbicacionTaller> getAllTalleres();
    List<UbicacionTaller> getTalleresByCity(String city);
    UbicacionTaller updateTaller(UbicacionTaller taller);
    boolean deleteTaller(int id);
}
