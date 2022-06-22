package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.ExitVoucherDto;
import fr.byteCode.erp.persistance.entities.ExitVoucher;

import java.util.List;

public interface IExitVoucherService extends IGenericService<ExitVoucher,Long> {
    ExitVoucherDto save(ExitVoucherDto exitVoucherDto);

    ExitVoucherDto update(ExitVoucherDto exitVoucherDto);

    ExitVoucherDto findById(Long id);

    List<ExitVoucherDto> findAllExitVouchers();

    void delete(Long id);
}
