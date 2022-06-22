package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.EntryVoucherDto;
import fr.byteCode.erp.persistance.entities.EntryVoucher;

import java.util.List;

public interface IEntryVoucherService extends IGenericService<EntryVoucher, Long> {
    EntryVoucherDto save(EntryVoucherDto entryVoucherDto);

    EntryVoucherDto update(EntryVoucherDto entryVoucherDto);

    EntryVoucherDto findById(Long id);

    List<EntryVoucherDto> findAllEntryVouchers();

    void delete(Long id);
}
