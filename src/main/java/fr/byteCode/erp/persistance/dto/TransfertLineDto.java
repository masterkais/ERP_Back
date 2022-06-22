package fr.byteCode.erp.persistance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.byteCode.erp.persistance.entities.Product;
import fr.byteCode.erp.persistance.entities.RequestTransfert;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransfertLineDto {
    private Long id;
    private int quantity;
    private Long requestTransfertId;
    private Long categoryid;
    private int state;

}
