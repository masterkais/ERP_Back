package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.RequestTransfert;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EntryVoucherDto {
    private Long id;
    private LocalDateTime dateEntry;
    private Long requestTransfertId;

    public EntryVoucherDto(Long id, LocalDateTime dateEntry, Long requestTransfertId) {
        this.id = id;
        this.dateEntry = dateEntry;
        this.requestTransfertId = requestTransfertId;
    }
}
