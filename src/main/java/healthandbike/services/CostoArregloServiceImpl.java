package healthandbike.services;

import healthandbike.domain.Arreglos;
import healthandbike.domain.CostoArreglo;
import healthandbike.repository.ArreglosRepository;
import healthandbike.repository.CostoArregloRepository;

import java.util.List;
import java.util.Optional;

public class CostoArregloServiceImpl implements CostoArregloService {

    private final CostoArregloRepository costoArregloRepository;
    private final ArreglosRepository arreglosRepository;

    public CostoArregloServiceImpl(CostoArregloRepository costoArregloRepository, ArreglosRepository arreglosRepository) {
        this.costoArregloRepository = costoArregloRepository;
        this.arreglosRepository = arreglosRepository;
    }

    @Override
    public CostoArreglo createCosto(CostoArreglo costo) {
        if (costo.getArreglos() != null && costo.getArreglos().getIdArreglo() > 0) {
            Optional<Arreglos> aOpt = arreglosRepository.findArregloById(costo.getArreglos().getIdArreglo());
            aOpt.ifPresent(costo::setArreglos);
        }
        costo.setTotalAmount(costo.getSparePartsCost() + costo.getLaborCost());
        return costoArregloRepository.saveCosto(costo);
    }

    @Override
    public Optional<CostoArreglo> getCostoById(int id) {
        return costoArregloRepository.findCostoById(id);
    }

    @Override
    public Optional<CostoArreglo> getCostoByArregloId(int arregloId) {
        return costoArregloRepository.findCostoByArregloId(arregloId);
    }

    @Override
    public List<CostoArreglo> getAllCostos() {
        return costoArregloRepository.findAllCostos();
    }

    @Override
    public CostoArreglo updateCosto(CostoArreglo costo) {
        costo.setTotalAmount(costo.getSparePartsCost() + costo.getLaborCost());
        return costoArregloRepository.saveCosto(costo);
    }

    @Override
    public boolean deleteCosto(int id) {
        return costoArregloRepository.deleteCosto(id);
    }
}
