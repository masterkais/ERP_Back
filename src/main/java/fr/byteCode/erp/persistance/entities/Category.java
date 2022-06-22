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
@Table(name = "T_CATEGORY", uniqueConstraints = {@UniqueConstraint(columnNames = {"CA_DELETED_TOKEN", "CA_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CA_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "CA_NAME", unique = true, nullable = false)
    private String name;
    @Column(name = "CA_DESCRIPTION", unique = true, nullable = false)
    private String description;
    @Column(name = "CA_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "CA_DELETED_TOKEN")
    private UUID deletedToken;
    @JsonIgnore
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Product> productList=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "B_ID", referencedColumnName = "B_ID")
    private Brand brand;
    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deletedToken=null;
        this.isDeleted=false;
        this.productList=null;
        this.brand=null;
    }
    public Category(Long id, String name, String description, List<Product> productList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productList = productList;
        this.deletedToken=null;
        this.isDeleted=false;
    }

    public Category(Long id, String name, String description, Brand brand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isDeleted = false;
        this.deletedToken = null;
        this.productList = null;
        this.brand = brand;
    }
}

