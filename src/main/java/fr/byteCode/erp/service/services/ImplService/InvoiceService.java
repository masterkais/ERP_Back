package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.InvoiceDao;
import fr.byteCode.erp.persistance.dto.InvoiceDto;
import fr.byteCode.erp.persistance.entities.Invoice;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.InvoiceConverter;
import fr.byteCode.erp.service.services.InterfaceService.IInvoiceService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class InvoiceService extends GenericService<Invoice, Long> implements IInvoiceService {
    private InvoiceDao invoiceDao;

    @Autowired
    private InvoiceService(InvoiceDao invoiceDao) {
        this.invoiceDao = invoiceDao;
    }

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto) {
        Objects.requireNonNull(invoiceDto);
        Invoice invoiceSaved = invoiceDao.saveAndFlush(InvoiceConverter.dtoToModel(invoiceDto));
        log.info(LOG_ENTITY_CREATED, invoiceSaved);
        return InvoiceConverter.modelToDto(invoiceSaved);

    }

    @Override
    public InvoiceDto update(InvoiceDto invoiceDto) {
        Objects.requireNonNull(invoiceDto);
        if (this.findById(invoiceDto.getId()) != null) {
            Invoice invoiceSaved = invoiceDao.saveAndFlush(InvoiceConverter.dtoToModel(invoiceDto));
            log.info(LOG_ENTITY_CREATED, invoiceSaved);
            return InvoiceConverter.modelToDto(invoiceSaved);
        } else
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(invoiceDto.getId()));
    }

    @Override
    public InvoiceDto findById(Long id) {
        return InvoiceConverter.modelToDto(Optional.ofNullable(invoiceDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));

    }

    @Override
    public List<InvoiceDto> findAllInvoice() {
        List<Invoice> invoiceList = invoiceDao.findAll();
        List<InvoiceDto> invoiceDtos = new ArrayList<>();
        invoiceList.forEach(invoice -> {
            invoiceDtos.add(InvoiceConverter.modelToDto(invoice));
        });
        return invoiceDtos;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            invoiceDao.delete(id, uuid);
        }
    }


}
