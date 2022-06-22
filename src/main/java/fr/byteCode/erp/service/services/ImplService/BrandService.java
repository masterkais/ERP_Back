package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.BrandDao;
import fr.byteCode.erp.persistance.dto.BrandDto;
import fr.byteCode.erp.persistance.entities.Brand;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.BrandConverter;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import fr.byteCode.erp.service.services.InterfaceService.IBrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.*;

@Service
@Slf4j
public class BrandService extends GenericService<Brand,Long> implements IBrandService {
    @Autowired
    BrandDao brandDao;
    @Override
    public BrandDto save(BrandDto brandDto) {
        Objects.requireNonNull(brandDto);
        checkValidName(brandDto.getName());
        Brand  brandSaved= brandDao.saveAndFlush(BrandConverter.dtoToModel(brandDto));
        log.info(LOG_ENTITY_CREATED, brandSaved);
        return BrandConverter.ModelToDto(brandSaved);

    }
    public void checkValidName(String name) {
        if (brandDao.findByName(name) != null) {
            log.error(LOG_ENTITY_NAME_EXIST, name);
            throw new HttpCustomException(ApiErrors.OBJECT_NAME_EXISTS, new ErrorsResponse().error(name));
        }
    }
    @Override
    public BrandDto update(BrandDto brandDto) {
        Objects.requireNonNull(brandDto);
        checkBrandExistById(brandDto.getId());
        checkValidName(brandDto.getName());
        Brand  brandSaved= brandDao.saveAndFlush(BrandConverter.dtoToModel(brandDto));
        log.info(LOG_ENTITY_UPDATED, brandSaved);
        return BrandConverter.ModelToDto(brandSaved);
    }

    private void checkBrandExistById(Long id) {
        if (brandDao.findOne(id) == null) {
            log.error(LOG_ENTITY_NOT_EXIST, id);
            throw new HttpCustomException(ApiErrors.OBJECT_ID_NOT_EXISTS, new ErrorsResponse().error(id));
        }
    }

    @Override
    public BrandDto findById(Long id) {
        return BrandConverter.ModelToDto(Optional.ofNullable(brandDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));

    }
    @Override
    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        BrandDto brandDto = findById(id);
        if (findById(id) != null) {
            brandDao.delete(brandDto.getId(), uuid);
        }
    }

    @Override
    public List<BrandDto> findAllBrands() {
        List<Brand> brandList = brandDao.findAll();
        List<BrandDto> brandDtos = new ArrayList<>();
        brandList.forEach(brand -> {
            brandDtos.add(BrandConverter.ModelToDto(brand));
        });
        return brandDtos;
    }
}
