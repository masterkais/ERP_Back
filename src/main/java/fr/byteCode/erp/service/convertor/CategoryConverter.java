package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.CategoryDto;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.persistance.entities.Product;

import java.util.ArrayList;
import java.util.List;

public final class CategoryConverter {
    private CategoryConverter() {
        super();
    }

    public static Category dtoToModel(CategoryDto categoryDto) {
        return new Category(categoryDto.getId(), categoryDto.getName(), categoryDto.getDescription());
    }

    public static CategoryDto ModelToDto(Category category) {
        if (category.getProductList() != null) {
            List<Long> producsIds = new ArrayList<>();
            List<Product> products = category.getProductList();
            products.forEach(product -> {
                producsIds.add(product.getId());
            });
            return new CategoryDto(category.getId(), category.getName(), category.getDescription(), producsIds,category.getBrand().getId());

        } else {
            return new CategoryDto(category.getId(), category.getName(), category.getDescription(),category.getBrand().getId());

        }
    }
}
