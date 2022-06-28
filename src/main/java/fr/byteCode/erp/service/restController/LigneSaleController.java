package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.LigneSaleDto;
import fr.byteCode.erp.persistance.entities.LigneSale;
import fr.byteCode.erp.service.services.InterfaceService.ILigneSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    @GetMapping(value = "/lines/{id}")
    public LigneSaleDto getLinesBySalesId(@PathVariable Long id) {
        List<LigneSaleDto> ligneSales=new ArrayList<>();
        ligneSales=ligneSalesService.findAllLigneSale();
        List<LigneSaleDto> ligneSalesResult=new ArrayList<>();
        ligneSales.forEach((d)->{
            if(d.getSalesOrderId()==id){
                ligneSalesResult.add(d);
            }
        });
        return ligneSalesResult.get(0);
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
