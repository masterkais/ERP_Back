package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "T_INTERVENTION", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"INTERVENTION_DELETED_TOKEN", "INTERVENTION_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Intervention implements Serializable {
    @Id
    @Column(name = "INTERVENTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "INTERVENTION_TITLE")
    @EqualsAndHashCode.Include
    private String tilte;

    @Column(name = "INTERVENTION_START_DATE")
    @EqualsAndHashCode.Include
    private LocalDateTime startDate;

    @Column(name = "INTERVENTION_END_DATE")
    @EqualsAndHashCode.Include
    private LocalDateTime endDate;

    @Column(name = "INTERVENTION_INTERVENENT")
    @EqualsAndHashCode.Include
    private String intervenent;

    @Column(name = "INTERVENTION_RAPPORT")
    @EqualsAndHashCode.Include
    private String rapport;

    @Column(name = "INTERVENTION_STATUS")
    @EqualsAndHashCode.Include
    private String Status;

    @Column(name = "INTERVENTION_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "INTERVENTION_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne
    private Ticket ticket;





}
