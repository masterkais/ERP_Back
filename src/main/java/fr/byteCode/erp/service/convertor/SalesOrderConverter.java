package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.SalesOrderDto;
import fr.byteCode.erp.persistance.entities.LigneSale;
import fr.byteCode.erp.persistance.entities.SalesOrder;
import fr.byteCode.erp.persistance.entities.User;

import java.util.ArrayList;
import java.util.List;

public class SalesOrderConverter {
    private SalesOrderConverter() {
        super();
    }

    public static SalesOrder dtoToModel(SalesOrderDto salesOrderDto) {
        return new SalesOrder(salesOrderDto.getId(), salesOrderDto.getDateCreated(), salesOrderDto.getDateAccpted(), salesOrderDto.getNumberPalette(), salesOrderDto.getState(), salesOrderDto.getSiteStockSaleName(),salesOrderDto.getTotalSale());
    }

    public static SalesOrderDto modelToDto(SalesOrder salesOrder) {
        List<User> deliveryManList = salesOrder.getDeliveryManList();
        List<Long> deliveryManIdList = new ArrayList<>();
        if (salesOrder.getDeliveryManList() != null) {
            deliveryManList.forEach(deliveryMan -> {
                deliveryManIdList.add(deliveryMan.getId());
            });
        }
        List<LigneSale> ligneSales = salesOrder.getLigneSales();
        List<Long> lineSalesIdList = new ArrayList<>();
        if (salesOrder.getLigneSales() != null) {
            ligneSales.forEach(ligneSale -> {
                lineSalesIdList.add(ligneSale.getId());
            });
        }
        return new SalesOrderDto(salesOrder.getId(), salesOrder.getDateCreated(), null, salesOrder.getNumberPalette(), deliveryManIdList, salesOrder.getPurchasingManager().getId(), lineSalesIdList, salesOrder.getState(), salesOrder.getClient().getId(), salesOrder.getSiteStockSale(), salesOrder.getTotalSale());
    }

}
