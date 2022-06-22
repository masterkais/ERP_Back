package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.*;
import fr.byteCode.erp.persistance.dto.RequestTransfertDto;
import fr.byteCode.erp.persistance.dto.UserDto;
import fr.byteCode.erp.persistance.entities.*;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.RequestTransfertConverter;
import fr.byteCode.erp.service.services.InterfaceService.IRequestTransfertService;
import fr.byteCode.erp.service.services.InterfaceService.IUserService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class RequestTransfertService extends GenericService<RequestTransfert, Long> implements IRequestTransfertService {
    private RequestTransfertDao requestTransfertDao;
    private TransfertLineDao transfertLineDao;
    private ProductDao productDao;
    private IUserService userService;
    private SiteStockDao siteStockDao;
    private VehiculeDao vehiculeDao;
    private UserDao userDao;
    private ExitVoucherDao exitVoucherDao;
    private EntryVoucherDao entryVoucherDao;
    private CategoryDao categoryDao;
    private TransfertLineDetailDao transfertLineDetailDao;

    private RequestTransfertService(TransfertLineDetailDao transfertLineDetailDao, CategoryDao categoryDao, ExitVoucherDao exitVoucherDao, UserDao userDao, VehiculeDao vehiculeDao, RequestTransfertDao requestTransfertDao, TransfertLineDao transfertLineDao, ProductDao productDao, IUserService userService, SiteStockDao siteStockDao, EntryVoucherDao entryVoucherDao) {
        this.requestTransfertDao = requestTransfertDao;
        this.transfertLineDao = transfertLineDao;
        this.productDao = productDao;
        this.userService = userService;
        this.siteStockDao = siteStockDao;
        this.vehiculeDao = vehiculeDao;
        this.userDao = userDao;
        this.exitVoucherDao = exitVoucherDao;
        this.entryVoucherDao = entryVoucherDao;
        this.categoryDao = categoryDao;
        this.transfertLineDetailDao = transfertLineDetailDao;
    }

    @Override
    public RequestTransfertDto update(RequestTransfertDto requestTransfertDto) {
        Objects.requireNonNull(requestTransfertDto);
        RequestTransfert requestSaved = requestTransfertDao.saveAndFlush(RequestTransfertConverter.dtoToModel(requestTransfertDto));
        log.info(LOG_ENTITY_CREATED, requestSaved);
        return RequestTransfertConverter.modelToDto(requestSaved);

    }

    @Override
    public RequestTransfertDto findById(Long id) {
        RequestTransfert requestTransfert=requestTransfertDao.findOne(id);
        return RequestTransfertConverter.modelToDto(Optional.ofNullable(requestTransfert).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<RequestTransfertDto> findAllRequestTransferts() {
        List<RequestTransfert> requestTransferts = requestTransfertDao.findAll();
        List<RequestTransfertDto> requestTransfertDtos = new ArrayList<>();
        requestTransferts.forEach(requestTransfert -> {
            requestTransfertDtos.add(RequestTransfertConverter.modelToDto(requestTransfert));
        });
        return requestTransfertDtos;
    }
    @Override
    public List<RequestTransfertDto> findAllRequestTransfertsByIdUser(Long idUser) {
        List<RequestTransfert> requestTransferts = requestTransfertDao.getListRequestTransfert(idUser);
        List<RequestTransfertDto> requestTransfertDtos = new ArrayList<>();
        requestTransferts.forEach(requestTransfert -> {
            requestTransfertDtos.add(RequestTransfertConverter.modelToDto(requestTransfert));
        });
        return requestTransfertDtos;
    }

    @Override
    public RequestTransfertDto createNewRequestTransfertEmpty(Long inventaryManagerId, String siteDestination, String siteSource) {
        UserDto userDto = userService.findById(inventaryManagerId);
        InventoryManager inventoryManager = new InventoryManager(userDto.getId(), userDto.getLastName(), userDto.getFirstName(), userDto.getAdress(), userDto.getFax(), userDto.getEmail(), userDto.getCity(), userDto.getPicture(), userDto.isActive(), userDto.getDateNaissanced(),
                userDto.getDateCreated(), userDto.getLogin(), userDto.getPassword());
        RequestTransfert requestTransfert = new RequestTransfert();
        requestTransfert.setInventoryManager(inventoryManager);
        requestTransfert.setSiteSource(siteSource);
        requestTransfert.setSiteDestinaion(siteDestination);
        RequestTransfert requestSaved = requestTransfertDao.saveAndFlush(requestTransfert);
        log.info(LOG_ENTITY_CREATED, requestSaved);
        return RequestTransfertConverter.modelToDto(requestSaved);
    }

    @Override
    public RequestTransfertDto acceptDeliverOrder(Long requestTransfertId) {
        Objects.requireNonNull(requestTransfertId);
        RequestTransfert requestTransfert = requestTransfertDao.findOne(requestTransfertId);
        requestTransfert.setState(4);
        List<TransfertLine> transfertLines = transfertLineDao.getAllTransfertLineByIdRequestAndState(requestTransfertId, 3);
        transfertLines.forEach(transfertLine -> {
            transfertLine.setState(5);
            Category category = transfertLine.getCategory();
            List<TransfertLineDetail> transfertLineDetails = transfertLineDetailDao.getTransfertLineDetailByIDTranfertLine(transfertLine.getId());
            transfertLineDetails.forEach((transfertLineDetail)->{
                transfertLineDetail.getProduct().setSiteStock(siteStockDao.findSiteStockByName(requestTransfert.getSiteDestinaion()));
                productDao.saveAndFlush(transfertLineDetail.getProduct());
                transfertLineDetailDao.saveAndFlush(transfertLineDetail);
            });

            transfertLineDao.saveAndFlush(transfertLine);
        });
        EntryVoucher entryVoucher = new EntryVoucher(LocalDateTime.now(), requestTransfert);
        entryVoucherDao.saveAndFlush(entryVoucher);
        requestTransfert.setEntryVoucher(entryVoucher);
        List<User> deliveryManList = requestTransfert.getDeliveryManList();
        deliveryManList.forEach(deliveryMan -> {
            deliveryMan.setActive(false);
            Vehicule vehicule = vehiculeDao.getVehiculeByDeliveryId(deliveryMan.getId());
            vehicule.setState(false);
            vehicule.setDeliveryMan(null);
            vehiculeDao.saveAndFlush(vehicule);
            userDao.saveAndFlush(deliveryMan);
        });
        RequestTransfert requestTransfertSaved = requestTransfertDao.saveAndFlush(requestTransfert);
        return RequestTransfertConverter.modelToDto(requestTransfertSaved);
    }

    @Override
    public RequestTransfertDto deliverOrder(Long requestTransfertId, List<Long> delivertManIds, List<Long> vehiculeIds) throws Exception {
        RequestTransfert requestTransfert = requestTransfertDao.findOne(requestTransfertId);
        List<User> deliveryManList = getListDeliveryManByIds(delivertManIds);
        List<Vehicule> vehiculeList = getListVehiculeByIds(vehiculeIds);
        verifyVeuculeDisponibility(vehiculeList);
        verifyDelivaryManDisponibility(deliveryManList);
        assignDriverToEachVehicules(deliveryManList, vehiculeList);
        requestTransfert.setDeliveryManList(deliveryManList);
        requestTransfert.setDateCreated(LocalDateTime.now());
        List<TransfertLine> transfertLines = transfertLineDao.getAllTransfertLineByIdRequestAndState(requestTransfertId, 2);
        List<TransfertLine> transfertLinesAccepted = new ArrayList<>();
        transfertLines.forEach(transfertLine -> {
            if (transfertLine.getState() == 2) {
                transfertLinesAccepted.add(transfertLine);
                transfertLine.setState(3);
                transfertLineDao.saveAndFlush(transfertLine);
            }
        });
        requestTransfert.setTransfertLines(transfertLinesAccepted);
        requestTransfert.setState(3);
        ExitVoucher exitVoucher = new ExitVoucher(LocalDateTime.now(), requestTransfert);
        exitVoucherDao.saveAndFlush(exitVoucher);
        requestTransfert.setExitVoucher(exitVoucher);
        RequestTransfert requestSaved = requestTransfertDao.saveAndFlush(requestTransfert);
        log.info(LOG_ENTITY_CREATED, requestSaved);
        return RequestTransfertConverter.modelToDto(requestSaved);
    }


    @Override
    public RequestTransfertDto submitRequestTransfert(Long requestTransfertId) throws Exception {
        RequestTransfert requestTransfert = requestTransfertDao.findOne(requestTransfertId);
        List<TransfertLine> transfertLines = requestTransfert.getTransfertLines();
        requestTransfert.setDateCreated(LocalDateTime.now());
        requestTransfert.setState(1);
        requestTransfert.getTransfertLines().forEach(lineTransfert -> {
            lineTransfert.setState(1);
            transfertLineDao.saveAndFlush(lineTransfert);
        });
        RequestTransfert requestSaved = requestTransfertDao.saveAndFlush(requestTransfert);
        log.info(LOG_ENTITY_CREATED, requestSaved);
        return RequestTransfertConverter.modelToDto(requestSaved);
    }


    private void verifyDelivaryManDisponibility(List<User> deliveryManList) throws Exception {
        for (User deliveryMan : deliveryManList) {
            if (deliveryMan.isActive() == true) {
                throw new Exception(deliveryMan.getId() + "is not available");
            }
        }
    }

    private void verifyVeuculeDisponibility(List<Vehicule> vehiculeList) throws Exception {
        for (Vehicule vehicule : vehiculeList) {
            if (vehicule.isState() == true) {
                throw new Exception(vehicule.getId() + "is not available");
            }
        }
    }

    private List<TransfertLine> getListTransferLineByIds(List<Long> lineTransfertIds) {
        return transfertLineDao.findAllById(lineTransfertIds);
    }

    private void assignDriverToEachVehicules(List<User> deliveryManList, List<Vehicule> vehiculeList) {
        int indice = 0;
        for (Vehicule vehicule : vehiculeList) {
            vehicule.setDeliveryMan(deliveryManList.get(indice));
            vehicule.setState(true);
            vehiculeDao.saveAndFlush(vehicule);
            deliveryManList.get(indice).setActive(true);
            userDao.saveAndFlush(deliveryManList.get(indice));
            indice = indice + 1;
        }
    }

    private List<Vehicule> getListVehiculeByIds(List<Long> vehiculeIds) {
        return vehiculeDao.findAllById(vehiculeIds);
    }

    private List<User> getListDeliveryManByIds(List<Long> delivertManIds) {
        List<User> users = userDao.findAllById(delivertManIds);
        List<User> deliveryManList = new ArrayList<>();
        users.forEach(u -> {
            deliveryManList.add(new User(u.getId(), u.getLastName(), u.getFirstName(), u.getAdress(), u.getFax(), u.getEmail(), u.getCity(), u.getPicture(), u.isActive(), u.getDateNaissanced(), u.getDateCreated(), u.getUserName(), u.getPassword()));
        });
        return deliveryManList;
    }
    @Override
    public float getMantantRequest(Long requestId){
        float mantant=0;
        RequestTransfert requestTransfert=this.requestTransfertDao.findOne(requestId);
        List<TransfertLine> transfertLines=this.transfertLineDao.getAllTransfertLineByIdRequestId(requestId);
        for(int i=0;i<transfertLines.size();i++){
            if(transfertLines.get(i).getState()==3) {
                List<TransfertLineDetail> transfertLineDetails = transfertLineDetailDao.getTransfertLineDetailByIDTranfertLine(transfertLines.get(i).getId());
                for (int j = 0; j < transfertLineDetails.size(); j++) {
                    mantant += transfertLineDetails.get(j).getProduct().getSellingPrice();
                }
            }
        }
        return mantant;
    }
}
