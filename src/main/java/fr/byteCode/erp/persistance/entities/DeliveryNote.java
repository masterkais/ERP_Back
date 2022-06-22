package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "T_DELIVER_NOTE", uniqueConstraints = {@UniqueConstraint(columnNames = {"DN_DELETED_TOKEN", "DN_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DeliveryNote implements Serializable {
    @Id
    @Column(name = "DN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "DN_DATE_EXIT")
    @EqualsAndHashCode.Include
    private LocalDateTime dateExit;
    @Column(name = "DN_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "DN_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SO_ID", referencedColumnName = "SO_ID")
    private SalesOrder salesOrder;

    public DeliveryNote(LocalDateTime now, SalesOrder salesOrder) {
        this.dateExit = now;
        this.salesOrder = salesOrder;
    }
    public DeliveryNote(Long id,LocalDateTime now) {
        this.dateExit = now;
        this.id=id;
    }
}