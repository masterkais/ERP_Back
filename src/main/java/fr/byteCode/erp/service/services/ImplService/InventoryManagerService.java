/*
package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.UserDao;
import fr.byteCode.erp.persistance.dto.InventoryManagerDto;
import fr.byteCode.erp.persistance.entities.InventoryManager;
import fr.byteCode.erp.persistance.entities.User;
import fr.byteCode.erp.service.convertor.InventoryManagerConverter;
import fr.byteCode.erp.service.services.InterfaceService.IInventoryManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;

@Service
@Slf4j
public class InventoryManagerService extends GenericService<User, Long> implements IInventoryManagerService {
    private UserDao userDao;
    private InventoryManagerDao inventoryManagerDao;

    @Autowired
    private InventoryManagerService(UserDao userDao, InventoryManagerDao inventoryManagerDao) {
        this.userDao = userDao;
        this.inventoryManagerDao = inventoryManagerDao;
    }


    @Override
    public InventoryManagerDto save(InventoryManagerDto inventoryManagerDto) {
        Objects.requireNonNull(inventoryManagerDto);
        InventoryManager savedUser = inventoryManagerDao.saveAndFlush(InventoryManagerConverter.dtoToModel(inventoryManagerDto));
        log.info(LOG_ENTITY_CREATED, savedUser);
        return InventoryManagerConverter.modelToDto(savedUser);
    }

    @Override
    public InventoryManagerDto update(InventoryManagerDto inventoryManagerDto) {
        return null;
    }

    @Override
    public InventoryManagerDto findById(Long id) {
        return null;
    }

    @Override
    public List<InventoryManagerDto> findAllInventoryManagers() {
        return null;
    }
}
*/
