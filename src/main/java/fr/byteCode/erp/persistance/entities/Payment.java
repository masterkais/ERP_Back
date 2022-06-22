package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "T_PAYMENT", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"PAYMENT_DELETED_TOKEN", "PAYMENT_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Payment implements Serializable {
    @Id
    @Column(name = "PAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "PAYMENT_DATE")
    @EqualsAndHashCode.Include
    private LocalDateTime date;

    @Column(name = "PAYMENT_DESC")
    @EqualsAndHashCode.Include
    private String origin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID")
    private Invoice invoice;

    @Column(name = "PAYMENT_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "PAYMENT_DELETED_TOKEN")
    private UUID deletedToken;

    public Payment(Long id, LocalDateTime date, String origin) {
        this.id = id;
        this.date = date;
        this.origin = origin;
        this.isDeleted=false;
        this.deletedToken=null;
    }
}
