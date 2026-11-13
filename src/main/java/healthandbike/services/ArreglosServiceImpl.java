package healthandbike.services;

import healthandbike.domain.Arreglos;
import healthandbike.domain.Historial;
import healthandbike.repository.ArreglosRepository;
import healthandbike.repository.HistorialRepository;

import java.util.List;
import java.util.Optional;

public class ArreglosServiceImpl implements ArreglosService {

    private final ArreglosRepository arreglosRepository;
    private final HistorialRepository historialRepository;

    public ArreglosServiceImpl(ArreglosRepository arreglosRepository, HistorialRepository historialRepository) {
        this.arreglosRepository = arreglosRepository;
        this.historialRepository = historialRepository;
    }

    @Override
    public Arreglos createArreglo(Arreglos arreglo) {
        if (arreglo.getHistorial() != null && arreglo.getHistorial().getIdHistorial() > 0) {
            Optional<Historial> hOpt = historialRepository.findHistorialById(arreglo.getHistorial().getIdHistorial());
            hOpt.ifPresent(arreglo::setHistorial);
        }
        return arreglosRepository.saveArreglo(arreglo);
    }

    @Override
    public Optional<Arreglos> getArregloById(int id) {
        return arreglosRepository.findArregloById(id);
    }

    @Override
    public List<Arreglos> getAllArreglos() {
        return arreglosRepository.findAllArreglos();
    }

    @Override
    public List<Arreglos> getArreglosByHistorialId(int historialId) {
        return arreglosRepository.findArreglosByHistorialId(historialId);
    }

    @Override
    public Arreglos updateArreglo(Arreglos arreglo) {
        return arreglosRepository.saveArreglo(arreglo);
    }

    @Override
    public boolean deleteArreglo(int id) {
        return arreglosRepository.deleteArreglo(id);
    }
}
