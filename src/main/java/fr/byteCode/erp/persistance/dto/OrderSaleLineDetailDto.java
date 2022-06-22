package fr.byteCode.erp.persistance.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderSaleLineDetailDto {
    private Long id;
    private Long productId;
    private Long OrderSaleLineId;
}
