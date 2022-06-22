package fr.byteCode.erp.persistance.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import fr.byteCode.erp.persistance.entities.InventoryManager;
import fr.byteCode.erp.persistance.entities.RequestTransfert;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class InventoryManagerDto extends UserDto implements Serializable {
    private List<RequestTransfert> requestTransferts;

    public InventoryManagerDto(List<RequestTransfert> requestTransferts) {
        this.requestTransferts = requestTransferts;
    }


}
