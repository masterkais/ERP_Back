package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.DeliveryMan;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VehiculeDto {
    private Long id;
    private String lable;
    private String serialNumber;
    private LocalDateTime datePurchase;
    private boolean state;
    private Long deliveryManId;

    public VehiculeDto(Long id, String lable, String serialNumber, LocalDateTime datePurchase, boolean state) {
        this.id = id;
        this.lable = lable;
        this.serialNumber = serialNumber;
        this.datePurchase = datePurchase;
        this.state = state;
    }

}
