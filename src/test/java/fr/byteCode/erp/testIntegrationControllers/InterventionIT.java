package fr.byteCode.erp.testIntegrationControllers;
import fr.byteCode.erp.persistance.constants.EntitiesContants;
import fr.byteCode.erp.persistance.dao.InterventionDao;
import fr.byteCode.erp.persistance.dto.InterventionDto;
import fr.byteCode.erp.persistance.entities.Intervention;
import fr.byteCode.erp.service.convertor.InterventionConverter;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InterventionIT extends ITConfig{
    private HttpHeaders httpHeaders;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String dateNaissance = "2000-12-31 23:59";
    static LocalDateTime daeNess = LocalDateTime.parse(dateNaissance, formatter);
    private Intervention intervention;
    private InterventionDto interventionDto;
    private static final String INTERVENTION_URL="/api/intervention/";

    @BeforeAll
    public void setUp(){

        this.httpHeaders=getHttpHeaders();
    }

    @Order(1)
    @Test
    void saveInterventionIT() {
        intervention = new Intervention(ONE,"title",daeNess,daeNess,"intervenant","rapport","status",false,null,null);
        HttpEntity entity = new HttpEntity(InterventionConverter.modelToDto(intervention), httpHeaders);
        ResponseEntity<InterventionDto> interventionDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + INTERVENTION_URL,
                HttpMethod.POST, entity,InterventionDto.class);
        assertNotNull(interventionDtoResponseEntity);
        assertEquals(HttpStatus.OK, interventionDtoResponseEntity.getStatusCode());
    }
    @Order(2)
    @Test
    void getInterventionIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<InterventionDto> interventionDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + INTERVENTION_URL + ONE,
                HttpMethod.GET, entity, InterventionDto.class);

        assertNotNull(interventionDtoResponseEntity);
        assertEquals(HttpStatus.OK, interventionDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllInterventionIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<InterventionDto>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(INTERVENTION_URL + "interventions"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<InterventionDto>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteInterventionIT() {
        HttpEntity entity = new HttpEntity(interventionDto, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + INTERVENTION_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }}