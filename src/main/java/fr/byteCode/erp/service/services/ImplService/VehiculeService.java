package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.UserDao;
import fr.byteCode.erp.persistance.dao.VehiculeDao;
import fr.byteCode.erp.persistance.dto.UserDto;
import fr.byteCode.erp.persistance.dto.VehiculeDto;
import fr.byteCode.erp.persistance.entities.DeliveryMan;
import fr.byteCode.erp.persistance.entities.Vehicule;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.VehiculeConverter;
import fr.byteCode.erp.service.services.InterfaceService.IUserService;
import fr.byteCode.erp.service.services.InterfaceService.IVehiculeService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;
import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_UPDATED;

@Slf4j
@Service
public class VehiculeService extends GenericService<Vehicule, Long> implements IVehiculeService {
    private VehiculeDao vehiculeDao;
    private UserDao userDao;
    private IUserService userService;

    @Autowired
    private VehiculeService(VehiculeDao vehiculeDao, UserDao userDao, IUserService userService) {
        this.vehiculeDao = vehiculeDao;
        this.userDao = userDao;
        this.userService = userService;
    }

    @Override
    public VehiculeDto reserveVehicule(Long vehiculeId,Long deliveryManId) {
     VehiculeDto vehiculeDto=findById(vehiculeId);
        Vehicule vehicule = VehiculeConverter.dtoToModel(vehiculeDto);
        if (vehiculeDto.isState()==false) {
                UserDto userDto = userService.findById(deliveryManId);
                DeliveryMan deliveryMan = new DeliveryMan(userDto.getId(), userDto.getLastName(), userDto.getFirstName(), userDto.getAdress(), userDto.getFax(), userDto.getEmail(), userDto.getCity(), userDto.getPicture(), userDto.isActive(), userDto.getDateNaissanced(),
                        userDto.getDateCreated(), userDto.getLogin(), userDto.getPassword());
                vehicule.setDeliveryMan(deliveryMan);
                vehicule.setState(true);
                Vehicule vehiculeSaved = vehiculeDao.saveAndFlush(vehicule);
                log.info(LOG_ENTITY_CREATED, vehiculeSaved);
                return VehiculeConverter.modelToDto(vehiculeSaved);
        }else {
            System.out.println("vehicule est deja reservé");
            return null;
        }
    }


    @Override
    public VehiculeDto save(VehiculeDto vehiculeDto) {
        Objects.requireNonNull(vehiculeDto);
        Vehicule vehicule = VehiculeConverter.dtoToModel(vehiculeDto);
        vehicule.setState(false);
        vehicule.setDeliveryMan(null);
       Vehicule vehiculeSaved = vehiculeDao.saveAndFlush(vehicule);
        log.info(LOG_ENTITY_CREATED, vehiculeSaved);
        return VehiculeConverter.modelToDto(vehiculeSaved);

    }

    @Override
    public VehiculeDto update(VehiculeDto vehiculeDto) {
        Objects.requireNonNull(vehiculeDto);
        Vehicule vehicule = VehiculeConverter.dtoToModel(vehiculeDto);
        if(vehicule.isState()==true){
            if(vehicule.getDeliveryMan()!=null) {
                UserDto userDto = userService.findById(vehicule.getDeliveryMan().getId());
                DeliveryMan deliveryMan = new DeliveryMan(userDto.getId(), userDto.getLastName(), userDto.getFirstName(), userDto.getAdress(), userDto.getFax(), userDto.getEmail(), userDto.getCity(), userDto.getPicture(), userDto.isActive(), userDto.getDateNaissanced(),
                        userDto.getDateCreated(), userDto.getLogin(), userDto.getPassword());
                vehicule.setDeliveryMan(deliveryMan);
            }
        }
        Vehicule vehiculeSaved = vehiculeDao.saveAndFlush(vehicule);
        log.info(LOG_ENTITY_UPDATED, vehiculeSaved);
        return VehiculeConverter.modelToDto(vehiculeSaved);
    }

    @Override
    public VehiculeDto findById(Long id) {
        return VehiculeConverter.modelToDto(Optional.ofNullable(vehiculeDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }
    @Override
    public VehiculeDto getVehiculeByDriverId(Long id) {
        return VehiculeConverter.modelToDto(Optional.ofNullable(vehiculeDao.getVehiculeByDeliveryId(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<VehiculeDto> findAllVehicules() {
        List<Vehicule> vehicules = vehiculeDao.findAll();
        List<VehiculeDto> vehiculeDtos = new ArrayList<>();
        vehicules.forEach(vehicule -> {
            vehiculeDtos.add(VehiculeConverter.modelToDto(vehicule));
        });
        return vehiculeDtos;
    }

    @Override
    public List<VehiculeDto> getListVehiculeByState(boolean state) {
        List<Vehicule> vehicules = vehiculeDao.getListVehiculeByState(state);
        List<VehiculeDto> vehiculeDtos = new ArrayList<>();
        vehicules.forEach(vehicule -> {
            vehiculeDtos.add(VehiculeConverter.modelToDto(vehicule));
        });
        return vehiculeDtos;
    }
}
