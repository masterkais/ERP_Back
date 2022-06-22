package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.Ticket;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class InterventionDto {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String tilte;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String intervenent;
    private String rapport;
    private String Status;
    private Ticket ticket;

    public InterventionDto(Long id, String tilte, String intervenent, String rapport, String status, Ticket ticket) {
        this.id = id;
        this.tilte = tilte;
        this.intervenent = intervenent;
        this.rapport = rapport;
        Status = status;
        this.ticket = ticket;
    }
}
