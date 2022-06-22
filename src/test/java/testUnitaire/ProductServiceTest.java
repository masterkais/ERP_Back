package testUnitaire;


import fr.byteCode.erp.persistance.dao.CategoryDao;
import fr.byteCode.erp.persistance.dao.ImageDao;
import fr.byteCode.erp.persistance.dao.ProductDao;
import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.dto.ProductDto;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.persistance.entities.Image;
import fr.byteCode.erp.persistance.entities.Product;
import fr.byteCode.erp.persistance.entities.SiteStock;
import fr.byteCode.erp.service.convertor.ProductConverter;
import fr.byteCode.erp.service.services.ImplService.ProductService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static fr.byteCode.erp.persistance.constants.EntitiesContants.PRODUCT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductDao productDao;
    @Mock
    private CategoryDao categoryDao;
    @Mock
    private SiteStockDao siteStockDao;
    @Mock
    private ImageDao imageDao;
    private Product product;
    private ProductDto productDto;
    private List<Product> productList;
    private Category category;
    private SiteStock siteStock;
    private Image image;
    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);
        product = PRODUCT;
        product.setImages(null);
        siteStock=new SiteStock();
        product.setSiteStock(siteStock);
        category=new Category();
        product.setCategory(category);
        image=new Image();
        List<Image> imageList=new ArrayList<>();
        imageList.add(image);
        product.setImages(imageList);
        productDto = ProductConverter.modelToDto(product);
        productList = new ArrayList<Product>();
        productList.add(product);
    }

    @AfterEach
    void tearDown() {
        productList = null;
        product = null;
        productDto = null;
    }

    @Test
    @DisplayName("test for save")
    public void saveProductTest() throws Exception {
        when(productDao.saveAndFlush(product)).thenReturn(product);
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        when(siteStockDao.findOne(anyLong())).thenReturn(siteStock);
        when(imageDao.findOne(anyLong())).thenReturn(image);
        ProductDto productDtoSaved = productService.save(productDto);
        assertEquals(productDtoSaved, productDto);
        verify(productDao, times(1)).saveAndFlush(product);
    }

    @Test
    @DisplayName("test for update")
    public void updateCategoryTest() throws Exception {
        when(productDao.findOne(anyLong())).thenReturn(product);
        when(productDao.saveAndFlush(product)).thenReturn(product);
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        when(siteStockDao.findOne(anyLong())).thenReturn(siteStock);
        when(imageDao.findOne(anyLong())).thenReturn(image);
        ProductDto productDtoResult = productService.findById(product.getId());
        ProductDto productDtoSaved = productService.save(productDto);
        assertEquals(productDtoResult, productDto);
        assertEquals(productDtoSaved, productDto);
        assertDoesNotThrow(() -> productService.findById(product.getId()));
        verify(productDao, times(2)).findOne(product.getId());
        verify(productDao, times(1)).saveAndFlush(product);


    }

    @DisplayName("test for get all product")
    @Test
    public void allProdutTest() {
        //GIVEN
        when(productDao.findAll()).thenReturn(productList);
        //WHEN
        List<ProductDto> results = productService.findAllSProducts();
        //THEN
        assertNotNull(results);
        verify(productDao, times(1)).findAll();
    }

    @DisplayName("test for delete by id")
    @Test
    public void testDeleteProdut() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(productDao.findOne(anyLong())).thenReturn(product);
        assertDoesNotThrow(
                () -> {
                    productService.delete(product.getId());
                }
        );
        verify(productDao, times(1)).findOne(product.getId());
    }


    @DisplayName("test for get category by id")
    @Test
    public void testGetProductById() {
        when(productDao.findOne(anyLong())).thenReturn(product);
        ProductDto productDtoResult = productService.findById(product.getId());
        assertEquals(productDtoResult, productDto);
        assertDoesNotThrow(() -> productService.findById(product.getId()));
        verify(productDao, times(2)).findOne(product.getId());
    }

}