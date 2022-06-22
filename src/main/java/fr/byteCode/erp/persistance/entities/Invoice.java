package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "T_INVOICE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"INVOICE_DELETED_TOKEN", "INVOICE_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Invoice implements Serializable {
    @Id
    @Column(name = "INVOICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "INVOICE_DATE")
    @EqualsAndHashCode.Include
    private LocalDateTime date;

    @Column(name = "INVOICE_DESC")
    @EqualsAndHashCode.Include
    private String description;

    @Column(name = "INVOICE_STATUS")
    @EqualsAndHashCode.Include
    private String status;

    @Column(name = "INVOICE_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "INVOICE_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SO_ID", referencedColumnName = "SO_ID")
    private SalesOrder salesOrder;

    public Invoice(Long id, LocalDateTime date, String description, String status) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.status = status;
        this.isDeleted=false;
        this.deletedToken=null;
    }
}
