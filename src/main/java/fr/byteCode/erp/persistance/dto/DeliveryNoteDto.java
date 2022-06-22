package fr.byteCode.erp.persistance.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class DeliveryNoteDto {
    private Long id;
    private LocalDateTime dateExit;
    private Long idOrderSales;

    public DeliveryNoteDto(Long id, LocalDateTime dateExit, Long idOrderSales) {
        this.id = id;
        this.dateExit = dateExit;
        this.idOrderSales = idOrderSales;
    }
}
