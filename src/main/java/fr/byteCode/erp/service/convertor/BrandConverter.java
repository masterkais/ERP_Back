package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.BrandDto;
import fr.byteCode.erp.persistance.dto.CategoryDto;
import fr.byteCode.erp.persistance.entities.Brand;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.persistance.entities.Product;

import java.util.ArrayList;
import java.util.List;

public final class BrandConverter {
    private BrandConverter() {
        super();
    }

    public static Brand dtoToModel(BrandDto brandDto) {
        return new Brand(brandDto.getId(), brandDto.getName(), brandDto.getDescription());
    }

    public static BrandDto ModelToDto(Brand brand) {
        if (brand.getCategories() != null) {
            List<Long> categoryList = new ArrayList<>();
            List<Category> categories = brand.getCategories();
            categories.forEach(category -> {
                categoryList.add(category.getId());
            });
            return new BrandDto(brand.getId(), brand.getName(), brand.getDescription(), categoryList);

        } else {
            return new BrandDto(brand.getId(), brand.getName(), brand.getDescription());

        }
    }
}
