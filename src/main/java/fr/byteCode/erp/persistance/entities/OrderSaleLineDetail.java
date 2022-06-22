package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "T_ORDER_SALE_LINE_DETAIL", uniqueConstraints = {@UniqueConstraint(columnNames = {"TOS_DELETED_TOKEN", "TOS_ID",}),
})
@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderSaleLineDetail implements Serializable {
    @Id
    @Column(name = "TOS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "TOS_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "TOS_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PR_ID", referencedColumnName = "PR_ID")
    private Product product;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "LS_ID", referencedColumnName = "LS_ID")
    private LigneSale ligneSale;

    public OrderSaleLineDetail(Long id) {
        this.id = id;
        this.deletedToken = null;
        this.isDeleted = false;
    }

    public OrderSaleLineDetail() {
        this.deletedToken = null;
        this.isDeleted = false;
    }
}
