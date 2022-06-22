package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.ReceivedDto;
import fr.byteCode.erp.persistance.entities.Received;

import java.util.List;

public interface IReceivedService extends IGenericService<Received, Long> {
    ReceivedDto save(ReceivedDto recevedDto);

    ReceivedDto update(ReceivedDto recevedDto);

    ReceivedDto findById(Long id);

    List<ReceivedDto> findAllRecived();

    void delete(Long id);
}
