package fr.byteCode.erp.testIntegrationControllers;


import fr.byteCode.erp.persistance.dao.BrandDao;
import fr.byteCode.erp.persistance.dao.CategoryDao;
import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.dto.ImageDto;
import fr.byteCode.erp.persistance.dto.ProductDto;
import fr.byteCode.erp.persistance.entities.*;
import fr.byteCode.erp.service.convertor.ImageConverter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;

import static fr.byteCode.erp.persistance.constants.EntitiesContants.PRODUCT;
import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class ProductIT extends ITConfig {

    private static final String PRODUCT_URL = "/api/product/";
    private static final String IMAGE_URL = "/api/image/";
    private static final String SITE_STOCK_URL = "/api/siteStock/";
    private HttpHeaders httpHeaders;
    private ProductDto productDto;
    private Product product;
    private SiteStock siteStock;
    private Image image;
    private Category category;
    private Brand brand;
    @Autowired
    SiteStockDao siteStockDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    BrandDao brandDao;

    @BeforeAll
    void setUp() {
        httpHeaders = getHttpHeaders();
        product = PRODUCT;
        siteStock = new SiteStock(1L, "Mega", "Mirmar", "Sidi mansour", true);
        image = new Image(1L, "data.jpg");
        brand = new Brand(1L, "Tchobiba tunis", "Tchobiba tunis");
        category = new Category(1L, "tochiba", "tochiba", brand);
        productDto = new ProductDto(1L, "tv32 tochiba", "tv32", 100, 100, true, true);
    }

    @Order(1)
    @Test
    void saveIT() {
        HttpEntity entity = new HttpEntity(productDto, httpHeaders);
        ImageDto imageDto = ImageConverter.modelToDto(image);
        ResponseEntity<ImageDto> imageDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + IMAGE_URL,
                HttpMethod.POST, entity, ImageDto.class);
        List<Long> imageIds = new ArrayList<>();
        imageIds.add(imageDto.getId());
        productDto.setImagesIds(imageIds);
        SiteStock siteStockSaved = siteStockDao.saveAndFlush(siteStock);
        productDto.setSiteStockId(siteStockSaved.getId());
        brandDao.saveAndFlush(brand);
        Category categorySaved = categoryDao.saveAndFlush(category);
        productDto.setCategoryId(categorySaved.getId());
        productDto.setRowMaterialsids(null);
        ResponseEntity<ProductDto> productDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + PRODUCT_URL,
                HttpMethod.POST, entity, ProductDto.class);
        assertNotNull(productDtoResponseEntity);
        assertEquals(HttpStatus.OK, productDtoResponseEntity.getStatusCode());
    }


    @Order(2)
    @Test
    void getProductIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<ProductDto> productDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + PRODUCT_URL + ONE,
                HttpMethod.GET, entity, ProductDto.class);

        assertNotNull(productDtoResponseEntity);
        assertEquals(HttpStatus.OK, productDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllProductsIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<ProductDto>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(PRODUCT_URL + "products"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<ProductDto>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteProductIT() {
        HttpEntity entity = new HttpEntity(productDto, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + PRODUCT_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }
}