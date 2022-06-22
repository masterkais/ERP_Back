package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.InvoiceDto;
import fr.byteCode.erp.persistance.entities.Invoice;

public final class InvoiceConverter {
    private InvoiceConverter() {
        super();
    }

    public static Invoice dtoToModel(InvoiceDto invoiceDto) {
        return new Invoice(invoiceDto.getId(), invoiceDto.getDate(), invoiceDto.getDescription(), invoiceDto.getStatus());
    }

    public static InvoiceDto modelToDto(Invoice invoice) {
        return new InvoiceDto(invoice.getId(), invoice.getDate(), invoice.getDescription(), invoice.getStatus(), invoice.getSalesOrder().getId());
    }
}
