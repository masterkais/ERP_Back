package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.*;
import fr.byteCode.erp.persistance.dto.TransfertLineDetailDto;
import fr.byteCode.erp.persistance.dto.TransfertLineDto;
import fr.byteCode.erp.persistance.entities.*;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.TransfertLineConverter;
import fr.byteCode.erp.service.services.InterfaceService.ITransfertLineService;
import fr.byteCode.erp.service.services.InterfaceService.IUserService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;
import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_UPDATED;

@Service
@Slf4j
public class TransfertLineService extends GenericService<TransfertLine, Long> implements ITransfertLineService {

    private RequestTransfertDao requestTransfertDao;
    private TransfertLineDao transfertLineDao;
    private ProductDao productDao;
    private IUserService userService;
    private SiteStockDao siteStockDao;
    private VehiculeDao vehiculeDao;
    private UserDao userDao;
    private ExitVoucherDao exitVoucherDao;
    private CategoryDao categoryDao;
    private TransfertLineDetailDao transfertLineDetailDao;

    private TransfertLineService(CategoryDao categoryDao, TransfertLineDetailDao transfertLineDetailDao, ExitVoucherDao exitVoucherDao, UserDao userDao, VehiculeDao vehiculeDao, RequestTransfertDao requestTransfertDao, TransfertLineDao transfertLineDao, ProductDao productDao, IUserService userService, SiteStockDao siteStockDao) {
        this.requestTransfertDao = requestTransfertDao;
        this.transfertLineDao = transfertLineDao;
        this.productDao = productDao;
        this.userService = userService;
        this.siteStockDao = siteStockDao;
        this.vehiculeDao = vehiculeDao;
        this.userDao = userDao;
        this.exitVoucherDao = exitVoucherDao;
        this.transfertLineDetailDao = transfertLineDetailDao;
        this.categoryDao = categoryDao;
    }

    @Override
    public TransfertLineDto save(TransfertLineDto transfertLineDto) {
        Objects.requireNonNull(transfertLineDto);
        TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(TransfertLineConverter.dtoToModel(transfertLineDto));
        log.info(LOG_ENTITY_CREATED, transfertLineSaved);
        return TransfertLineConverter.modelToDto(transfertLineSaved);

    }

    @Override
    public TransfertLineDto update(TransfertLineDto transfertLineDto) {
        Objects.requireNonNull(transfertLineDto);
        TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(TransfertLineConverter.dtoToModel(transfertLineDto));
        log.info(LOG_ENTITY_CREATED, transfertLineSaved);
        return TransfertLineConverter.modelToDto(transfertLineSaved);
    }

    @Override
    public TransfertLineDto findById(Long id) {
        return TransfertLineConverter.modelToDto(Optional.ofNullable(transfertLineDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));

    }

    @Override
    public List<TransfertLineDto> findAllTransfertLines() {
        List<TransfertLine> transfertLines = transfertLineDao.findAll();
        List<TransfertLineDto> transfertLineDtos = new ArrayList<>();
        transfertLines.forEach(transfertLine -> {
            transfertLineDtos.add(TransfertLineConverter.modelToDto(transfertLine));
        });
        return transfertLineDtos;
    }
    @Override
    public List<TransfertLineDto> findAllTransfertLinesByRequestId(Long idRequest) {
        List<TransfertLine> transfertLines = transfertLineDao.getAllTransfertLineByIdRequestId(idRequest);
        List<TransfertLineDto> transfertLineDtos = new ArrayList<>();
        transfertLines.forEach(transfertLine -> {
            transfertLineDtos.add(TransfertLineConverter.modelToDto(transfertLine));
        });
        return transfertLineDtos;
    }

