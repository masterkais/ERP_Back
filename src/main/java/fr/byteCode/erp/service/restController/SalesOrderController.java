package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.SalesOrderDto;
import fr.byteCode.erp.service.services.ImplService.SalesOrderService;
import fr.byteCode.erp.service.services.InterfaceService.ISalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/salesorder")
public class SalesOrderController {
    private final ISalesOrderService salesOrderService;
    @Autowired
    public SalesOrderController(ISalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @GetMapping(value = "/sales")
    public List<SalesOrderDto> findAll() {
        return salesOrderService.findAllSalesOrder();
    }

    @GetMapping(value = "/{id}")
    public SalesOrderDto getById(@PathVariable Long id) {
        return salesOrderService.findById(id);
    }

    @PostMapping()
    public SalesOrderDto save(@RequestBody @Valid SalesOrderDto salesOrderDto) {
        return salesOrderService.save(salesOrderDto);
    }

    @PostMapping("/createNewSalesOrderEmpty")
    public SalesOrderDto createNewSalesOrderEmpty(@RequestParam("purchasingManagerId") Long purchasingManagerId,@RequestParam("clientId") Long clientId) {
        return salesOrderService.createNewSalesOrderEmpty(purchasingManagerId,clientId);
    }

    @PostMapping("/submitSalesOrder")
    public SalesOrderDto submitSalesOrder(@RequestParam Long idSalesOrder) throws Exception {
        return salesOrderService.submitSalesOrder(idSalesOrder);
    }

    @PostMapping("/deliverSalesOrder")
    public SalesOrderDto deliverSalesOrder(@RequestParam Long idSalesOrder,@RequestParam List<Long> deLiveryManIds,@RequestParam List<Long> vehiculesIds) throws Exception {
        return salesOrderService.deliverSalesOrder(idSalesOrder,deLiveryManIds,vehiculesIds);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        salesOrderService.delete(id);
        return true;
    }
}
