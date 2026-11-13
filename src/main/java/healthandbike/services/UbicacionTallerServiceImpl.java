package healthandbike.services;

import healthandbike.domain.UbicacionTaller;
import healthandbike.repository.UbicacionTallerRepository;

import java.util.List;
import java.util.Optional;

public class UbicacionTallerServiceImpl implements UbicacionTallerService {

    private final UbicacionTallerRepository tallerRepository;

    public UbicacionTallerServiceImpl(UbicacionTallerRepository tallerRepository) {
        this.tallerRepository = tallerRepository;
    }

    @Override
    public UbicacionTaller createTaller(UbicacionTaller taller) {
        return tallerRepository.saveTaller(taller);
    }

    @Override
    public Optional<UbicacionTaller> getTallerById(int id) {
        return tallerRepository.findTallerById(id);
    }

    @Override
    public List<UbicacionTaller> getAllTalleres() {
        return tallerRepository.findAllTalleres();
    }

    @Override
    public List<UbicacionTaller> getTalleresByCity(String city) {
        return tallerRepository.findTalleresByCity(city);
    }

    @Override
    public UbicacionTaller updateTaller(UbicacionTaller taller) {
        return tallerRepository.saveTaller(taller);
    }

    @Override
    public boolean deleteTaller(int id) {
        return tallerRepository.deleteTaller(id);
    }
}