    @Override
    public List<TransfertLineDto> acceptLineTransfert(Long idTransfertLine, List<Long> idProducts) throws Exception {

        TransfertLine transfertLine = transfertLineDao.findOne(idTransfertLine);
        int quantite=transfertLine.getQuantity();
        RequestTransfert requestTransfert = transfertLine.getRequestTransfert();
        Objects.requireNonNull(transfertLine);
        List<TransfertLineDto> transfertLineDtos = new ArrayList<>();
        requestTransfert.setState(2);
        transfertLine.setState(2);

        if (transfertLine.getQuantity() == 1) {
            Product product = productDao.findOne(idProducts.get(0));
            checkIfProductBelongInSiteSource(product, requestTransfert);
            TransfertLineDetail transfertLineDetail = new TransfertLineDetail();
            transfertLineDetail.setProduct(product);
            transfertLineDetail.setTransfertLine(transfertLine);
            transfertLineDetailDao.saveAndFlush(transfertLineDetail);
            TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(transfertLine);
            log.info(LOG_ENTITY_UPDATED, transfertLineSaved);
            TransfertLineDto transfertLineDto = TransfertLineConverter.modelToDto(transfertLineSaved);
            transfertLineDtos.add(transfertLineDto);
            product.setActive(false);
            productDao.saveAndFlush(product);
            return transfertLineDtos;
        } else {
            int quantityProduct = transfertLine.getQuantity();
            if (idProducts.size() == 1) {
                transfertLine.setRequestTransfert(requestTransfert);
                transfertLine.setCategory(transfertLine.getCategory());
                TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(transfertLine);
                log.info(LOG_ENTITY_CREATED, transfertLineSaved);
                TransfertLineDto transfertLineDto = TransfertLineConverter.modelToDto(transfertLineSaved);
                transfertLineDtos.add(transfertLineDto);
                TransfertLineDetail transfertLineDetail = new TransfertLineDetail();
                transfertLineDetail.setTransfertLine(transfertLine);
                Product product = productDao.findOne(idProducts.get(0));
                product.setActive(false);
                productDao.saveAndFlush(product);
                transfertLineDetail.setProduct(product);
                transfertLineDetailDao.saveAndFlush(transfertLineDetail);
                transfertLine.setQuantity(idProducts.size());
                transfertLineDao.saveAndFlush(transfertLine);
            } else {
                for (int i = 0; i < idProducts.size(); i++) {
                    transfertLine.setRequestTransfert(requestTransfert);
                    transfertLine.setCategory(transfertLine.getCategory());
                    transfertLineDao.saveAndFlush(transfertLine);
                    TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(transfertLine);
                    log.info(LOG_ENTITY_CREATED, transfertLineSaved);
                    TransfertLineDto transfertLineDto = TransfertLineConverter.modelToDto(transfertLineSaved);
                    transfertLineDtos.add(transfertLineDto);
                    TransfertLineDetail transfertLineDetail = new TransfertLineDetail();
                    transfertLineDetail.setTransfertLine(transfertLine);
                    Product product = productDao.findOne(idProducts.get(i));
                    product.setActive(false);
                    productDao.saveAndFlush(product);
                    transfertLineDetail.setProduct(product);
                    transfertLineDetailDao.saveAndFlush(transfertLineDetail);
                    transfertLine.setQuantity(idProducts.size());
                    transfertLineDao.saveAndFlush(transfertLine);
                }
            }

            if (transfertLine.getQuantity() != productDao.getNumberOfLineDemandeDetailByLineDemandeById(transfertLine.getId())) {
                TransfertLine transfertLineSupp = new TransfertLine();
                transfertLineSupp.setRequestTransfert(requestTransfert);
                transfertLineSupp.setState(1);
                transfertLineSupp.setDeleted(false);
                transfertLineSupp.setQuantity(quantite-transfertLine.getQuantity());
                transfertLineSupp.setCategory(transfertLine.getCategory());
                transfertLineSupp.setDeletedToken(null);
                transfertLineDao.save(transfertLineSupp);
                log.info(LOG_ENTITY_CREATED, transfertLineSupp);
            }
            requestTransfertDao.saveAndFlush(requestTransfert);
            return transfertLineDtos;
        }
    }

    private void checkValidityQuantityOfLineTransfertAndProduct(int quantity, List<Long> productIds) throws Exception {
        if (quantity != productIds.size()) {
            throw new Exception("quantity and number of product not valid");
        }
    }

    private void checkIfProductBelongInSiteSource(Product product, RequestTransfert requestTransfert) throws Exception {
        if (product.getSiteStock().equals(requestTransfert.getSiteDestinaion())) {
            throw new Exception("product exist in site stock destination ");
        }
    }

