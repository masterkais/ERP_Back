package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.ExitVoucherDto;
import fr.byteCode.erp.persistance.entities.ExitVoucher;

public final class ExitVoucherConverter {
    private ExitVoucherConverter() {
        super();
    }

    public static ExitVoucher dtoToModel(ExitVoucherDto exitVoucherDto) {
        return new ExitVoucher(exitVoucherDto.getId(), exitVoucherDto.getDateExit());
    }

    public static ExitVoucherDto modelToDto(ExitVoucher exitVoucher) {
        return new ExitVoucherDto(exitVoucher.getId(), exitVoucher.getDateExit(), exitVoucher.getRequestTransfert().getId());

    }

}
