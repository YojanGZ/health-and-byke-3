package healthandbike.services;

import healthandbike.domain.EstadoMoto;

import java.util.List;
import java.util.Optional;

public interface EstadoMotoService {

    EstadoMoto createEstado(EstadoMoto estado);
    Optional<EstadoMoto> getEstadoById(int id);
    List<EstadoMoto> getAllEstados();
    List<EstadoMoto> getEstadosByMotoId(int motoId);
    EstadoMoto updateEstado(EstadoMoto estado);
    boolean deleteEstado(int id);
}
