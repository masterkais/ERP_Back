package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.EntryVoucherDao;
import fr.byteCode.erp.persistance.dao.RequestTransfertDao;
import fr.byteCode.erp.persistance.dao.UserDao;
import fr.byteCode.erp.persistance.dao.VehiculeDao;
import fr.byteCode.erp.persistance.dto.EntryVoucherDto;
import fr.byteCode.erp.persistance.entities.EntryVoucher;
import fr.byteCode.erp.persistance.entities.RequestTransfert;
import fr.byteCode.erp.persistance.entities.User;
import fr.byteCode.erp.persistance.entities.Vehicule;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.EntryVoucherConverter;
import fr.byteCode.erp.service.services.InterfaceService.IEntryVoucherService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class EntryVoucherService extends GenericService<EntryVoucher, Long> implements IEntryVoucherService {
    private EntryVoucherDao entryVoucherDao;
    private RequestTransfertDao requestTransfertDao;
    private UserDao userDao;
    private VehiculeDao vehiculeDao;

    @Autowired
    private EntryVoucherService(EntryVoucherDao entryVoucherDao,RequestTransfertDao requestTransfertDao,UserDao userDao,VehiculeDao vehiculeDao) {
        this.entryVoucherDao = entryVoucherDao;
        this.requestTransfertDao=requestTransfertDao;
        this.userDao=userDao;
        this.vehiculeDao=vehiculeDao;
    }

    @Override
    public EntryVoucherDto save(EntryVoucherDto entryVoucherDto) {
        Objects.requireNonNull(entryVoucherDto);
        EntryVoucher entryVoucherSaved = entryVoucherDao.saveAndFlush(EntryVoucherConverter.dtoToModel(entryVoucherDto));
        log.info(LOG_ENTITY_CREATED, entryVoucherSaved);
        RequestTransfert requestTransfert=requestTransfertDao.findOne(entryVoucherDto.getRequestTransfertId());
        requestTransfert.setDateAccpted(LocalDateTime.now());
        requestTransfertDao.saveAndFlush(requestTransfert);
        List<User> drivers =requestTransfert.getDeliveryManList();
        drivers.forEach((user)->{
            user.setActive(false);
            Vehicule vehicule=userDao.getVehiculeByDriver(user.getId());
            vehicule.setState(false);
            vehicule.setDeliveryMan(null);
            vehiculeDao.saveAndFlush(vehicule);
            userDao.saveAndFlush(user);
        });
        return EntryVoucherConverter.modelToDto(entryVoucherSaved);

    }

    @Override
    public EntryVoucherDto update(EntryVoucherDto entryVoucherDto) {
        Objects.requireNonNull(entryVoucherDto);
        EntryVoucher entryVoucherSaved = entryVoucherDao.saveAndFlush(EntryVoucherConverter.dtoToModel(entryVoucherDto));
        log.info(LOG_ENTITY_CREATED, entryVoucherDto);
        return EntryVoucherConverter.modelToDto(entryVoucherSaved);
    }

    @Override
    public EntryVoucherDto findById(Long id) {
        return EntryVoucherConverter.modelToDto(Optional.ofNullable(entryVoucherDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<EntryVoucherDto> findAllEntryVouchers() {
        List<EntryVoucher> entryVouchers = entryVoucherDao.findAll();
        List<EntryVoucherDto> entryVoucherDtos = new ArrayList<>();
        entryVouchers.forEach(entryVoucher -> {
            entryVoucherDtos.add(EntryVoucherConverter.modelToDto(entryVoucher));
        });
        return entryVoucherDtos;
    }
}
