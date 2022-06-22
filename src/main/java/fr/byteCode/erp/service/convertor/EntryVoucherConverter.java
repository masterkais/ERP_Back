package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.EntryVoucherDto;
import fr.byteCode.erp.persistance.entities.EntryVoucher;

public final class EntryVoucherConverter {
    private EntryVoucherConverter() {
        super();
    }

    public static EntryVoucher dtoToModel(EntryVoucherDto entryVoucherDto) {

        return new EntryVoucher(entryVoucherDto.getId(), entryVoucherDto.getDateEntry());
    }

    public static EntryVoucherDto modelToDto(EntryVoucher entryVoucher) {
        return new EntryVoucherDto(entryVoucher.getId(), entryVoucher.getDateEntry(), entryVoucher.getRequestTransfert().getId());
    }
}

