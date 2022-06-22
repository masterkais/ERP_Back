package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.TransfertLineDetailDto;
import fr.byteCode.erp.persistance.dto.TransfertLineDto;
import fr.byteCode.erp.persistance.entities.TransfertLineDetail;
import fr.byteCode.erp.service.services.InterfaceService.ITransfertLineService;
import fr.byteCode.erp.service.services.InterfaceService.IVehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/transfertLine")
public class TransfertLineController {
    private final ITransfertLineService transfertLineService;

    @Autowired
    private TransfertLineController(ITransfertLineService transfertLineService) {
        this.transfertLineService = transfertLineService;
    }

    @GetMapping(value = "/transfertLines")
    public List<TransfertLineDto> findAll() {
        return transfertLineService.findAllTransfertLines();
    }
    @GetMapping(value = "/transfertLines/TransfertLinesByRequestId/{idRequest}")
    public List<TransfertLineDto> findAll(@PathVariable Long idRequest) {
        return transfertLineService.findAllTransfertLinesByRequestId(idRequest);
    }
    @GetMapping(value = "/transfertLines/transfertLinesDetails/{idTransfertLine}")
    public List<TransfertLineDetail> findTransfertLineDetaiByIdLineTransfert(@PathVariable Long idTransfertLine) {
        return transfertLineService.getTransfertLineDetailByIDTranfertLineV2(idTransfertLine);
    }
    @GetMapping(value = "/transfertLines/transfertLinesDetailsV1/{idTransfertLine}")
    public List<TransfertLineDetailDto> findTransfertLineDetaiByIdLineTransfertV1(@PathVariable Long idTransfertLine) {
        return transfertLineService.getTransfertLineDetailByIDTranfertLine(idTransfertLine);
    }
    @GetMapping(value = "/{id}")
    public TransfertLineDto getTransfertLine(@PathVariable Long id) {
        return transfertLineService.findById(id);
    }

    @PostMapping()
    public TransfertLineDto save(@RequestBody @Valid TransfertLineDto transfertLineDto) {
        return transfertLineService.save(transfertLineDto);
    }

    @PutMapping()
    public TransfertLineDto update(@RequestBody @Valid TransfertLineDto transfertLineDto) {
        return transfertLineService.save(transfertLineDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        transfertLineService.delete(id);
        return true;
    }

    @PostMapping("/addLineToRequestTransfert")
    public TransfertLineDto addLineToRequestTransfert(@RequestBody @Valid TransfertLineDto transfertLine) throws Exception {
        return transfertLineService.addLineToRequestTransfert(transfertLine);
    }

    @PostMapping("/acceptLineRequestTransfert")
    public List<TransfertLineDto> acceptLineRequestTransfert(@RequestParam Long transfertLineId,@RequestParam List<Long> productIds) throws Exception {
        return transfertLineService.acceptLineTransfert(transfertLineId,productIds);
    }
    @PostMapping("/acceptLineRequestTransfertEntry")
    public List<TransfertLineDto> acceptLineRequestTransfertEntry(@RequestParam Long transfertLineId,@RequestParam List<Long> productIds) throws Exception {
        return transfertLineService.acceptLineTransfertEntry(transfertLineId,productIds);
    }
    @PostMapping("/rejectLineRequestTransfert")
    public TransfertLineDto rejectLineRequestTransfert(@RequestParam Long transfertLineId) throws Exception {
        return transfertLineService.rejectLineTransfert(transfertLineId);
    }
}
