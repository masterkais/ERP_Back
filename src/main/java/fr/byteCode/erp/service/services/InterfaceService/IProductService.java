package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.ProductDto;
import fr.byteCode.erp.persistance.entities.Product;

import java.util.List;

public interface IProductService extends IGenericService<Product, Long> {
    ProductDto save(ProductDto productDto) throws Exception;

    ProductDto update(ProductDto productDto) throws Exception;

    ProductDto findById(Long id);

    List<ProductDto> findAllSProducts();

    void delete(Long id);

    List<ProductDto> findAllSProductsByCategoy(Long id);

    List<ProductDto> findAllSProductsBySiteStock(Long id);

    List<ProductDto> findAllSProductsByActive(boolean active);

    List<ProductDto> findAllSProductsByState(boolean state);

    List<ProductDto> findAllSProductsByCategoyAndSiteStock(Long categoryId, Long siteStockId);
}
