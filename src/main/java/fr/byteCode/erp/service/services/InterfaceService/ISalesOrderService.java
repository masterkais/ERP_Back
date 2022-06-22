package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.SalesOrderDto;
import fr.byteCode.erp.persistance.entities.SalesOrder;

import java.util.List;

public interface ISalesOrderService extends IGenericService<SalesOrder, Long> {

    SalesOrderDto save(SalesOrderDto salesOrderDto);

    SalesOrderDto update(SalesOrderDto salesOrderDto);

    SalesOrderDto findById(Long id);

    List<SalesOrderDto> findAllSalesOrder();

    void delete(Long id);

    SalesOrderDto deliverSalesOrder(Long orderSaleId, List<Long> delivertManIds, List<Long> vehiculeIds) throws Exception;

    SalesOrderDto  createNewSalesOrderEmpty(Long purchasingManagerId, Long clientId);

    SalesOrderDto submitSalesOrder(Long salsOrderId) throws Exception;
}
