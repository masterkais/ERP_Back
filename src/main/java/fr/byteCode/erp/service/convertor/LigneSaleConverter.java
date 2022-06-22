package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.LigneSaleDto;
import fr.byteCode.erp.persistance.entities.LigneSale;

public class LigneSaleConverter {
    private LigneSaleConverter(){super();}

    public static LigneSale dtoToModel(LigneSaleDto ligneSaleDto){
        return new LigneSale(ligneSaleDto.getId(), ligneSaleDto.getQuantity(), ligneSaleDto.getState());
    }
    public static LigneSaleDto modelToDto(LigneSale ligneSale){
        return new LigneSaleDto(ligneSale.getId(), ligneSale.getQuantity(), ligneSale.getSalesOrder().getId(), ligneSale.getCategory().getId(), ligneSale.getState());
    }

}
