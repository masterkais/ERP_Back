package testUnitaire;


import fr.byteCode.erp.persistance.dao.SiteStockDao;
import fr.byteCode.erp.persistance.dto.SiteStockDto;
import fr.byteCode.erp.persistance.entities.SiteStock;
import fr.byteCode.erp.service.services.ImplService.SiteStockService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.mockito.Mockito.*;


public class SiteStockServiceTest {
    @InjectMocks
    private SiteStockService siteStockService;
    @Mock
    private SiteStockDao siteStockDao;
    private SiteStock siteStock;
    private SiteStockDto siteStockDto;
    private List<SiteStock> siteStockList;

    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);
        siteStockDto = new SiteStockDto(ONE, "name", "description", "adress", true);
        siteStock = new SiteStock(ONE, "name", "description", "adress", true);

        siteStockList = new ArrayList<SiteStock>();
        siteStockList.add(siteStock);
    }

    @AfterEach
    void tearDown() {
        siteStockList = null;
        siteStock = null;
        siteStockDto = null;
    }

    @Test
    @DisplayName("test for save siteStock")
    public void saveProductTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(siteStockDao.saveAndFlush(siteStock)).thenReturn(siteStock);
        SiteStockDto siteSockDtoSaved = siteStockService.save(siteStockDto);
        Assertions.assertEquals(siteSockDtoSaved, siteStockDto);
        verify(siteStockDao, times(1)).saveAndFlush(siteStock);
    }

    @Test
    @DisplayName("test for update siteStock")
    public void updateSiteStockTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(siteStockDao.findOne(anyLong())).thenReturn(siteStock);
        when(siteStockDao.saveAndFlush(siteStock)).thenReturn(siteStock);
        SiteStockDto siteStockDtoResult = siteStockService.findById(siteStock.getId());
        SiteStockDto siteStockDtoSaved = siteStockService.save(siteStockDto);
        Assertions.assertEquals(siteStockDtoResult, siteStockDto);
        Assertions.assertEquals(siteStockDtoSaved, siteStockDto);
        Assertions.assertDoesNotThrow(() -> siteStockService.findById(siteStock.getId()));
        verify(siteStockDao, times(2)).findOne(siteStock.getId());
        verify(siteStockDao, times(1)).saveAndFlush(siteStock);


    }

    @DisplayName("test for get all product by sitestock")
    @Test
    public void allSiteStockTest() {
        //GIVEN
        when(siteStockDao.findAll()).thenReturn(siteStockList);
        //WHEN
        List<SiteStockDto> results = siteStockService.findAllSiteStocks();
        //THEN
        Assertions.assertNotNull(results);
        verify(siteStockDao, times(1)).findAll();
    }

    @DisplayName("test for delete by id")
    @Test
    public void testDeleteSieStock() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(siteStockDao.findOne(anyLong())).thenReturn(siteStock);
        Assertions.assertDoesNotThrow(
                () -> {
                    siteStockService.delete(siteStock.getId());
                }
        );
        verify(siteStockDao, times(1)).findOne(siteStock.getId());
    }


    @DisplayName("test for get siteStock by id")
    @Test
    public void testGetSiteStockById() {
        when(siteStockDao.findOne(anyLong())).thenReturn(siteStock);
        SiteStockDto siteSockDtoResult = siteStockService.findById(siteStock.getId());
        Assertions.assertEquals(siteSockDtoResult, siteStockDto);
        Assertions.assertDoesNotThrow(() -> siteStockService.findById(siteStock.getId()));
        verify(siteStockDao, times(2)).findOne(siteStock.getId());
    }

}