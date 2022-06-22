package fr.byteCode.erp.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_SITE_STOCK", uniqueConstraints = {@UniqueConstraint(columnNames = {"SS_DELETED_TOKEN", "SS_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SiteStock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "SS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "SS_NAME")
    private String name;
    @Column(name = "SS_DESCRIPTION")
    private String description;
    @Column(name = "SS_ADRESS")
    private String adress;
    @Column(name = "SS_STATE")
    private boolean state;
    @Column(name = "SS_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "SS_DELETED_TOKEN")
    private UUID deletedToken;
    @JsonIgnore
    @OneToMany(mappedBy = "siteStock",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RowMaterial> rowMaterials=new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Product> product;

    public SiteStock(Long id, String name, String description, String adress, boolean state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adress = adress;
        this.state = state;
        this.isDeleted=false;
        this.deletedToken=null;
    }
}

