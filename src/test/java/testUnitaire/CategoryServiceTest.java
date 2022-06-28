package testUnitaire;


import fr.byteCode.erp.persistance.dao.BaseRepository;
import fr.byteCode.erp.persistance.dao.BrandDao;
import fr.byteCode.erp.persistance.dao.CategoryDao;
import fr.byteCode.erp.persistance.dto.CategoryDto;
import fr.byteCode.erp.persistance.entities.Brand;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.service.services.ImplService.CategoryService;
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

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class CategoryServiceTest {
    @InjectMocks
    private CategoryService categoryService;
    @Mock
    private BaseRepository<Category, Long> baseRepository;
    @Mock
    private CategoryDao categoryDao;
    private Category category;
    private CategoryDto categoryDto;
    private List<Category> categoryList;
    private Brand brand;
    @Mock
    private BrandDao brandDao;
    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);
        category = new Category(ONE, "categoryName", "description");
        categoryList = new ArrayList<Category>();
        categoryList.add(category);
        brand=new Brand(1L,"Tochiba","Tochiba tv",categoryList);
        category.setBrand(brand);
        category.setProductList(null);
        categoryDto = new CategoryDto(ONE, "categoryName", "description",brand.getId());
        categoryDto.setProductListIds(null);


    }

    @AfterEach
    void tearDown() {
        category = null;
        categoryDto = null;
        categoryList = null;
    }

    @Test
    @DisplayName("test for save category")
    public void saveCategoryTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(categoryDao.findByName(category.getName())).thenReturn(null);
        when(brandDao.findOne(anyLong())).thenReturn(brand);
        when(categoryDao.findAll()).thenReturn(null);
        when(categoryDao.saveAndFlush(category)).thenReturn(category);
        CategoryDto categoryDtoResult = categoryService.saveCaegory(categoryDto);
        assertNotNull(category);
        assertEquals(categoryDtoResult, this.categoryDto);
        verify(categoryDao, times(1)).saveAndFlush(any());
    }

    @Test
    @DisplayName("test for update category")
    public void updateCategoryTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        when(categoryDao.findByName(category.getName())).thenReturn(null);
        when(brandDao.findOne(anyLong())).thenReturn(brand);
        when(categoryDao.findAll()).thenReturn(null);
        when(categoryDao.saveAndFlush(category)).thenReturn(category);
        CategoryDto categoryDtoResult = categoryService.updateCaegory(categoryDto);
        assertNotNull(category);
        assertEquals(categoryDtoResult, this.categoryDto);
        verify(categoryDao, times(1)).saveAndFlush(any());

    }

    @DisplayName("test for get all category")
    @Test
    public void allCategoryTest() {
        //GIVEN
        when(categoryDao.findAll()).thenReturn(categoryList);
        //WHEN
        List<CategoryDto> results = categoryService.findAllCategorie();
        //THEN
        assertNotNull(results);
        verify(categoryDao, times(1)).findAll();
    }

    @DisplayName("test for delete by id")
    @Test
    public void testDeleteCategory() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        assertDoesNotThrow(
                () -> {
                    categoryService.delete(category.getId());
                }
        );
        verify(categoryDao, times(2)).findOne(category.getId());
    }

    @DisplayName("test for get category by id")
    @Test
    public void testGetCategoryById() {
        when(categoryDao.findOne(anyLong())).thenReturn(category);
        assertDoesNotThrow(
                () -> {
                    categoryService.findById(category.getId());
                }
        );
        verify(categoryDao, times(1)).findOne(category.getId());
    }

}