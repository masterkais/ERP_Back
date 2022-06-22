package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "T_TICKET", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"TICKET_DELETED_TOKEN", "TICKET_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Ticket implements Serializable {
    @Id
    @Column(name = "TICKET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "TICKET_TITLE")
    @EqualsAndHashCode.Include
    private String title;

    @Column(name = "TICKET_AUTHOR")
    @EqualsAndHashCode.Include
    private String author;


    @Column(name = "TICKET_DETAIL")
    @EqualsAndHashCode.Include
    private String detail;

    @Column(name = "TICKET_START_DATE")
    @EqualsAndHashCode.Include
    private LocalDateTime startDate;

    @Column(name = "TICKET_END_DATE")
    @EqualsAndHashCode.Include
    private LocalDateTime endDate;

    @Column(name = "TICKET_STATUS")
    @EqualsAndHashCode.Include
    private String status;
    @Column(name = "TICKET_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "TICKET_DELETED_TOKEN")
    private UUID deletedToken;

    @ManyToOne
    private User user;


}
