package testUnitaire;


import fr.byteCode.erp.persistance.dao.ProductDao;
import fr.byteCode.erp.persistance.dao.RowMaterialDao;
import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import fr.byteCode.erp.persistance.entities.Product;
import fr.byteCode.erp.persistance.entities.RowMaterial;
import fr.byteCode.erp.persistance.entities.SiteStock;
import fr.byteCode.erp.service.convertor.RowMaterialConverter;
import fr.byteCode.erp.service.services.ImplService.RowMaterialService;
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


public class RowMaterialServiceTest {
    @InjectMocks
    private RowMaterialService rowMaterialService;

    @Mock
    private RowMaterialDao rowMaterialDao;
    @Mock
    private ProductDao productDao;
    @Mock
    private SiteStockDao siteStockDao;
    private RowMaterial rowMaterial;
    private RowMaterialDto rowMaterialDto;
    private List<RowMaterial> rowMaterialList;
    private Product product;
    private SiteStock siteStock;
    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);
        product = PRODUCT;
        product.setImages(null);
        siteStock=new SiteStock();
        product.setSiteStock(siteStock);
        rowMaterial=new RowMaterial(1L, "piéce tactile", "marque tochiba", "aluminuim", "01102145", true, 1000);
        rowMaterial.setSiteStock(siteStock);
        rowMaterial.setProduct(product);
        rowMaterialDto = RowMaterialConverter.modelToDto(rowMaterial);
        rowMaterialList = new ArrayList<RowMaterial>();
        rowMaterialList.add(rowMaterial);
    }

    @AfterEach
    void tearDown() {
        rowMaterialList = null;
        rowMaterial = null;
        rowMaterialDto = null;
    }

    @Test
    @DisplayName("test for save")
    public void saveProductTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(rowMaterialDao.saveAndFlush(rowMaterial)).thenReturn(rowMaterial);
        RowMaterialDto rowMaterialDtoSaved = rowMaterialService.save(rowMaterialDto);
        assertEquals(rowMaterialDtoSaved, rowMaterialDto);
        verify(rowMaterialDao, times(1)).saveAndFlush(rowMaterial);
    }

    @Test
    @DisplayName("test for update")
    public void updateCategoryTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(rowMaterialDao.findOne(anyLong())).thenReturn(rowMaterial);
        when(rowMaterialDao.saveAndFlush(rowMaterial)).thenReturn(rowMaterial);
        RowMaterialDto rowMaterialDtoResult = rowMaterialService.findById(rowMaterial.getId());
        RowMaterialDto rowMaterialDtoSaved = rowMaterialService.save(rowMaterialDto);
        assertEquals(rowMaterialDtoSaved, rowMaterialDto);
        assertEquals(rowMaterialDtoResult,rowMaterialDto);
        assertDoesNotThrow(() -> rowMaterialService.findById(rowMaterial.getId()));
        verify(rowMaterialDao, times(2)).findOne(rowMaterial.getId());
        verify(rowMaterialDao, times(1)).saveAndFlush(rowMaterial);


    }

    @DisplayName("test for get all product")
    @Test
    public void allProdutTest() {
        //GIVEN
        when(rowMaterialDao.findAll()).thenReturn(rowMaterialList);
        //WHEN
        List<RowMaterialDto> results = rowMaterialService.findAllRowMaterials();
        //THEN
        assertNotNull(results);
        verify(rowMaterialDao, times(1)).findAll();
    }

    @DisplayName("test for delete by id")
    @Test
    public void testDeleteProdut() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(rowMaterialDao.findOne(anyLong())).thenReturn(rowMaterial);
        assertDoesNotThrow(
                () -> {
                    rowMaterialService.delete(rowMaterial.getId());
                }
        );
        verify(rowMaterialDao, times(1)).findOne(rowMaterial.getId());
    }


    @DisplayName("test for get category by id")
    @Test
    public void testGetProductById() {
        when(rowMaterialDao.findOne(anyLong())).thenReturn(rowMaterial);
        RowMaterialDto rowMaterialDtoResult = rowMaterialService.findById(rowMaterial.getId());
        assertEquals(rowMaterialDtoResult, rowMaterialDto);
        assertDoesNotThrow(() -> rowMaterialService.findById(rowMaterial.getId()));
        verify(rowMaterialDao, times(2)).findOne(rowMaterial.getId());
    }

}