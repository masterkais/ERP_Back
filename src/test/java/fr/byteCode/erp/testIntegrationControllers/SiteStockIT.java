package fr.byteCode.erp.testIntegrationControllers;

import fr.byteCode.erp.persistance.dto.SiteStockDto;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class SiteStockIT extends ITConfig {

    private static final String SITE_STOCK_URL = "/api/siteStock/";
    private HttpHeaders httpHeaders;
    private SiteStockDto siteStockDto;

    @BeforeAll
    void setUp() {

        httpHeaders = getHttpHeaders();
        siteStockDto = new SiteStockDto(ONE, "name", "description", "adress", true);
    }

    @Order(1)
    @Test
    void saveIT() {
        HttpEntity entity = new HttpEntity(siteStockDto, httpHeaders);
        ResponseEntity<SiteStockDto> siteStockDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + SITE_STOCK_URL,
                HttpMethod.POST, entity, SiteStockDto.class);
        assertNotNull(siteStockDtoResponseEntity);
        assertEquals(HttpStatus.OK, siteStockDtoResponseEntity.getStatusCode());
    }


    @Order(2)
    @Test
    void getSiteStockIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<SiteStockDto> siteStockDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + SITE_STOCK_URL + ONE,
                HttpMethod.GET, entity, SiteStockDto.class);

        assertNotNull(siteStockDtoResponseEntity);
        assertEquals(HttpStatus.OK, siteStockDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllSiteStocksIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<SiteStockDto>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(SITE_STOCK_URL + "siteStocks"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<SiteStockDto>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteSiteStockIT() {
        HttpEntity entity = new HttpEntity(siteStockDto, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + SITE_STOCK_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }
}