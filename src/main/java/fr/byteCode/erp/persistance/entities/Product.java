package fr.byteCode.erp.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_PRODUCT", uniqueConstraints = {@UniqueConstraint(columnNames = {"PR_DELETED_TOKEN", "PR_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "PR_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "PR_NAME")
    private String name;
    @Column(name = "PR_DESCRIPTION")
    private String description;
    @Column(name = "PR_SELLING_PRICE")
    private float sellingPrice;
    @Column(name = "PR_BUYING_PRICE")
    private float buyingPrice;
    @Column(name = "PR_STATE")
    private boolean state;
    @Column(name = "PR_ACTIVE")
    private boolean active;
    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Image> images=new ArrayList<>();
    @Column(name = "PR_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "PR_DELETED_TOKEN")
    private UUID deletedToken;
    @ManyToOne
    @JoinColumn(name = "CA_ID", referencedColumnName = "CA_ID")
    private Category category;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<RowMaterial> rowMaterials;
    @ManyToOne
    @JoinColumn(name = "SS_ID", referencedColumnName = "SS_ID")
    private SiteStock siteStock;

    public Product(Long id, String name, String description, float sellingPrice, float buyingPrice, boolean state, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.state = state;
        this.active = active;
        this.isDeleted = false;
        this.deletedToken = null;
        this.rowMaterials=null;
    }

    public Product(Long id, String name, String description, float sellingPrice, float buyingPrice, boolean state, boolean active, List<Image> images, Category category, SiteStock siteStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.state = state;
        this.active = active;
        this.images = images;
        this.category = category;
        this.siteStock = siteStock;
        this.isDeleted = false;
        this.deletedToken = null;
        this.rowMaterials=null;
    }
}

