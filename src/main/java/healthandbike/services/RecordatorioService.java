package healthandbike.services;

import healthandbike.domain.Recordatorio;

import java.util.List;
import java.util.Optional;

public interface RecordatorioService {

    Recordatorio createRecordatorio(Recordatorio recordatorio);
    Optional<Recordatorio> getRecordatorioById(int id);
    List<Recordatorio> getAllRecordatorios();
    List<Recordatorio> getRecordatoriosByUserId(int userId);
    Recordatorio updateRecordatorio(Recordatorio recordatorio);
    boolean deleteRecordatorio(int id);
}
