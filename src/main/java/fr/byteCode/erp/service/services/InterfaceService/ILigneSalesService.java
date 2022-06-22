package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.LigneSaleDto;
import fr.byteCode.erp.persistance.entities.LigneSale;

import java.util.List;

public interface ILigneSalesService extends IGenericService<LigneSale, Long> {
    LigneSaleDto save(LigneSaleDto ligneSaleDto);

    LigneSaleDto update(LigneSaleDto ligneSaleDto);

    LigneSaleDto findById(Long id);

    List<LigneSaleDto> findAllLigneSale();

    void delete(Long id);

    List<LigneSaleDto> acceptLineSale(Long idLigneSale, List<Long> idProducts) throws Exception;

    LigneSaleDto rejectLineOrderSalesc(Long idLineOrderSales);

    LigneSaleDto addLineToSalesOrder(LigneSaleDto ligneSaleDto) throws Exception;
}
