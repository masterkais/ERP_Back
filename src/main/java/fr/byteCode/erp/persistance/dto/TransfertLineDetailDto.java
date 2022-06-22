package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.Product;
import fr.byteCode.erp.persistance.entities.TransfertLine;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TransfertLineDetailDto {
    private Long id;
    private Long product;
}
