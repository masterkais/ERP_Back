package fr.byteCode.erp.testIntegrationControllers;

import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class RowMaterialIT extends ITConfig {

    private static final String ROW_MATERIAL_URL = "/api/rowMaterial/";
    private HttpHeaders httpHeaders;
    private RowMaterialDto rowMaterialDto;

    @BeforeAll
    void setUp() {

        httpHeaders = getHttpHeaders();
        rowMaterialDto = new RowMaterialDto(ONE, "name", "description", "type", "reference", true, 1);
        ;

    }

    @Order(1)
    @Test
    void saveIT() {
        HttpEntity entity = new HttpEntity(rowMaterialDto, httpHeaders);
        ResponseEntity<RowMaterialDto> rowMaterialDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + ROW_MATERIAL_URL,
                HttpMethod.POST, entity, RowMaterialDto.class);
        assertNotNull(rowMaterialDtoResponseEntity);
        assertEquals(HttpStatus.OK, rowMaterialDtoResponseEntity.getStatusCode());
    }


    @Order(2)
    @Test
    void getRowMaterialIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<RowMaterialDto> productDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + ROW_MATERIAL_URL + ONE,
                HttpMethod.GET, entity, RowMaterialDto.class);

        assertNotNull(productDtoResponseEntity);
        assertEquals(HttpStatus.OK, productDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllRowMaterialT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<RowMaterialDto>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(ROW_MATERIAL_URL + "rowMaterials"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<RowMaterialDto>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteProductIT() {
        HttpEntity entity = new HttpEntity(rowMaterialDto, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + ROW_MATERIAL_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }

}