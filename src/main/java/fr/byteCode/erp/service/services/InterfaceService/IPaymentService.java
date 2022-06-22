package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.PaymentDto;
import fr.byteCode.erp.persistance.entities.Payment;

import java.util.List;

public interface IPaymentService extends IGenericService<Payment, Long> {
    PaymentDto save(PaymentDto payementDto);

    PaymentDto update(PaymentDto payementDto);

    PaymentDto findById(Long id);

    List<PaymentDto> findAllPayement();

    void delete(Long id);
}
