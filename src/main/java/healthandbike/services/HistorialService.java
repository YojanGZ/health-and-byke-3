package healthandbike.services;

import healthandbike.domain.Historial;

import java.util.List;
import java.util.Optional;

public interface HistorialService {

    Historial createHistorial(Historial historial);
    Optional<Historial> getHistorialById(int id);
    List<Historial> getAllHistoriales();
    List<Historial> getHistorialesByUserId(int userId);
    Historial updateHistorial(Historial historial);
    boolean deleteHistorial(int id);
}
