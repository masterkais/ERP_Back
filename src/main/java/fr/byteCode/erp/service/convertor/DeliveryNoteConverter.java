package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.DeliveryNoteDto;
import fr.byteCode.erp.persistance.dto.EntryVoucherDto;
import fr.byteCode.erp.persistance.entities.DeliveryNote;

public class DeliveryNoteConverter {
    private DeliveryNoteConverter() {
        super();
    }

    public static DeliveryNote dtoToModel(DeliveryNoteDto deliveryNoteDto) {

        return new DeliveryNote(deliveryNoteDto.getId(), deliveryNoteDto.getDateExit());
    }

    public static DeliveryNoteDto modelToDto(DeliveryNote deliveryNote) {
        return new DeliveryNoteDto(deliveryNote.getId(), deliveryNote.getDateExit(), deliveryNote.getSalesOrder().getId());
    }
}
