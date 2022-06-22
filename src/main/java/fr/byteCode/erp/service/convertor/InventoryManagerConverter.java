package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.InventoryManagerDto;
import fr.byteCode.erp.persistance.entities.InventoryManager;

public class InventoryManagerConverter {
    private InventoryManagerConverter(){
        super();
    }
    public static InventoryManager dtoToModel(InventoryManagerDto inventoryManagerDto){
        return null;//new InventoryManager(inventoryManagerDto.getId(), inventoryManagerDto.getLastName(), inventoryManagerDto.getFirstName(),inventoryManagerDto.getAdress(),inventoryManagerDto.getFax(),inventoryManagerDto.getEmail(),inventoryManagerDto.getCity(),inventoryManagerDto.getPicture(), inventoryManagerDto.isActive(),inventoryManagerDto.getDateNaissanced(),inventoryManagerDto.getDateCreated()
               //,inventoryManagerDto.getRequestTransferts());

    }
    public static InventoryManagerDto modelToDto(InventoryManager inventoryManager){
        return null; //new //InventoryManagerDto(inventoryManager.getId(), inventoryManager.getLastName(), inventoryManager.getFirstName(),inventoryManager.getAdress(),inventoryManager.getFax(),inventoryManager.getEmail(),inventoryManager.getCity(),inventoryManager.getPicture(), inventoryManager.isActive(),inventoryManager.getDateNaissanced(),inventoryManager.getDateCreated()
        //,inventoryManager.getGroup().getId(),inventoryManager.getRequestTransferts());
    }
}