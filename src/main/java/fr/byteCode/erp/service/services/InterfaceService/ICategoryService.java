package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.CategoryDto;
import fr.byteCode.erp.persistance.entities.Category;

import java.util.List;

public interface ICategoryService extends IGenericService<Category, Long> {
    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    CategoryDto findById(Long id);

    List<CategoryDto> findAllCategorie();

    void delete(Long id);


    List<CategoryDto> findAllCategorieDispo();

    CategoryDto saveCaegory(CategoryDto categoryDto);

    CategoryDto updateCaegory(CategoryDto categoryDto);
}
