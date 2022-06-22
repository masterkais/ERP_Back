package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.InterventionDto;
import fr.byteCode.erp.persistance.entities.Intervention;

import java.util.List;

public interface IInterventionService extends IGenericService<Intervention,Long>{
    InterventionDto save(InterventionDto interventionDto);

    InterventionDto update(InterventionDto interventionDto);

    InterventionDto findById(Long id);

    List<InterventionDto> findALL();

    void delete(Long id);
}
