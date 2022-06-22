package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.VehiculeDto;
import fr.byteCode.erp.persistance.entities.Vehicule;

public final class VehiculeConverter {
    private VehiculeConverter() {
        super();
    }

    public static Vehicule dtoToModel(VehiculeDto vehiculeDto) {
        return new Vehicule(vehiculeDto.getId(), vehiculeDto.getLable(), vehiculeDto.getSerialNumber(), vehiculeDto.getDatePurchase(), vehiculeDto.isState());
    }

    public static VehiculeDto modelToDto(Vehicule vehicule) {
        if (vehicule.getDeliveryMan() == null) {
            return new VehiculeDto(vehicule.getId(), vehicule.getLable(), vehicule.getSerialNumber(), vehicule.getDatePurchase(), vehicule.isState(), vehicule.getId());
        } else {
            return new VehiculeDto(vehicule.getId(), vehicule.getLable(), vehicule.getSerialNumber(), vehicule.getDatePurchase(), vehicule.isState(), vehicule.getId());
        }
    }
}
