package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.PaymentDto;
import fr.byteCode.erp.persistance.entities.Payment;

import java.time.LocalDateTime;

public class PaymentConverter {
    public static Payment dtoToModel(PaymentDto payementDto) {
        return new Payment(payementDto.getId(), payementDto.getDate(), payementDto.getOrigin());
    }

    public static PaymentDto modelToDto(Payment payment) {
        return new PaymentDto(payment.getId(), payment.getDate(), payment.getOrigin(), payment.getInvoice().getId());
    }
}
