package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.*;
import fr.byteCode.erp.persistance.dto.LigneSaleDto;
import fr.byteCode.erp.persistance.entities.*;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.LigneSaleConverter;
import fr.byteCode.erp.service.services.InterfaceService.ILigneSalesService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.*;

@Service
@Slf4j
public class LigneSalesService extends GenericService<LigneSale, Long> implements ILigneSalesService {
    private LigneSaleDao ligneSaleDao;
    private UserDao userDao;
    private ProductDao productDao;
    private SalesOrderDao salesOrderDao;
    private CategoryDao categoryDao;
    private SiteStockDao siteStockDao;
    private OrderLineDetailDao orderLineDetailDao;

    @Autowired
    private LigneSalesService(OrderLineDetailDao orderLineDetailDao,SiteStockDao siteStockDao,LigneSaleDao ligneSaleDao, ProductDao productDao, UserDao userDao, SalesOrderDao salesOrderDao, CategoryDao categoryDao) {
        this.ligneSaleDao = ligneSaleDao;
        this.productDao = productDao;
        this.userDao = userDao;
        this.salesOrderDao = salesOrderDao;
        this.categoryDao = categoryDao;
        this.siteStockDao=siteStockDao;
        this.orderLineDetailDao=orderLineDetailDao;

    }

    @Override
    public LigneSaleDto save(LigneSaleDto ligneSaleDto) {
        Objects.requireNonNull(ligneSaleDto);
        LigneSale ligneSaleSaved = ligneSaleDao.saveAndFlush(LigneSaleConverter.dtoToModel(ligneSaleDto));
        log.info(LOG_ENTITY_CREATED, ligneSaleSaved);
        return LigneSaleConverter.modelToDto(ligneSaleSaved);
    }

