package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.InterventionDao;
import fr.byteCode.erp.persistance.dto.InterventionDto;

import fr.byteCode.erp.persistance.entities.Intervention;
import fr.byteCode.erp.service.convertor.InterventionConverter;
import fr.byteCode.erp.service.services.InterfaceService.IInterventionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class InterventionService extends GenericService<Intervention,Long> implements IInterventionService {
    @Autowired
    private InterventionDao interventionDao;
    private InterventionService(InterventionDao interventionDao) {

        this.interventionDao = interventionDao;
    }
    @Override
    public InterventionDto save(InterventionDto interventionDto) {
        Objects.requireNonNull(interventionDto);
        Intervention interventionSaved=interventionDao.saveAndFlush(InterventionConverter.dtoToModel(interventionDto));
        return InterventionConverter.modelToDto(interventionSaved);
    }

    @Override
    public InterventionDto update(InterventionDto interventionDto) {
        Objects.requireNonNull(interventionDto);
        Intervention interventionSaved = interventionDao.saveAndFlush(InterventionConverter.dtoToModel(interventionDto));
        return InterventionConverter.modelToDto(interventionSaved);    }

    @Override
    public InterventionDto findById(Long id) {

        return InterventionConverter.modelToDto(this.interventionDao.findOne(id));
    }

    @Override
    public List<InterventionDto> findALL() {

        List<Intervention> interventionList=this.interventionDao.findAll();
        List<InterventionDto> interventionDtos=new ArrayList<>();
        interventionList.forEach(intervention ->{
            interventionDtos.add(InterventionConverter.modelToDto(intervention));
        });
        return interventionDtos;
    }
    @Override
    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            interventionDao.delete(id, uuid);
        }
    }
}
