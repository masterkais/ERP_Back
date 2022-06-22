package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import fr.byteCode.erp.persistance.entities.RowMaterial;

import java.util.List;

public interface IRowMaterialService extends IGenericService<RowMaterial, Long>{
    RowMaterialDto save(RowMaterialDto rowMaterialDto);

    RowMaterialDto update(RowMaterialDto rowMaterialDto);

    RowMaterialDto findById(Long id);

    List<RowMaterialDto> findAllRowMaterials();

    void delete(Long id);
}
