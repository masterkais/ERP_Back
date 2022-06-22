package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.OrderSaleLineDetailDto;
import fr.byteCode.erp.persistance.entities.OrderSaleLineDetail;

public class OrderSaleLineDetailConverter {
    private OrderSaleLineDetailConverter() {
        super();
    }

    public static OrderSaleLineDetail dtoToModel(OrderSaleLineDetailDto orderSaleLineDetailDto) {
        return new OrderSaleLineDetail(orderSaleLineDetailDto.getId());
    }

    public static OrderSaleLineDetailDto modelToDto(OrderSaleLineDetail orderSaleLineDetail) {
        return new OrderSaleLineDetailDto(orderSaleLineDetail.getId(), orderSaleLineDetail.getLigneSale().getId(), orderSaleLineDetail.getProduct().getId());
    }
}
