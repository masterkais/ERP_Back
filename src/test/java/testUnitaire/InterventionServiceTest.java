package testUnitaire;

import fr.byteCode.erp.persistance.constants.EntitiesContants;
import fr.byteCode.erp.persistance.dao.InterventionDao;
import fr.byteCode.erp.persistance.dto.InterventionDto;
import fr.byteCode.erp.persistance.entities.Intervention;
import fr.byteCode.erp.service.convertor.InterventionConverter;
import fr.byteCode.erp.service.services.ImplService.InterventionService;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class InterventionServiceTest {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String dateNaissance = "2000-12-31 23:59";
    static LocalDateTime daeNess = LocalDateTime.parse(dateNaissance, formatter);

    @InjectMocks
    private InterventionService interventionService;

    @Mock
    private InterventionDao interventionDao;
    private Intervention intervention;
    private InterventionDto interventionDto;
    private List<Intervention> interventionList;
    private List<InterventionDto> interventionDtos;

    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        MockitoAnnotations.openMocks(this);
        intervention = new Intervention(ONE,"title",daeNess,daeNess,"intervenant","rapport","status",false,null,EntitiesContants.TICKET);
        interventionDto= InterventionConverter.modelToDto(intervention);
        interventionList=new ArrayList<>();
        interventionDtos=new ArrayList<>();
        interventionList.add(intervention);
        interventionDtos.add(interventionDto);

    }
    @AfterEach
    void tearDown(){
        interventionDtos= null;
        intervention=null;
        interventionDto=null;
    }
    @Test
    @DisplayName("test for save")
    public void saveInterventionTest(){
        Mockito.when(interventionDao.saveAndFlush(intervention)).thenReturn(intervention);
        InterventionDto interventionResult=interventionService.save(interventionDto);
        assertEquals(interventionResult,interventionDto);
        Mockito.verify(interventionDao,Mockito.times(1)).saveAndFlush(intervention);
    }
    @Test
    @DisplayName("test for update")
    public void updateInterventionTest()throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        Mockito.when(interventionDao.findOne(ArgumentMatchers.anyLong())).thenReturn(intervention);
        Mockito.when(interventionDao.saveAndFlush(intervention)).thenReturn(intervention);
        InterventionDto interventionDtoResult = interventionService.findById(intervention.getId());
        InterventionDto interventionDtoSaved = interventionService.save(interventionDto);
        assertEquals(interventionDtoResult,interventionDto);
        assertEquals(interventionDtoSaved,interventionDto);
        assertDoesNotThrow(() -> interventionService.findById(intervention.getId()));
        verify(interventionDao, times(2)).findOne(intervention.getId());
        verify(interventionDao, times(1)).saveAndFlush(intervention);
    }
    @DisplayName("test for get all product")
    @Test
    public void allInterventionTest() {
        //GIVEN
        when(interventionDao.findAll()).thenReturn(interventionList);
        //WHEN
        List<InterventionDto> results = interventionService.findALL();
        //THEN
        assertNotNull(results);
        verify(interventionDao, times(1)).findAll();
    }
    @DisplayName("test for delete by id")
    @Test
    public void testDeleteIntervention() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(interventionDao.findOne(anyLong())).thenReturn(intervention);
        assertDoesNotThrow(
                () -> {
                    interventionService.delete(intervention.getId());
                }
        );
        verify(interventionDao, times(1)).findOne(intervention.getId());}

    @DisplayName("test for get intervention by id")
    @Test
    public void testGetInterventionById() {
        when(interventionDao.findOne(anyLong())).thenReturn(intervention);
        InterventionDto interventionDtoResult = interventionService.findById(intervention.getId());
        assertEquals(interventionDtoResult, interventionDto);
        assertDoesNotThrow(() -> interventionService.findById(intervention.getId()));
        verify(interventionDao, times(2)).findOne(intervention.getId());
    }

}
