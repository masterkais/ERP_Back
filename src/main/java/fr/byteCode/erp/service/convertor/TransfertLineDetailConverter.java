package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.TransfertLineDetailDto;
import fr.byteCode.erp.persistance.entities.TransfertLineDetail;

public class TransfertLineDetailConverter {
    private TransfertLineDetailConverter() {
        super();
    }

    public static TransfertLineDetail dtoToModel(TransfertLineDetailDto transfertLineDetailDto) {
        return new TransfertLineDetail(transfertLineDetailDto.getId());
    }

    public static TransfertLineDetailDto modelToDto(TransfertLineDetail transfertLineDetail) {
        return new TransfertLineDetailDto(transfertLineDetail.getId(), transfertLineDetail.getTransfertLine().getId());
    }
}
