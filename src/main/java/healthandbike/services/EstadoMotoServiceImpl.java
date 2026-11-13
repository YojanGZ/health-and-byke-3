package healthandbike.services;

import healthandbike.domain.EstadoMoto;
import healthandbike.domain.TipoMoto;
import healthandbike.repository.EstadoMotoRepository;
import healthandbike.repository.TipoMotoRepository;

import java.util.List;
import java.util.Optional;

public class EstadoMotoServiceImpl implements EstadoMotoService {

    private final EstadoMotoRepository estadoMotoRepository;
    private final TipoMotoRepository tipoMotoRepository;

    public EstadoMotoServiceImpl(EstadoMotoRepository estadoMotoRepository, TipoMotoRepository tipoMotoRepository) {
        this.estadoMotoRepository = estadoMotoRepository;
        this.tipoMotoRepository = tipoMotoRepository;
    }

    @Override
    public EstadoMoto createEstado(EstadoMoto estado) {
        if (estado.getTipoMoto() != null && estado.getTipoMoto().getIdMoto() > 0) {
            Optional<TipoMoto> motoOpt = tipoMotoRepository.findMotoById(estado.getTipoMoto().getIdMoto());
            motoOpt.ifPresent(estado::setTipoMoto);
        }
        return estadoMotoRepository.saveEstado(estado);
    }

    @Override
    public Optional<EstadoMoto> getEstadoById(int id) {
        return estadoMotoRepository.findEstadoById(id);
    }

    @Override
    public List<EstadoMoto> getAllEstados() {
        return estadoMotoRepository.findAllEstados();
    }

    @Override
    public List<EstadoMoto> getEstadosByMotoId(int motoId) {
        return estadoMotoRepository.findEstadosByMotoId(motoId);
    }

    @Override
    public EstadoMoto updateEstado(EstadoMoto estado) {
        return estadoMotoRepository.saveEstado(estado);
    }

    @Override
    public boolean deleteEstado(int id) {
        return estadoMotoRepository.deleteEstado(id);
    }
}
