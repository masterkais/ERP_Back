package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.*;
import fr.byteCode.erp.persistance.dto.SalesOrderDto;
import fr.byteCode.erp.persistance.dto.SiteStockDto;
import fr.byteCode.erp.persistance.entities.*;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.SalesOrderConverter;
import fr.byteCode.erp.service.convertor.SiteStockConverter;
import fr.byteCode.erp.service.services.InterfaceService.ISalesOrderService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class SalesOrderService extends GenericService<SalesOrder, Long> implements ISalesOrderService {
    private SalesOrderDao salesOrderDao;
    private UserDao userDao;
    private SiteStockDao siteStockDao;
    private LigneSaleDao ligneSaleDao;
    private VehiculeDao vehiculeDao;
    private DeliveryNoteDao deliveryNoteDao;

    @Autowired
    public SalesOrderService(DeliveryNoteDao deliveryNoteDao, VehiculeDao vehiculeDao, SalesOrderDao salesOrderDao, UserDao userDao, SiteStockDao siteStockDao, LigneSaleDao ligneSaleDao) {
        this.salesOrderDao = salesOrderDao;
        this.userDao = userDao;
        this.siteStockDao = siteStockDao;
        this.ligneSaleDao = ligneSaleDao;
        this.vehiculeDao = vehiculeDao;
        this.deliveryNoteDao = deliveryNoteDao;
    }

    @Override
    public SalesOrderDto save(SalesOrderDto salesOrderDto) {
        Objects.requireNonNull(salesOrderDto);
        SalesOrder salesOrderSaved = salesOrderDao.saveAndFlush(SalesOrderConverter.dtoToModel(salesOrderDto));
        log.info(LOG_ENTITY_CREATED, salesOrderSaved);
        return SalesOrderConverter.modelToDto(salesOrderSaved);
    }

    @Override
    public SalesOrderDto update(SalesOrderDto salesOrderDto) {
        Objects.requireNonNull(salesOrderDto);
        if (this.findById(salesOrderDto.getId()) != null) {
            SalesOrder salesOrderSaved = salesOrderDao.saveAndFlush(SalesOrderConverter.dtoToModel(salesOrderDto));
            log.info(LOG_ENTITY_CREATED, salesOrderSaved);
            return SalesOrderConverter.modelToDto(salesOrderSaved);
        } else
            throw new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(salesOrderDto.getId()));
    }

    @Override
    public SalesOrderDto findById(Long id) {
        return SalesOrderConverter.modelToDto(Optional.ofNullable(salesOrderDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<SalesOrderDto> findAllSalesOrder() {
        List<SalesOrder> salesOrderList = salesOrderDao.findAll();
        List<SalesOrderDto> salesOrderDtos = new ArrayList<>();
        salesOrderList.forEach(salesOrder -> {
            salesOrderDtos.add(SalesOrderConverter.modelToDto(salesOrder));
        });
        return salesOrderDtos;
    }

    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            salesOrderDao.delete(id, uuid);
        }
    }

    @Override
    public SalesOrderDto deliverSalesOrder(Long orderSaleId, List<Long> delivertManIds, List<Long> vehiculeIds) throws Exception {
        SalesOrder salesOrder = salesOrderDao.findOne(orderSaleId);
        List<User> deliveryManList = getListDeliveryManByIds(delivertManIds);
        List<Vehicule> vehiculeList = getListVehiculeByIds(vehiculeIds);
        verifyVeuculeDisponibility(vehiculeList);
        verifyDelivaryManDisponibility(deliveryManList);
        assignDriverToEachVehicules(deliveryManList, vehiculeList);
        salesOrder.setDeliveryManList(deliveryManList);
        salesOrder.setDateCreated(LocalDateTime.now());
        List<LigneSale> ligneSales = ligneSaleDao.getAllLigneSalesByIdOrderSalesAndState(salesOrder.getId(), 2);
        List<LigneSale> ligneSalesAccepted = new ArrayList<>();
        ligneSales.forEach(ligneSale -> {
            if (ligneSale.getState() == 2) {
                ligneSalesAccepted.add(ligneSale);
                ligneSale.setState(3);
                ligneSaleDao.saveAndFlush(ligneSale);
            }
        });
        salesOrder.setLigneSales(ligneSalesAccepted);
        salesOrder.setState(3);
        DeliveryNote deliveryNote = new DeliveryNote(LocalDateTime.now(), salesOrder);
        deliveryNoteDao.saveAndFlush(deliveryNote);
        salesOrder.setDeliveryNote(deliveryNote);
        SalesOrder salesOrderSaved = salesOrderDao.saveAndFlush(salesOrder);
        log.info(LOG_ENTITY_CREATED, salesOrderSaved);
        return SalesOrderConverter.modelToDto(salesOrderSaved);
    }

    private List<User> getListDeliveryManByIds(List<Long> delivertManIds) {
        List<User> users = userDao.findAllById(delivertManIds);
        List<User> deliveryManList = new ArrayList<>();
        users.forEach(u -> {
            deliveryManList.add(new User(u.getId(), u.getLastName(), u.getFirstName(), u.getAdress(), u.getFax(), u.getEmail(), u.getCity(), u.getPicture(), u.isActive(), u.getDateNaissanced(), u.getDateCreated(), u.getUserName(), u.getPassword()));
        });
        return deliveryManList;
    }

    private List<Vehicule> getListVehiculeByIds(List<Long> vehiculeIds) {
        return vehiculeDao.findAllById(vehiculeIds);
    }

    private void verifyVeuculeDisponibility(List<Vehicule> vehiculeList) throws Exception {
        for (Vehicule vehicule : vehiculeList) {
            if (vehicule.isState() == true) {
                throw new Exception(vehicule.getId() + "is not available");
            }
        }
    }

    private void verifyDelivaryManDisponibility(List<User> deliveryManList) throws Exception {
        for (User deliveryMan : deliveryManList) {
            if (deliveryMan.isActive() == true) {
                throw new Exception(deliveryMan.getId() + "is not available");
            }
        }
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

    @Override
    public SalesOrderDto createNewSalesOrderEmpty(Long purchasingManagerId, Long clientId) {
        User user = userDao.findOne(purchasingManagerId);
        PurchasingManager purchasingManager = new PurchasingManager(user.getId(), user.getLastName(), user.getFirstName(), user.getAdress(), user.getFax(), user.getEmail(), user.getCity(), user.getPicture(), user.isActive(), user.getDateNaissanced(),
                user.getDateCreated(), user.getUserName(), user.getPassword());
        User userClient = userDao.findOne(clientId);
        Client client = new Client(userClient.getId(), userClient.getLastName(), userClient.getFirstName(), userClient.getAdress(), userClient.getFax(), userClient.getEmail(), userClient.getCity(), userClient.getPicture(), userClient.isActive(), userClient.getDateNaissanced(),
                userClient.getDateCreated(), userClient.getUserName(), userClient.getPassword());
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setPurchasingManager(purchasingManager);
        salesOrder.setClient(client);
        salesOrder.setDateCreated(LocalDateTime.now());
        salesOrder.setSiteStockSale(null);
        SalesOrder salesOrderSaved = salesOrderDao.saveAndFlush(salesOrder);
        log.info(LOG_ENTITY_CREATED, salesOrderSaved);
        return SalesOrderConverter.modelToDto(salesOrderSaved);
    }

    private SiteStockDto findSiteStockByName(String siteStockSalesName) {
        return SiteStockConverter.modelToDto(Optional.ofNullable(siteStockDao.findSiteStockByName(siteStockSalesName)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(siteStockSalesName))));
    }

    @Override
    public SalesOrderDto submitSalesOrder(Long salesOrderId) throws Exception {
        SalesOrder salesOrder = salesOrderDao.findOne(salesOrderId);
        List<LigneSale> ligneSales = salesOrder.getLigneSales();
        salesOrder.setDateCreated(LocalDateTime.now());
        salesOrder.setState(1);
        ligneSales.forEach(ligneSale -> {
            ligneSale.setState(1);
            ligneSaleDao.saveAndFlush(ligneSale);
        });
        salesOrder.setLigneSales(ligneSales);
        SalesOrder salesOrderSaved = salesOrderDao.saveAndFlush(salesOrder);
        log.info(LOG_ENTITY_CREATED, salesOrderSaved);
        return SalesOrderConverter.modelToDto(salesOrderSaved);
    }

}
