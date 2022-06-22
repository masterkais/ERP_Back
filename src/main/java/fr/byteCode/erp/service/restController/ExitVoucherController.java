package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.ExitVoucherDto;
import fr.byteCode.erp.service.services.InterfaceService.IExitVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/exitVoucher")
public class ExitVoucherController {
    private final IExitVoucherService exitVoucherService;

    @Autowired
    private ExitVoucherController(IExitVoucherService exitVoucherService) {
        this.exitVoucherService = exitVoucherService;
    }

    @GetMapping(value = "/exitVouchers")
    public List<ExitVoucherDto> findAll() {
        return exitVoucherService.findAllExitVouchers();
    }

    @GetMapping(value = "/{id}")
    public ExitVoucherDto getExitVoucher(@PathVariable Long id) {
        return exitVoucherService.findById(id);
    }

    @PostMapping()
    public ExitVoucherDto save(@RequestBody @Valid ExitVoucherDto exitVoucherDto) {
        return exitVoucherService.save(exitVoucherDto);
    }

    @PutMapping()
    public ExitVoucherDto update(@RequestBody @Valid ExitVoucherDto exitVoucherDto) {
        return exitVoucherService.save(exitVoucherDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        exitVoucherService.delete(id);
        return true;
    }
}
