package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.RequestTransfert;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ExitVoucherDto {
    private Long id;
    private LocalDateTime dateExit;
    private Long requestTransfertId;

    public ExitVoucherDto(Long id, LocalDateTime dateExit, Long requestTransfertId) {
        this.id = id;
        this.dateExit = dateExit;
        this.requestTransfertId = requestTransfertId;
    }
    public ExitVoucherDto(Long id, LocalDateTime dateExit) {
        this.id = id;
        this.dateExit = dateExit;
    }
}
