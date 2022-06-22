package fr.byteCode.erp.persistance.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LigneSaleDto implements Serializable {
    private Long id;
    private int quantity;
    private Long salesOrderId;
    private Long categoryId;
    private int state;
}
