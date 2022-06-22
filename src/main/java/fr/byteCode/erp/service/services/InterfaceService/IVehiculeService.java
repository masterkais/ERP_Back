package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.VehiculeDto;
import fr.byteCode.erp.persistance.entities.Vehicule;

import java.util.List;

public interface IVehiculeService extends IGenericService<Vehicule,Long>{
    VehiculeDto save(VehiculeDto vehiculeDto);

    VehiculeDto reserveVehicule(Long vehiculeId,Long deliveryManId);

    VehiculeDto update(VehiculeDto vehiculeDto);

    VehiculeDto findById(Long id);

    VehiculeDto getVehiculeByDriverId(Long id);

    List<VehiculeDto> findAllVehicules();

    List<VehiculeDto> getListVehiculeByState(boolean state);

    void delete(Long id);
}