    @Override
    public TransfertLineDto rejectLineTransfert(Long idTransfertLine) {
        TransfertLine transfertLine = transfertLineDao.findOne(idTransfertLine);
        Objects.requireNonNull(transfertLine);
        transfertLine.setState(6);
        UUID uuid = UUID.randomUUID();
        transfertLine.setDeleted(true);
        transfertLine.setDeletedToken(uuid);
        TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(transfertLine);
        log.info(LOG_ENTITY_UPDATED, transfertLineSaved);
        return TransfertLineConverter.modelToDto(transfertLineSaved);
    }
    @Override
    public TransfertLineDto addLineToRequestTransfert(TransfertLineDto transfertLineDto) throws Exception {
        Objects.requireNonNull(transfertLineDto);
        RequestTransfert requestTransfert = requestTransfertDao.findOne(transfertLineDto.getRequestTransfertId());
        boolean existSiteDestination = checkSiteDestination(requestTransfert.getSiteDestinaion());
        //checkProductStockQuantityIsDisponible(transfertLineDto);
        if (existSiteDestination == true) {
            if (requestTransfert != null) {
                List<TransfertLine> transfertLines = requestTransfert.getTransfertLines();
                TransfertLine transfertLine = TransfertLineConverter.dtoToModel(transfertLineDto);
                Category category = getCategoryById(transfertLineDto.getCategoryid());
                //checkProductQuantityInLineRequestTransfert(transfertLineDto, category);
                transfertLine.setCategory(category);
                transfertLine.setRequestTransfert(requestTransfert);
                TransfertLine transfertLineSaved = transfertLineDao.save(transfertLine);
                transfertLines.add(transfertLineSaved);
                requestTransfert.setTransfertLines(transfertLines);
                RequestTransfert requestTransfertSaved = requestTransfertDao.saveAndFlush(requestTransfert);
                log.info(LOG_ENTITY_CREATED, transfertLineSaved);
                log.info(LOG_ENTITY_UPDATED, requestTransfertSaved);
                return TransfertLineConverter.modelToDto(transfertLineSaved);
            } else {
                System.out.println("request transfert n'est pas disponible");
                return null;
            }
        } else {
            System.out.println("site destination not exist");
            return null;
        }
    }

    private void checkProductStockQuantityIsDisponible(TransfertLineDto transfertLineDto) throws Exception {
        RequestTransfert requestTransfert = requestTransfertDao.findOne(transfertLineDto.getRequestTransfertId());
        Category category = getCategoryById(transfertLineDto.getCategoryid());
        SiteStock siteStockSource = siteStockDao.findSiteStockByName(requestTransfert.getSiteSource());
        int quantityOfProductByCategorieAndSiteStock = productDao.getQuantityOfProductByCategoryIdAndSiteStockId(category.getId(), siteStockSource.getId());
        System.out.println("quantityOfProductByCategorieAndSiteStock=" + quantityOfProductByCategorieAndSiteStock);
        int quantity = 0;
        List<TransfertLine> transfertLines = requestTransfert.getTransfertLines();
        for (TransfertLine transfertLine : transfertLines) {
            if (transfertLine.getCategory().getId() == category.getId()) {
                quantity += transfertLine.getQuantity();
            }
        }
        System.out.println("quantity=" + quantity);
        int quantityTarget = quantity + transfertLineDto.getQuantity();
        System.out.println("quantityTarget=" + quantityTarget);
        if (quantityTarget > quantityOfProductByCategorieAndSiteStock) {
            throw new Exception("quantity of product " + category.getId() + " in this line transfert not available");
        } else {
            System.out.println("quantity est disponible");
        }
    }

