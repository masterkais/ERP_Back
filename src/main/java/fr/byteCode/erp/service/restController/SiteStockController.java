package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.SiteStockDto;
import fr.byteCode.erp.service.services.ImplService.SiteStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/siteStock")
public class SiteStockController {
    private final SiteStockService siteStockService;

    @Autowired
    public SiteStockController(SiteStockService siteStockService) {
        this.siteStockService = siteStockService;
    }
    @GetMapping(value = "/siteStocks")
    public List<SiteStockDto> findAll() {
        return siteStockService.findAllSiteStocks();
    }
    @GetMapping(value = "/{id}")
    public SiteStockDto getSiteStock(@PathVariable Long id) {
        return siteStockService.findById(id);
    }
    @PostMapping()
    public SiteStockDto save(@RequestBody @Valid SiteStockDto siteStockDto) {
        return siteStockService.save(siteStockDto);
    }
    @PutMapping()
    public SiteStockDto update(@RequestBody @Valid SiteStockDto siteStockDto) {
        return siteStockService.update(siteStockDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        siteStockService.delete(id);
        return true;
    }
    @GetMapping(value = "/name/{name}")
    public SiteStockDto getSiteStockByName(@PathVariable String name) {
        return siteStockService.findByName(name);
    }
}
