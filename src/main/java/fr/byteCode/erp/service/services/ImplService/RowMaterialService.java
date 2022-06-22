package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.ProductDao;
import fr.byteCode.erp.persistance.dao.RowMaterialDao;
import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import fr.byteCode.erp.persistance.entities.Product;
import fr.byteCode.erp.persistance.entities.RowMaterial;
import fr.byteCode.erp.persistance.entities.SiteStock;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.RowMaterialConverter;
import fr.byteCode.erp.service.services.InterfaceService.IRowMaterialService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class RowMaterialService extends GenericService<RowMaterial,Long> implements IRowMaterialService {
    private RowMaterialDao rowMaterialDao;
    private ProductDao productDao;
    private SiteStockDao siteStockDao;
    @Autowired
    private RowMaterialService(RowMaterialDao rowMaterialDao,ProductDao productDao,SiteStockDao siteStockDao){
        this.rowMaterialDao=rowMaterialDao;
        this.productDao=productDao;
        this.siteStockDao=siteStockDao;
    }
    @Override
    public RowMaterialDto save(RowMaterialDto rowMaterialDto) {
        Objects.requireNonNull(rowMaterialDto);
        RowMaterial rowMaterial=RowMaterialConverter.dtoToModel(rowMaterialDto);
        Product product=productDao.findOne(rowMaterialDto.getProductId());
        SiteStock siteStock=siteStockDao.findOne(rowMaterialDto.getSiteStockId());
        rowMaterial.setProduct(product);
        rowMaterial.setSiteStock(siteStock);
        RowMaterial rowMaterialSaved = rowMaterialDao.saveAndFlush(rowMaterial);
        log.info(LOG_ENTITY_CREATED, rowMaterialSaved);
        return RowMaterialConverter.modelToDto(rowMaterialSaved);
    }

    @Override
    public RowMaterialDto update(RowMaterialDto rowMaterialDto) {
        Objects.requireNonNull(rowMaterialDto);
        RowMaterial rowMaterial=RowMaterialConverter.dtoToModel(rowMaterialDto);
        Product product=productDao.findOne(rowMaterialDto.getProductId());
        SiteStock siteStock=siteStockDao.findOne(rowMaterialDto.getSiteStockId());
        RowMaterial rowMaterialSaved = rowMaterialDao.saveAndFlush(rowMaterial);
        log.info(LOG_ENTITY_CREATED, rowMaterialSaved);
        return RowMaterialConverter.modelToDto(rowMaterialSaved);
    }

    @Override
    public RowMaterialDto findById(Long id) {
        return RowMaterialConverter.modelToDto(Optional.ofNullable(rowMaterialDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));

    }

    @Override
    public List<RowMaterialDto> findAllRowMaterials() {
        List<RowMaterial> rowMaterialList= rowMaterialDao.findAll();
        List<RowMaterialDto> rowMaterialDtos = new ArrayList<>();
        rowMaterialList.forEach(rowMaterial -> {
            rowMaterialDtos.add(RowMaterialConverter.modelToDto(rowMaterial));
        });
        return rowMaterialDtos;
    }
    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            rowMaterialDao.delete(id, uuid);
        }
    }
}
