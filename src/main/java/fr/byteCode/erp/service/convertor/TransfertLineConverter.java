package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.TransfertLineDto;
import fr.byteCode.erp.persistance.entities.TransfertLine;


public class TransfertLineConverter {
    private TransfertLineConverter(){
        super();
    }
    public static TransfertLine dtoToModel(TransfertLineDto transfertLineDto){
        return new TransfertLine(transfertLineDto.getId(), transfertLineDto.getQuantity());
    }
    public static TransfertLineDto modelToDto(TransfertLine transfertLine){
        return new TransfertLineDto(transfertLine.getId(),transfertLine.getQuantity(),transfertLine.getRequestTransfert().getId(),transfertLine.getCategory().getId(), transfertLine.getState());
    }
}

