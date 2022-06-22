package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.User;
import lombok.*;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TicketDto {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String author;
    private String detail;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private User user;

    public TicketDto(Long id, String title, String author, String detail, LocalDateTime startDate, String status, User user) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.detail = detail;
        this.startDate = startDate;
        this.status = status;
        this.user = user;
    }
}
