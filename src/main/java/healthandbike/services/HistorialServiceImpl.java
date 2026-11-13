package healthandbike.services;

import healthandbike.domain.Historial;
import healthandbike.domain.UbicacionTaller;
import healthandbike.domain.User;
import healthandbike.repository.HistorialRepository;
import healthandbike.repository.UbicacionTallerRepository;
import healthandbike.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class HistorialServiceImpl implements HistorialService {

    private final HistorialRepository historialRepository;
    private final UserRepository userRepository;
    private final UbicacionTallerRepository tallerRepository;

    public HistorialServiceImpl(HistorialRepository historialRepository, UserRepository userRepository, UbicacionTallerRepository tallerRepository) {
        this.historialRepository = historialRepository;
        this.userRepository = userRepository;
        this.tallerRepository = tallerRepository;
    }

    @Override
    public Historial createHistorial(Historial historial) {
        if (historial.getUser() != null && historial.getUser().getId() > 0) {
            Optional<User> userOpt = userRepository.findUserById(historial.getUser().getId());
            userOpt.ifPresent(historial::setUser);
        }
        if (historial.getTaller() != null && historial.getTaller().getIdTaller() > 0) {
            Optional<UbicacionTaller> tallerOpt = tallerRepository.findTallerById(historial.getTaller().getIdTaller());
            tallerOpt.ifPresent(historial::setTaller);
        }
        return historialRepository.saveHistorial(historial);
    }

    @Override
    public Optional<Historial> getHistorialById(int id) {
        return historialRepository.findHistorialById(id);
    }

    @Override
    public List<Historial> getAllHistoriales() {
        return historialRepository.findAllHistoriales();
    }

    @Override
    public List<Historial> getHistorialesByUserId(int userId) {
        return historialRepository.findHistorialesByUserId(userId);
    }

    @Override
    public Historial updateHistorial(Historial historial) {
        return historialRepository.saveHistorial(historial);
    }

    @Override
    public boolean deleteHistorial(int id) {
        return historialRepository.deleteHistorial(id);
    }
}
