package healthandbike.services;

import healthandbike.domain.Recordatorio;
import healthandbike.domain.User;
import healthandbike.repository.RecordatorioRepository;
import healthandbike.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class RecordatorioServiceImpl implements RecordatorioService {

    private final RecordatorioRepository recordatorioRepository;
    private final UserRepository userRepository;

    public RecordatorioServiceImpl(RecordatorioRepository recordatorioRepository, UserRepository userRepository) {
        this.recordatorioRepository = recordatorioRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Recordatorio createRecordatorio(Recordatorio recordatorio) {
        if (recordatorio.getUser() != null && recordatorio.getUser().getId() > 0) {
            Optional<User> userOpt = userRepository.findUserById(recordatorio.getUser().getId());
            userOpt.ifPresent(recordatorio::setUser);
        }
        return recordatorioRepository.saveRecordatorio(recordatorio);
    }

    @Override
    public Optional<Recordatorio> getRecordatorioById(int id) {
        return recordatorioRepository.findRecordatorioById(id);
    }

    @Override
    public List<Recordatorio> getAllRecordatorios() {
        return recordatorioRepository.findAllRecordatorios();
    }

    @Override
    public List<Recordatorio> getRecordatoriosByUserId(int userId) {
        return recordatorioRepository.findRecordatoriosByUserId(userId);
    }

    @Override
    public Recordatorio updateRecordatorio(Recordatorio recordatorio) {
        return recordatorioRepository.saveRecordatorio(recordatorio);
    }

    @Override
    public boolean deleteRecordatorio(int id) {
        return recordatorioRepository.deleteRecordatorio(id);
    }
}
