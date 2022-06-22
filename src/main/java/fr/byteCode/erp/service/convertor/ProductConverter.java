package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.ImageDto;
import fr.byteCode.erp.persistance.dto.ProductDto;
import fr.byteCode.erp.persistance.entities.Image;
import fr.byteCode.erp.persistance.entities.Product;
import fr.byteCode.erp.persistance.entities.RowMaterial;

import java.util.ArrayList;
import java.util.List;

public final class ProductConverter {
    private ProductConverter() {
        super();
    }

    public static Product dtoToModel(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getName(), productDto.getDescription(), productDto.getSellingPrice(), productDto.getBuyingPrice(), productDto.isState(), productDto.isActive());
    }

    public static ProductDto modelToDto(Product product) {
        if(product.getImages()!=null){
        List<Long> imagesIds=new ArrayList<>();
        List<Image> images=product.getImages();
        images.forEach(i->{
            imagesIds.add(i.getId());
        });
            return new ProductDto(product.getId(),product.getName(),product.getDescription(),product.getSellingPrice(), product.getBuyingPrice(), product.isState(), product.isActive(), imagesIds,product.getCategory().getId(),product.getSiteStock().getId());

        }
    else {
            return new ProductDto(product.getId(),product.getName(),product.getDescription(),product.getSellingPrice(), product.getBuyingPrice(), product.isState(), product.isActive(),product.getCategory().getId(),product.getSiteStock().getId());

        }
            }
}

