package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.dto.SiteStockDto;
import fr.byteCode.erp.persistance.entities.SiteStock;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.SiteStockConverter;
import fr.byteCode.erp.service.services.InterfaceService.ISiteStockService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;
import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_UPDATED;

@Service
@Slf4j
public class SiteStockService extends GenericService<SiteStock, Long> implements ISiteStockService {
    private SiteStockDao siteStockDao;

    @Autowired
    private SiteStockService(SiteStockDao siteStockDao) {
        this.siteStockDao = siteStockDao;
    }

    @Override
    public SiteStockDto save(SiteStockDto siteStockDto) {
        Objects.requireNonNull(siteStockDto);
        SiteStock siteStockSaved = siteStockDao.saveAndFlush(SiteStockConverter.dtoToModel(siteStockDto));
        log.info(LOG_ENTITY_CREATED, siteStockSaved);
        return SiteStockConverter.modelToDto(siteStockSaved);
    }

    @Override
    public SiteStockDto update(SiteStockDto siteStockDto) {
        Objects.requireNonNull(siteStockDto);
        SiteStock siteStockSaved = siteStockDao.saveAndFlush(SiteStockConverter.dtoToModel(siteStockDto));
        log.info(LOG_ENTITY_UPDATED, siteStockSaved);
        return SiteStockConverter.modelToDto(siteStockSaved);
    }

    @Override
    public SiteStockDto findById(Long id) {
        return SiteStockConverter.modelToDto(Optional.ofNullable(siteStockDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<SiteStockDto> findAllSiteStocks() {
        List<SiteStock> siteStockList = siteStockDao.findAll();
        List<SiteStockDto> siteStockDtos = new ArrayList<>();
        siteStockList.forEach(siteSock -> {
            siteStockDtos.add(SiteStockConverter.modelToDto(siteSock));
        });
        return siteStockDtos;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            siteStockDao.delete(id, uuid);
        }
    }
    @Override
    public SiteStockDto findByName(String name) {
        return SiteStockConverter.modelToDto(Optional.ofNullable(siteStockDao.findSiteStockByName(name)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(name))));

    }
}
