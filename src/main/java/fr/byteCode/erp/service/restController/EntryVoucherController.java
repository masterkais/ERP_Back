package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dao.EntryVoucherDao;
import fr.byteCode.erp.persistance.dao.RequestTransfertDao;
import fr.byteCode.erp.persistance.dto.EntryVoucherDto;
import fr.byteCode.erp.persistance.entities.EntryVoucher;
import fr.byteCode.erp.persistance.entities.RequestTransfert;
import fr.byteCode.erp.service.convertor.EntryVoucherConverter;
import fr.byteCode.erp.service.services.InterfaceService.IEntryVoucherService;
import fr.byteCode.erp.service.services.InterfaceService.IRequestTransfertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/entryVoucher")
public class EntryVoucherController {
    private final IEntryVoucherService entryVoucherService;
    private final IRequestTransfertService requestTransfertService;
    @Autowired
    private RequestTransfertDao requestTransfertDao;
    @Autowired
    private EntryVoucherDao entryVoucherDao;
    @Autowired
    private EntryVoucherController(IEntryVoucherService entryVoucherService,IRequestTransfertService requestTransfertService) {
        this.entryVoucherService = entryVoucherService;
        this.requestTransfertService=requestTransfertService;
    }

    @GetMapping(value = "/newEntryVoucher/{id}")
    public EntryVoucherDto getNewEntryVoucher(@PathVariable Long id) {
        RequestTransfert requestTransfert=requestTransfertService.findOne(id);
        requestTransfert.setState(5);
        EntryVoucher entryVoucher=new EntryVoucher();
        entryVoucher.setDateEntry(LocalDateTime.now());
        entryVoucher.setDeleted(false);
        entryVoucher.setDeletedToken(null);
        entryVoucher.setRequestTransfert(requestTransfert);
        entryVoucherDao.saveAndFlush(entryVoucher);
        requestTransfert.setEntryVoucher(entryVoucher);
        requestTransfertDao.saveAndFlush(requestTransfert);
        System.out.println("request transfert a etait modifier  avec sucéé");
        return EntryVoucherConverter.modelToDto(entryVoucher);
    }

    @GetMapping(value = "/entryVouchers")
    public List<EntryVoucherDto> findAll() {
        return entryVoucherService.findAllEntryVouchers();
    }

    @GetMapping(value = "/{id}")
    public EntryVoucherDto getEntryVoucher(@PathVariable Long id) {
        return entryVoucherService.findById(id);
    }

    @PostMapping()
    public EntryVoucherDto save(@RequestBody @Valid EntryVoucherDto entryVoucherDto) {
        return entryVoucherService.save(entryVoucherDto);
    }

    @PutMapping()
    public EntryVoucherDto update(@RequestBody @Valid EntryVoucherDto entryVoucherDto) {
        return entryVoucherService.save(entryVoucherDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        entryVoucherService.delete(id);
        return true;
    }
}
