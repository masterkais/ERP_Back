package fr.byteCode.erp.service.services.ImplService;
import fr.byteCode.erp.persistance.dao.PaymentDao;
import fr.byteCode.erp.persistance.dto.PaymentDto;
import fr.byteCode.erp.persistance.entities.Payment;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.PaymentConverter;
import fr.byteCode.erp.service.services.InterfaceService.IPaymentService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class PaymentService extends GenericService<Payment, Long> implements IPaymentService {
    private final PaymentDao paymentDao;

    @Autowired
    private PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    @Override
    public PaymentDto save(PaymentDto payementDto) {
        Objects.requireNonNull(payementDto);
        Payment paymentSaved = paymentDao.saveAndFlush(PaymentConverter.dtoToModel(payementDto));
        log.info(LOG_ENTITY_CREATED, paymentSaved);
        return PaymentConverter.modelToDto(paymentSaved);
    }

    @Override
    public PaymentDto update(PaymentDto payementDto) {
        Objects.requireNonNull(payementDto);
        if (this.findById(payementDto.getId()) != null) {
            Payment paymentSaved = paymentDao.saveAndFlush(PaymentConverter.dtoToModel(payementDto));
            log.info(LOG_ENTITY_CREATED, paymentSaved);
            return PaymentConverter.modelToDto(paymentSaved);
        } else
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(payementDto.getId()));

    }

    @Override
    public PaymentDto findById(Long id) {
        return PaymentConverter.modelToDto(Optional.ofNullable(paymentDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<PaymentDto> findAllPayement() {
        List<Payment> paymentList = paymentDao.findAll();
        List<PaymentDto> paymentDtos = new ArrayList<>();
        paymentList.forEach(payment -> {
            paymentDtos.add(PaymentConverter.modelToDto(payment));
        });
        return paymentDtos;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            paymentDao.delete(id, uuid);
        }
    }
}
