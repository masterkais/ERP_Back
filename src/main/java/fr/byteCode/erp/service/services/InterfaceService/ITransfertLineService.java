package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.TransfertLineDetailDto;
import fr.byteCode.erp.persistance.dto.TransfertLineDto;
import fr.byteCode.erp.persistance.entities.TransfertLine;
import fr.byteCode.erp.persistance.entities.TransfertLineDetail;

import java.util.List;

public interface ITransfertLineService extends IGenericService<TransfertLine,Long> {
    TransfertLineDto save(TransfertLineDto transfertLineDto);

    TransfertLineDto update(TransfertLineDto TransfertLineDto);

    TransfertLineDto findById(Long id);

    List<TransfertLineDto> findAllTransfertLines();

    void delete(Long id);

    List<TransfertLineDto> findAllTransfertLinesByRequestId(Long idRequest);

    List<TransfertLineDto> acceptLineTransfert(Long idTransfertLine, List<Long> idProducts) throws Exception;

    TransfertLineDto rejectLineTransfert(Long idTransfertLine);

    TransfertLineDto addLineToRequestTransfert(TransfertLineDto transfertLineDto) throws Exception;

    List<TransfertLineDetail> getAllTransfertLineDetailByIDTranfertLine(Long idLineTransfert);

    List<TransfertLineDetailDto> getTransfertLineDetailByIDTranfertLine(Long idLineTransfert);

    List<TransfertLineDetail> getTransfertLineDetailByIDTranfertLineV2(Long idLineTransfert);

    List<TransfertLineDto> acceptLineTransfertEntry(Long transfertLineId, List<Long> productIds)throws Exception;
}
