package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.InvoiceDto;
import fr.byteCode.erp.persistance.entities.Invoice;

import java.util.List;

public interface IInvoiceService extends IGenericService<Invoice,Long>{
    InvoiceDto save(InvoiceDto invoiceDto);

    InvoiceDto update(InvoiceDto invoiceDto);

    InvoiceDto findById(Long id);

    List<InvoiceDto> findAllInvoice();

    void delete(Long id);
}
