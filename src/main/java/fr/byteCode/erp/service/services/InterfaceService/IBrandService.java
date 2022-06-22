package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.BrandDto;
import fr.byteCode.erp.persistance.entities.Brand;

import java.util.List;

public interface IBrandService extends IGenericService<Brand, Long> {
    BrandDto save(BrandDto brandDto);

    BrandDto update(BrandDto brandDto);

    BrandDto findById(Long id);

    List<BrandDto> findAllBrands();

    void delete(Long id);


}
