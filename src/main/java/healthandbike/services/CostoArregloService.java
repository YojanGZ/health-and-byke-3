package healthandbike.services;

import healthandbike.domain.CostoArreglo;

import java.util.List;
import java.util.Optional;

public interface CostoArregloService {

    CostoArreglo createCosto(CostoArreglo costo);
    Optional<CostoArreglo> getCostoById(int id);
    Optional<CostoArreglo> getCostoByArregloId(int arregloId);
    List<CostoArreglo> getAllCostos();
    CostoArreglo updateCosto(CostoArreglo costo);
    boolean deleteCosto(int id);
}
