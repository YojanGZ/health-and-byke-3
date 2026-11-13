package healthandbike.services;

import healthandbike.domain.TipoMoto;
import healthandbike.domain.User;
import healthandbike.repository.TipoMotoRepository;
import healthandbike.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class TipoMotoServiceImpl implements TipoMotoService {

    private final TipoMotoRepository tipoMotoRepository;
    private final UserRepository userRepository;

    public TipoMotoServiceImpl(TipoMotoRepository tipoMotoRepository, UserRepository userRepository) {
        this.tipoMotoRepository = tipoMotoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TipoMoto createMoto(TipoMoto moto) {
        if (moto.getUser() != null && moto.getUser().getId() > 0) {
            Optional<User> userOpt = userRepository.findUserById(moto.getUser().getId());
            userOpt.ifPresent(moto::setUser);
        }
        return tipoMotoRepository.saveMoto(moto);
    }

    @Override
    public Optional<TipoMoto> getMotoById(int id) {
        return tipoMotoRepository.findMotoById(id);
    }

    @Override
    public Optional<TipoMoto> getMotoByPlate(String plate) {
        return tipoMotoRepository.findMotoByPlate(plate);
    }

    @Override
    public List<TipoMoto> getAllMotos() {
        return tipoMotoRepository.findAllMotos();
    }

    @Override
    public List<TipoMoto> getMotosByUserId(int userId) {
        return tipoMotoRepository.findMotosByUserId(userId);
    }

    @Override
    public TipoMoto updateMoto(TipoMoto moto) {
        return tipoMotoRepository.saveMoto(moto);
    }

    @Override
    public boolean deleteMoto(int id) {
        return tipoMotoRepository.deleteMoto(id);
    }
}
