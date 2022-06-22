package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.ReceivedDao;
import fr.byteCode.erp.persistance.dto.ReceivedDto;
import fr.byteCode.erp.persistance.entities.Received;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.ReceivedConverter;
import fr.byteCode.erp.service.services.InterfaceService.IReceivedService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class ReceivedService extends GenericService<Received, Long> implements IReceivedService {
    private final ReceivedDao receivedDao;

    @Autowired
    private ReceivedService(ReceivedDao receivedDao) {
        this.receivedDao = receivedDao;

    }

    @Override
    public ReceivedDto save(ReceivedDto recevedDto) {
        Objects.requireNonNull(recevedDto);
        Received receivedSaved = receivedDao.saveAndFlush(ReceivedConverter.dtoToModel(recevedDto));
        log.info(LOG_ENTITY_CREATED, receivedSaved);
        return ReceivedConverter.modelToDto(receivedSaved);
    }

    @Override
    public ReceivedDto update(ReceivedDto recevedDto) {
        Objects.requireNonNull(recevedDto);
        if (this.findById(recevedDto.getId()) != null) {
            Received receivedSaved = receivedDao.saveAndFlush(ReceivedConverter.dtoToModel(recevedDto));
            log.info(LOG_ENTITY_CREATED, receivedSaved);
            return ReceivedConverter.modelToDto(receivedSaved);
        } else
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(recevedDto.getId()));

    }

    @Override
    public ReceivedDto findById(Long id) {
        return ReceivedConverter.modelToDto(Optional.ofNullable(receivedDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<ReceivedDto> findAllRecived() {
        List<Received> receivedList = receivedDao.findAll();
        List<ReceivedDto> receivedDtos = new ArrayList<>();
        receivedList.forEach(received -> {
            receivedDtos.add(ReceivedConverter.modelToDto(received));
        });
        return receivedDtos;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            receivedDao.delete(id, uuid);
        }
    }
}
