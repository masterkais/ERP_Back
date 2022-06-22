package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.CategoryDto;
import fr.byteCode.erp.service.services.InterfaceService.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/category")
public class CategoryController {
    private final ICategoryService categoryService;
    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping(value = "/categorys")
    public List<CategoryDto> findAll() {
        return categoryService.findAllCategorie();
    }
    @GetMapping(value = "/categorysDispo")
    public List<CategoryDto> findAllCategorysDispo() {
        return categoryService.findAllCategorieDispo();
    }
    @GetMapping(value = "/{id}")
    public CategoryDto getCategory(@PathVariable Long id) {
        return categoryService.findById(id);
    }
    @PostMapping()
    public CategoryDto save(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }
    @PutMapping()
    public CategoryDto updae(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.update(categoryDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        categoryService.delete(id);
        return true;
    }


}
