package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.BrandDao;
import fr.byteCode.erp.persistance.dao.CategoryDao;
import fr.byteCode.erp.persistance.dto.CategoryDto;
import fr.byteCode.erp.persistance.entities.Brand;
import fr.byteCode.erp.persistance.entities.Category;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.CategoryConverter;
import fr.byteCode.erp.service.services.InterfaceService.ICategoryService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.*;

@Service
@Slf4j
public class CategoryService extends GenericService<Category, Long> implements ICategoryService {
    private CategoryDao categoryDao;
    private BrandDao brandDao;
    @Autowired
    private CategoryService(CategoryDao categoryDao,BrandDao brandDao) {
        this.brandDao=brandDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        Objects.requireNonNull(categoryDto);
        checkValidName(categoryDto.getName());
        checkUniqueName(categoryDto.getName());
        Category category=CategoryConverter.dtoToModel(categoryDto);
        Brand brand=brandDao.findOne(categoryDto.getIdBrand());
        category.setBrand(brand);
        Category savedCategory = categoryDao.saveAndFlush(category);

        log.info(LOG_ENTITY_CREATED, savedCategory);
        return CategoryConverter.ModelToDto(savedCategory);
    }

    private void checkUniqueName(String name) {
        List<Category> categorys=this.categoryDao.findAll();
        int counter=0;
       if(categorys!=null) {
           for (int i = 0; i < categorys.size(); i++) {
               if (categorys.get(i).getName().equals(name)) {
                   counter++;
               }
           }
       }
       if(counter!=1 && counter!=0){
           throw new HttpCustomException(ApiErrors.OBJECT_NAME_EXISTS, new ErrorsResponse().error(name));
       }
    }

    public void checkValidName(String name) {
        if (categoryDao.findByName(name) != null) {
            log.error(LOG_ENTITY_NAME_EXIST, name);
            throw new HttpCustomException(ApiErrors.OBJECT_NAME_EXISTS, new ErrorsResponse().error(name));
        }
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Objects.requireNonNull(categoryDto);
        checkCategoryExistByIdForUpdate(categoryDto.getId());
        checkUniqueName(categoryDto.getName());
        Category category=CategoryConverter.dtoToModel(categoryDto);
        Brand brand=brandDao.findOne(categoryDto.getIdBrand());
        category.setBrand(brand);
        Category savedCategory = categoryDao.saveAndFlush(category);
        log.info(LOG_ENTITY_UPDATED, savedCategory);
        return CategoryConverter.ModelToDto(savedCategory);
    }

    public void checkCategoryExistByIdForUpdate(Long id) {
        if (categoryDao.findOne(id) == null) {
            log.error(LOG_ENTITY_NOT_EXIST, id);
            throw new HttpCustomException(ApiErrors.OBJECT_ID_NOT_EXISTS, new ErrorsResponse().error(id));
        }
    }


    @Override
    public CategoryDto findById(Long id) {
        return CategoryConverter.ModelToDto(Optional.ofNullable(categoryDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        CategoryDto categoryDto = findById(id);
        if (findById(id) != null) {
            categoryDao.delete(categoryDto.getId(), uuid);
        }
    }

    @Override
    public List<CategoryDto> findAllCategorie() {
        List<Category> categoryList = categoryDao.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categoryList.forEach(category -> {
            CategoryDto categoryDto=new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setDescription(category.getDescription());
            categoryDto.setName(category.getName());
            categoryDto.setIdBrand(category.getBrand().getId());
            categoryDtos.add(categoryDto);
        });
        return categoryDtos;
    }
    @Override
    public List<CategoryDto> findAllCategorieDispo() {
        List<Category> categoryList = categoryDao.findAll();
        System.out.println("+++++"+categoryList.size());
        List<CategoryDto> categoryDtos = new ArrayList<>();
        categoryList.forEach(category -> {
            if(category.getProductList().size()!=0){
            CategoryDto categoryDto=new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setDescription(category.getDescription());
            categoryDto.setName(category.getName());
            categoryDto.setIdBrand(category.getBrand().getId());
            categoryDtos.add(categoryDto);}
        });
        return categoryDtos;
    }
    @Override
    public CategoryDto saveCaegory(CategoryDto categoryDto) {
        Objects.requireNonNull(categoryDto);
        checkValidName(categoryDto.getName());
        checkUniqueName(categoryDto.getName());
        Category category=new Category();
        category.setId(categoryDto.getId());
        category.setDescription(categoryDto.getDescription());
        category.setProductList(null);
        Brand brand=brandDao.findOne(categoryDto.getIdBrand());
        category.setBrand(brand);
        Category savedCategory = categoryDao.saveAndFlush(category);
        log.info(LOG_ENTITY_CREATED, savedCategory);
        return categoryDto;
    }
    @Override
    public CategoryDto updateCaegory(CategoryDto categoryDto) {
        Objects.requireNonNull(categoryDto);
        checkValidName(categoryDto.getName());
        checkUniqueName(categoryDto.getName());
        Category category=new Category();
        category.setId(categoryDto.getId());
        category.setDescription(categoryDto.getDescription());
        category.setProductList(null);
        Brand brand=brandDao.findOne(categoryDto.getIdBrand());
        category.setBrand(brand);
        Category savedCategory = categoryDao.saveAndFlush(category);
        log.info(LOG_ENTITY_CREATED, savedCategory);
        return categoryDto;
    }
}
