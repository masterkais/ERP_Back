package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.*;

import fr.byteCode.erp.service.services.ImplService.ImageService;
import fr.byteCode.erp.service.services.ImplService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    private final ImageService imageService;
    @Autowired
    public ProductController(ProductService productService,ImageService imageService) {
        this.productService = productService;
        this.imageService=imageService;
    }
    @GetMapping(value = "/products")
    public List<ProductDto> findAll() {
        return productService.findAllSProducts();
    }
    @GetMapping(value = "/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }
    @PostMapping()
    public ProductDto save(@RequestBody @Valid ProductDto productDto) throws Exception {
        return productService.save(productDto);
    }
    @PutMapping()
    public ProductDto update(@RequestBody @Valid ProductDto productDto) throws Exception {

        return productService.update(productDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        productService.delete(id);
        return true;
    }
    @GetMapping(value = "/products/categoryId/{id}")
    public List<ProductDto> findAllByCategoryId(@PathVariable Long id) {
        return productService.findAllSProductsByCategoy(id);
    }
    @GetMapping(value = "/products/siteStockId/{id}")
    public List<ProductDto> findAllBySiteStock(@PathVariable Long id) {
        return productService.findAllSProductsBySiteStock(id);
    }
    @GetMapping(value = "/products/active/{active}")
    public List<ProductDto> findAllByActive(@PathVariable boolean active) {
        return productService.findAllSProductsByActive(active);
    }
    @GetMapping(value = "/products/state/{state}")
    public List<ProductDto> findAllByState(@PathVariable boolean state) {
        return productService.findAllSProductsByState(state);
    }
    @GetMapping(value = "/products/siteStockAndCategory/{categoryId}/{siteStockId}")
    public List<ProductDto> findAllByCategoryIdAndSieStockId(@PathVariable Long categoryId,@PathVariable Long siteStockId) {
        return productService.findAllSProductsByCategoyAndSiteStock(categoryId,siteStockId);
    }
}
