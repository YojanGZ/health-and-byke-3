package healthandbike.services;

import healthandbike.domain.TipoMoto;

import java.util.List;
import java.util.Optional;

public interface TipoMotoService {

    TipoMoto createMoto(TipoMoto moto);
    Optional<TipoMoto> getMotoById(int id);
    Optional<TipoMoto> getMotoByPlate(String plate);
    List<TipoMoto> getAllMotos();
    List<TipoMoto> getMotosByUserId(int userId);
    TipoMoto updateMoto(TipoMoto moto);
    boolean deleteMoto(int id);
}
