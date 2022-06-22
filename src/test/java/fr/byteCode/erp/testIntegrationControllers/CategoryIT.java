package fr.byteCode.erp.testIntegrationControllers;

import fr.byteCode.erp.persistance.dao.BrandDao;
import fr.byteCode.erp.persistance.dto.CategoryDto;
import fr.byteCode.erp.persistance.entities.Brand;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.service.convertor.CategoryConverter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CategoryIT extends ITConfig {

    private static final String CATEGORY_URL = "/api/category/";
    private HttpHeaders httpHeaders;
    private CategoryDto categoryDto;
    private Category category;
    @Autowired
    private BrandDao brandDao;
    private Brand brand;

    @BeforeAll
    void setUp() {
        httpHeaders = getHttpHeaders();
        brand = new Brand(1L, "Tchobiba tunis", "Tchobiba tunis");
        category = new Category(1L, "tochiba", "tochiba", brand);
    }

    @Order(1)
    @Test
    void saveIT() {
        Brand brandSaved = brandDao.saveAndFlush(brand);
        category.setBrand(brandSaved);
        CategoryDto categoryDto = CategoryConverter.ModelToDto(category);
        HttpEntity entity = new HttpEntity(categoryDto, httpHeaders);
        ResponseEntity<CategoryDto> categoryDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + CATEGORY_URL,
                HttpMethod.POST, entity, CategoryDto.class);
        assertNotNull(categoryDtoResponseEntity);
        assertEquals(HttpStatus.OK, categoryDtoResponseEntity.getStatusCode());
    }

    @Order(2)
    @Test
    void getCategoryIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<CategoryDto> categoryDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + CATEGORY_URL + ONE,
                HttpMethod.GET, entity, CategoryDto.class);

        assertNotNull(categoryDtoResponseEntity);
        assertEquals(HttpStatus.OK, categoryDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllCategorysIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<CategoryDto>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(CATEGORY_URL + "categorys"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<CategoryDto>>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteAccountIT() {
        HttpEntity entity = new HttpEntity(categoryDto, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + CATEGORY_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }


}
