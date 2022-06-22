package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import fr.byteCode.erp.persistance.entities.RowMaterial;

public class RowMaterialConverter {

    public RowMaterialConverter() {
        super();
    }

    public static RowMaterial dtoToModel(RowMaterialDto rowMaterialDto) {
        return new RowMaterial(rowMaterialDto.getId(), rowMaterialDto.getName(), rowMaterialDto.getDescription(), rowMaterialDto.getType(), rowMaterialDto.getReference(), rowMaterialDto.getState(), rowMaterialDto.getBuyingPrice());
    }

    public static RowMaterialDto modelToDto(RowMaterial rowMaterial) {
        if(rowMaterial.getProduct()!=null){
            return new RowMaterialDto(rowMaterial.getId(), rowMaterial.getName(), rowMaterial.getDescription(), rowMaterial.getType(), rowMaterial.getReference(), rowMaterial.getState(), rowMaterial.getBuyingPrice(),rowMaterial.getSiteStock().getId(),rowMaterial.getProduct().getId());
        }
        else{
            return new RowMaterialDto(rowMaterial.getId(), rowMaterial.getName(), rowMaterial.getDescription(), rowMaterial.getType(), rowMaterial.getReference(), rowMaterial.getState(), rowMaterial.getBuyingPrice());
        }
    }
}
