package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_ROW_Material", uniqueConstraints = {@UniqueConstraint(columnNames = {"RM_DELETED_TOKEN", "RM_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RowMaterial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "RM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "RM_NAME", unique = true, nullable = false)
    private String name;
    @Column(name = "RM_DESCRIPTION", unique = true, nullable = false)
    private String description;
    @Column(name = "RM_TYPE", unique = true, nullable = false)
    private String type;
    @Column(name = "RM_REFERENCE", unique = true, nullable = false)
    private String reference;
    @Column(name = "RM_STATE", unique = true, nullable = false)
    private Boolean state;
    @Column(name = "RM_BUYING_PRICE", unique = true, nullable = false)
    private float buyingPrice;
    @Column(name = "RM_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "RM_DELETED_TOKEN")
    private UUID deletedToken;
    @ManyToOne
    @JoinColumn(name = "PR_ID", referencedColumnName = "PR_ID")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "SS_ID", referencedColumnName = "SS_ID")
    private SiteStock siteStock;

    public RowMaterial(Long id, String name, String description, String type, String reference, Boolean state, float buyingPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.reference = reference;
        this.state = state;
        this.buyingPrice = buyingPrice;
        this.isDeleted=false;
        this.deletedToken=null;
    }

    public RowMaterial(Long id, String name, String description, String type, String reference, Boolean state, float buyingPrice, Product product, SiteStock siteStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.reference = reference;
        this.state = state;
        this.buyingPrice = buyingPrice;
        this.product = product;
        this.siteStock = siteStock;
        this.isDeleted=false;
        this.deletedToken=null;
    }
}