    private Category getCategoryById(Long categoryid) throws Exception {
        Category category = categoryDao.findOne(categoryid);
        return Optional.ofNullable(category).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(categoryid)));

    }

    private void checkProductQuantityInLineRequestTransfert(TransfertLineDto transfertLineDto, Category category) throws Exception {
        RequestTransfert requestTransfert = requestTransfertDao.findOne(transfertLineDto.getRequestTransfertId());
        SiteStock siteStockSource = siteStockDao.findSiteStockByName(requestTransfert.getSiteSource());
        int quantityOfProductByCategorieAndSiteStock = productDao.getQuantityOfProductByCategoryIdAndSiteStockId(category.getId(), siteStockSource.getId());
        if (transfertLineDto.getQuantity() > quantityOfProductByCategorieAndSiteStock) {
            System.out.println("product quantitiy in line transfert must be grater than product quantity stock");
            throw new Exception();
        }
    }

    private boolean checkSiteDestination(String siteDestinaion) {
        if (siteStockDao.findSiteStockByName(siteDestinaion) == null) {
            return false;
        } else return true;
    }
    @Override
    public List<TransfertLineDetail> getAllTransfertLineDetailByIDTranfertLine(Long idLineTransfert) {
        List<TransfertLineDetail> transfertLinesDetail = transfertLineDetailDao.getAllTransfertLineDetailByIDTranfertLine(idLineTransfert);
        return transfertLinesDetail;
    }
    @Override
    public List<TransfertLineDetailDto> getTransfertLineDetailByIDTranfertLine(Long idLineTransfert) {
        List<TransfertLineDetail> transfertLinesDetail = transfertLineDetailDao.getTransfertLineDetailByIDTranfertLine(idLineTransfert);
        List<TransfertLineDetailDto> transfertLineDetailDtos=new ArrayList<>();
        transfertLinesDetail.forEach((data)->{
            TransfertLineDetailDto transfertLineDetailDto=new TransfertLineDetailDto();
            transfertLineDetailDto.setId(data.getId());
            transfertLineDetailDto.setProduct(data.getProduct().getId());
            transfertLineDetailDtos.add(transfertLineDetailDto);
        });
        return transfertLineDetailDtos;
    }
    @Override
    public List<TransfertLineDetail> getTransfertLineDetailByIDTranfertLineV2(Long idLineTransfert) {
        List<TransfertLineDetail> transfertLinesDetail = transfertLineDetailDao.getTransfertLineDetailByIDTranfertLine(idLineTransfert);
        return transfertLinesDetail;
    }
    @Override
    public List<TransfertLineDto> acceptLineTransfertEntry(Long idTransfertLine, List<Long> idProducts) throws Exception {
        TransfertLine transfertLine = transfertLineDao.findOne(idTransfertLine);
        int quantite=transfertLine.getQuantity();
        RequestTransfert requestTransfert = transfertLine.getRequestTransfert();
        SiteStock siteStock=siteStockDao.findSiteStockByName(requestTransfert.getSiteDestinaion());
        Objects.requireNonNull(transfertLine);
        List<TransfertLineDto> transfertLineDtos = new ArrayList<>();
        requestTransfert.setState(4);
        transfertLine.setState(4);
        List<User> deliveryManList = requestTransfert.getDeliveryManList();
        if(deliveryManList!=null){
        deliveryManList.forEach(deliveryMan -> {
            deliveryMan.setActive(false);
            Vehicule vehicule = vehiculeDao.getVehiculeByDeliveryId(deliveryMan.getId());
            if(vehicule!=null) {
                vehicule.setState(false);
                vehicule.setDeliveryMan(null);
                vehiculeDao.saveAndFlush(vehicule);
            }
            userDao.saveAndFlush(deliveryMan);
        });}
        requestTransfert.setDateAccpted(LocalDateTime.now());
        if (transfertLine.getQuantity() == 1) {
            Product product = productDao.findOne(idProducts.get(0));
            checkIfProductBelongInSiteSource(product, requestTransfert);
            TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(transfertLine);
            log.info(LOG_ENTITY_UPDATED, transfertLineSaved);
            TransfertLineDto transfertLineDto = TransfertLineConverter.modelToDto(transfertLineSaved);
            transfertLineDtos.add(transfertLineDto);
            product.setActive(true);
            product.setSiteStock(siteStock);
            productDao.saveAndFlush(product);
            return transfertLineDtos;
        } else {
            int quantityProduct = transfertLine.getQuantity();
            if (idProducts.size() == 1) {
                transfertLine.setRequestTransfert(requestTransfert);
                transfertLine.setCategory(transfertLine.getCategory());
                TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(transfertLine);
                log.info(LOG_ENTITY_CREATED, transfertLineSaved);
                TransfertLineDto transfertLineDto = TransfertLineConverter.modelToDto(transfertLineSaved);
                transfertLineDtos.add(transfertLineDto);
                Product product = productDao.findOne(idProducts.get(0));
                product.setActive(true);
                product.setSiteStock(siteStock);
                productDao.saveAndFlush(product);
                transfertLine.setQuantity(idProducts.size());
                transfertLineDao.saveAndFlush(transfertLine);
            } else {
                for (int i = 0; i < idProducts.size(); i++) {
                    transfertLine.setRequestTransfert(requestTransfert);
                    transfertLine.setCategory(transfertLine.getCategory());
                    transfertLineDao.saveAndFlush(transfertLine);
                    TransfertLine transfertLineSaved = transfertLineDao.saveAndFlush(transfertLine);
                    log.info(LOG_ENTITY_CREATED, transfertLineSaved);
                    TransfertLineDto transfertLineDto = TransfertLineConverter.modelToDto(transfertLineSaved);
                    transfertLineDtos.add(transfertLineDto);
                    Product product = productDao.findOne(idProducts.get(i));
                    product.setActive(true);
                    product.setSiteStock(siteStock);
                    productDao.saveAndFlush(product);
                    transfertLine.setQuantity(idProducts.size());
                    transfertLineDao.saveAndFlush(transfertLine);
                }
            }

            if (transfertLine.getQuantity() != productDao.getNumberOfLineDemandeDetailByLineDemandeById(transfertLine.getId())) {
                TransfertLine transfertLineSupp = new TransfertLine();
                transfertLineSupp.setRequestTransfert(requestTransfert);
                transfertLineSupp.setState(5);
                transfertLineSupp.setDeleted(false);
                transfertLineSupp.setQuantity(quantite-transfertLine.getQuantity());
                transfertLineSupp.setCategory(transfertLine.getCategory());
                transfertLineSupp.setDeletedToken(null);
                transfertLineDao.save(transfertLineSupp);
                log.info(LOG_ENTITY_CREATED, transfertLineSupp);
            }
            requestTransfertDao.saveAndFlush(requestTransfert);
            return transfertLineDtos;
        }
    }

}
