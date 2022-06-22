package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.SalesOrder;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ReceivedDto implements Serializable {
    private Long id;
    private LocalDateTime date;
    private String method;
    private String type;
    private Long salesOrderId;

}
