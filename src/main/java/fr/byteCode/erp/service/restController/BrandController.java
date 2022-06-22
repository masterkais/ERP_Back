package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.BrandDto;
import fr.byteCode.erp.persistance.entities.Brand;
import fr.byteCode.erp.service.services.InterfaceService.IBrandService;
import fr.byteCode.erp.service.services.InterfaceService.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/brand")
public class BrandController {
    private final IBrandService brandService;
    @Autowired
    public BrandController(IBrandService brandService) {
        this.brandService=brandService;
    }
    @GetMapping(value = "/brands")
    public List<Brand> findAll() {
        return brandService.findAll();
    }
    @GetMapping(value = "/{id}")
    public BrandDto getBrand(@PathVariable Long id) {
        return brandService.findById(id);
    }
    @PostMapping()
    public BrandDto save(@RequestBody @Valid BrandDto brand) {
        return brandService.save(brand);
    }
    @PutMapping()
    public BrandDto update(@RequestBody @Valid BrandDto categoryDto) {
        return brandService.update(categoryDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        brandService.delete(id);
        return true;
    }


}
