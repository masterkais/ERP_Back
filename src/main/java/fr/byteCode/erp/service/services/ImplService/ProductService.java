package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.*;
import fr.byteCode.erp.persistance.dto.ProductDto;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.persistance.entities.Image;
import fr.byteCode.erp.persistance.entities.Product;
import fr.byteCode.erp.persistance.entities.SiteStock;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.ProductConverter;
import fr.byteCode.erp.service.services.InterfaceService.IProductService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;
import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_UPDATED;

@Service
@Slf4j
public class ProductService extends GenericService<Product, Long> implements IProductService {

    private ProductDao productDao;
    private CategoryDao categoryDao;
    private RowMaterialDao rowMaterialDao;
    private SiteStockDao siteStockDao;
    private ImageDao imageDao;

    @Autowired
    private ProductService(ProductDao productDao, CategoryDao categoryDao, SiteStockDao siteStockDao, RowMaterialDao rowMaterialDao, ImageDao imageDao) {
        this.categoryDao = categoryDao;
        this.rowMaterialDao = rowMaterialDao;
        this.productDao = productDao;
        this.imageDao = imageDao;
        this.siteStockDao = siteStockDao;
    }

    @Override
    public ProductDto save(ProductDto productDto) throws Exception {
        Objects.requireNonNull(productDto);
        List<Image> images=new ArrayList<>();
        List<Long> imagesIds=productDto.getImagesIds();
        imagesIds.forEach((id)->{
            Image image=this.imageDao.findOne(id);
            images.add(image);
        });
        Product product = ProductConverter.dtoToModel(productDto);
        product.setImages(images);
        Category category = categoryDao.findOne(productDto.getCategoryId());
        product.setCategory(category);
        SiteStock siteStock = getSiteStock(productDto.getSiteStockId());
        product.setSiteStock(siteStock);
        Product productSaved = productDao.saveAndFlush(product);
        log.info(LOG_ENTITY_CREATED, productSaved);
        return ProductConverter.modelToDto(productSaved);
    }

    private SiteStock getSiteStock(Long siteStockId) throws Exception {
        try{
            return siteStockDao.getById(siteStockId);
        }catch (Exception e){
        throw new Exception("site stock not valid");
        }
    }

    @Override
    public ProductDto update(ProductDto productDto) throws Exception {
        Objects.requireNonNull(productDto);
        List<Image> images=new ArrayList<>();
        List<Long> imagesIds=productDto.getImagesIds();
        imagesIds.forEach((id)->{
            Image image=this.imageDao.findOne(id);
            images.add(image);
        });
        Product product = ProductConverter.dtoToModel(productDto);
        product.setImages(images);
        Category category = categoryDao.findOne(productDto.getCategoryId());
        product.setCategory(category);
        SiteStock siteStock = getSiteStock(productDto.getSiteStockId());
        product.setSiteStock(siteStock);
        Product productSaved = productDao.saveAndFlush(product);
        log.info(LOG_ENTITY_UPDATED, productSaved);
        return ProductConverter.modelToDto(productSaved);
    }

    @Override
    public ProductDto findById(Long id) {
        return ProductConverter.modelToDto(Optional.ofNullable(productDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<ProductDto> findAllSProducts() {
        List<Product> productList = productDao.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDtos.add(ProductConverter.modelToDto(product));
        });
        return productDtos;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            productDao.delete(id, uuid);
        }
    }

    @Override
    public List<ProductDto> findAllSProductsByCategoy(Long id) {
        List<Product> productList = productDao.getAllProductByCategoryId(id);
        List<ProductDto> productDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDtos.add(ProductConverter.modelToDto(product));
        });
        return productDtos;
    }
    @Override
    public List<ProductDto> findAllSProductsBySiteStock(Long id) {
        List<Product> productList = productDao.getAllProductBySiteStockId(id);
        List<ProductDto> productDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDtos.add(ProductConverter.modelToDto(product));
        });
        return productDtos;
    }
    @Override
    public List<ProductDto> findAllSProductsByActive(boolean active) {
        List<Product> productList = productDao.getAllProductByActive(active);
        List<ProductDto> productDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDtos.add(ProductConverter.modelToDto(product));
        });
        return productDtos;
    }
    @Override
    public List<ProductDto> findAllSProductsByState(boolean state) {
        List<Product> productList = productDao.getAllProductByState(state);
        List<ProductDto> productDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDtos.add(ProductConverter.modelToDto(product));
        });
        return productDtos;
    }
    @Override
    public List<ProductDto> findAllSProductsByCategoyAndSiteStock(Long categoryId, Long siteStockId) {
        List<Product> productList = productDao.getAllProductByCategoryIdAndSiteStockId(categoryId,siteStockId);
        List<ProductDto> productDtos = new ArrayList<>();
        productList.forEach(product -> {
            productDtos.add(ProductConverter.modelToDto(product));
        });
        return productDtos;
    }
}


