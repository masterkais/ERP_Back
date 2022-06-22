package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.LigneSaleDto;
import fr.byteCode.erp.service.services.InterfaceService.ILigneSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/ligneSale")
public class LigneSaleController {

    private final ILigneSalesService ligneSalesService;

    @Autowired
    public LigneSaleController(ILigneSalesService ligneSalesService) {
        this.ligneSalesService = ligneSalesService;
    }

    @GetMapping(value = "/LigneSales")
    public List<LigneSaleDto> findAll() {
        return ligneSalesService.findAllLigneSale();
    }

    @GetMapping(value = "/{id}")
    public LigneSaleDto getProduct(@PathVariable Long id) {
        return ligneSalesService.findById(id);
    }

    @PostMapping()
    public LigneSaleDto save(@RequestBody @Valid LigneSaleDto ligneSaleDto) {
        return ligneSalesService.save(ligneSaleDto);
    }

    @PostMapping("/addLineToSalesOrder")
    public LigneSaleDto addLineToSalesOrder(@RequestBody @Valid LigneSaleDto ligneSaleDto) throws Exception {
        return ligneSalesService.addLineToSalesOrder(ligneSaleDto);
    }

    @PostMapping("/acceptLineSale")
    public List<LigneSaleDto> acceptLineSale(@RequestParam Long idLigneSaleOrder, @RequestParam List<Long> productsIds) throws Exception {
        return ligneSalesService.acceptLineSale(idLigneSaleOrder, productsIds);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        ligneSalesService.delete(id);
        return true;
    }

}
