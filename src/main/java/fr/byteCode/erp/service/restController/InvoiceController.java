package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.InvoiceDto;
import fr.byteCode.erp.service.services.ImplService.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    @Autowired
    public InvoiceController(InvoiceService invoiceService){
        this.invoiceService=invoiceService;
    }
    @GetMapping(value = "/")
    public List<InvoiceDto> findAll() {
        return invoiceService.findAllInvoice();
    }
    @GetMapping(value = "/{id}")
    public InvoiceDto getProduct(@PathVariable Long id) {
        return invoiceService.findById(id);
    }



    @PostMapping()
    public InvoiceDto save(@RequestBody @Valid InvoiceDto invoiceDto) {
        return invoiceService.save(invoiceDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        invoiceService.delete(id);
        return true;
    }
}