    @Override
    public LigneSaleDto update(LigneSaleDto ligneSaleDto) {
        Objects.requireNonNull(ligneSaleDto);
        if (this.findById(ligneSaleDto.getId()) != null) {
            LigneSale ligneSaleSaved = ligneSaleDao.saveAndFlush(LigneSaleConverter.dtoToModel(ligneSaleDto));
            log.info(LOG_ENTITY_CREATED, ligneSaleSaved);
            return LigneSaleConverter.modelToDto(ligneSaleSaved);
        } else
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(ligneSaleDto.getId()));
    }

    @Override
    public LigneSaleDto findById(Long id) {
        return LigneSaleConverter.modelToDto(Optional.ofNullable(ligneSaleDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<LigneSaleDto> findAllLigneSale() {
        List<LigneSale> ligneSaleList = ligneSaleDao.findAll();
        List<LigneSaleDto> ligneSaleDtos = new ArrayList<>();
        ligneSaleList.forEach(ligneSale -> {
            ligneSaleDtos.add(LigneSaleConverter.modelToDto(ligneSale));
        });
        return ligneSaleDtos;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            ligneSaleDao.delete(id, uuid);
        }
    }

    @Override
    public List<LigneSaleDto> acceptLineSale(Long idLigneSale, List<Long> idProducts) throws Exception {
        LigneSale ligneSale = ligneSaleDao.findOne(idLigneSale);
        SalesOrder salesOrder= ligneSale.getSalesOrder();
        Objects.requireNonNull(ligneSale);
        List<LigneSaleDto>  ligneSaleDtos= new ArrayList<>();
        salesOrder.setState(2);
        ligneSale.setState(2);
        checkValidityQuantityOfLineTransfertAndProduct(ligneSale.getQuantity(), idProducts);
        if (ligneSale.getQuantity() == 1) {
            Product product = productDao.findOne(idProducts.get(0));
            checkIfProductBelongInSiteSource(product,salesOrder);
            OrderSaleLineDetail orderSaleLineDetail = new OrderSaleLineDetail();
           orderSaleLineDetail.setProduct(product);
            orderSaleLineDetail.setLigneSale(ligneSale);
            orderLineDetailDao.saveAndFlush(orderSaleLineDetail);
            salesOrderDao.saveAndFlush(salesOrder);
            product.setSiteStock(null);
            product.setDeleted(true);
            product.setDeletedToken(UUID.randomUUID());
            LigneSale ligneSaleSaved = ligneSaleDao.saveAndFlush(ligneSale);
            log.info(LOG_ENTITY_CREATED, ligneSaleSaved);
            LigneSaleDto ligneSaleDto = LigneSaleConverter.modelToDto(ligneSaleSaved);
            ligneSaleDtos.add(ligneSaleDto);
            return ligneSaleDtos;
        } else {
            int quantityProduct = ligneSale.getQuantity();
            for (int i = 0; i < quantityProduct - 1; i++) {
                LigneSale ligneSaleNew = new LigneSale();
                ligneSaleNew.setSalesOrder(salesOrder);
                ligneSaleNew.setCategory(ligneSale.getCategory());
                ligneSaleNew.setState(2);
                ligneSaleDao.saveAndFlush(ligneSaleNew);
                OrderSaleLineDetail orderSaleLineDetail = new OrderSaleLineDetail();
                Product product=productDao.findOne(idProducts.get(i+1));
                product.setSiteStock(null);
                product.setDeleted(true);
                product.setDeletedToken(UUID.randomUUID());
                productDao.saveAndFlush(product);
                orderSaleLineDetail.setProduct(product);
                orderSaleLineDetail.setLigneSale(ligneSale);
                orderLineDetailDao.saveAndFlush(orderSaleLineDetail);
                LigneSale lineSalesSaved = ligneSaleDao.saveAndFlush(ligneSaleNew);
                log.info(LOG_ENTITY_CREATED, lineSalesSaved);
                LigneSaleDto ligneSaleDtoSaved= LigneSaleConverter.modelToDto(lineSalesSaved);
                ligneSaleDtos.add(ligneSaleDtoSaved);
            }
            ligneSale.setState(2);
            ligneSale.setQuantity(0);
            ligneSaleDao.saveAndFlush(ligneSale);
            OrderSaleLineDetail orderSaleLineDetail=new OrderSaleLineDetail();
            orderSaleLineDetail.setLigneSale(ligneSale);
            Product product=productDao.findOne(idProducts.get(0));
            orderSaleLineDetail.setProduct(product);
            orderLineDetailDao.saveAndFlush(orderSaleLineDetail);
            return ligneSaleDtos;
        }
    }
    private void checkIfProductBelongInSiteSource(Product product, SalesOrder salesOrder) throws Exception {
        SiteStock siteStock=siteStockDao.findSiteStockByName(salesOrder.getSiteStockSale());
        if (!product.getSiteStock().equals(siteStock) ){
            throw new Exception("product exist in site stock destination ");
        }
    }
    @Override
    public LigneSaleDto rejectLineOrderSalesc(Long idLineOrderSales) {
        LigneSale ligneSale = ligneSaleDao.findOne(idLineOrderSales);
        Objects.requireNonNull(ligneSale);
        ligneSale.setState(4);
        UUID uuid = UUID.randomUUID();
        ligneSale.setDeleted(true);
        ligneSale.setDeletedToken(uuid);
        LigneSale lineSalesSaved = ligneSaleDao.saveAndFlush(ligneSale);
        log.info(LOG_ENTITY_UPDATED, lineSalesSaved);
        return LigneSaleConverter.modelToDto(lineSalesSaved);
    }

    @Override
    public LigneSaleDto addLineToSalesOrder(LigneSaleDto ligneSaleDto) throws Exception {
        Objects.requireNonNull(ligneSaleDto);
        SalesOrder salesOrder = salesOrderDao.findOne(ligneSaleDto.getSalesOrderId());
        checkProductStockQuantityIsDisponible(ligneSaleDto);
        if (salesOrder != null) {
            List<LigneSale> ligneSales = salesOrder.getLigneSales();
            LigneSale ligneSale = LigneSaleConverter.dtoToModel(ligneSaleDto);
            Category category = getCategoryById(ligneSaleDto.getCategoryId());
            ligneSale.setCategory(category);
            ligneSale.setSalesOrder(salesOrder);
            LigneSale ligneSaleSaved = ligneSaleDao.saveAndFlush(ligneSale);
            ligneSales.add(ligneSaleSaved);
            salesOrder.setLigneSales(ligneSales);
            salesOrder.setTotalSale(salesOrder.getTotalSale()+ligneSale.getCategory().getProductList().get(0).getSellingPrice());
            SalesOrder salesOrderSaved = salesOrderDao.saveAndFlush(salesOrder);
            log.info(LOG_ENTITY_CREATED, ligneSaleSaved);
            log.info(LOG_ENTITY_UPDATED, salesOrderSaved);
            return LigneSaleConverter.modelToDto(ligneSaleSaved);
        } else {
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(salesOrder));
        }
    }

    private void checkProductQuantityInLineOrderSales(LigneSaleDto ligneSaleDto, Category category) {
        SalesOrder salesOrder = salesOrderDao.findOne(ligneSaleDto.getSalesOrderId());
        SiteStock siteStockSales = siteStockDao.findSiteStockByName(salesOrder.getSiteStockSale());
        int quantityOfProductByCategorieAndSiteStock = productDao.getQuantityOfProductByCategoryIdAndSiteStockId(category.getId(), siteStockSales.getId());
        if (ligneSaleDto.getQuantity() > quantityOfProductByCategorieAndSiteStock) {
            throw new HttpCustomException(ApiErrors.PRODUCT_QUANTITY_IN_SALEORDERLINE_MUST_BE_GRATER_THAN_PRODUCT_QUANTITY_STOCK, new ErrorsResponse().error(ligneSaleDto.getQuantity()));
        }
    }

    private void checkProductStockQuantityIsDisponible(LigneSaleDto ligneSaleDto) throws Exception {
        SalesOrder salesOrder = salesOrderDao.findOne(ligneSaleDto.getSalesOrderId());
        checkExistSalesOrder(salesOrder);
        Category category = getCategoryById(ligneSaleDto.getCategoryId());
        checkExistCategory(category);
        System.out.println("catid"+category.getId());
        SiteStock siteStock = siteStockDao.findSiteStockByName(salesOrder.getSiteStockSale());
        int quantity = 0;
        List<LigneSale> ligneSales = salesOrder.getLigneSales();
        for (LigneSale ligneSale : ligneSales) {
            if (ligneSale.getCategory().getId() == category.getId()) {
                quantity += ligneSale.getQuantity();
            }
        }
        int quantityTarget = quantity + ligneSaleDto.getQuantity();
        System.out.println("****quantityTarget"+quantityTarget);

    }

    private void checkExistCategory(Category category) {
        if(category==null){
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND,new ErrorsResponse().error(category.getId()));
        }
    }

    private void checkExistSalesOrder(SalesOrder salesOrder) {
        if(salesOrder==null){
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND,new ErrorsResponse().error(salesOrder.getId()));
        }
    }

    private Category getCategoryById(Long categoryid) throws Exception {
        Category category = categoryDao.findOne(categoryid);
        return Optional.ofNullable(category).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(categoryid)));

    }

    private void checkValidityQuantityOfLineTransfertAndProduct(int quantity, List<Long> productIds) throws Exception {
        if (quantity != productIds.size()) {
            throw new HttpCustomException(ApiErrors.QUANTITY_AND_NUMBER_OF_PRODUC_NOT_VALID, new ErrorsResponse().error(quantity));
        }


    }

}
