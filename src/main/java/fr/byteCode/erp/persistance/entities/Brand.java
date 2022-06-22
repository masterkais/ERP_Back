package fr.byteCode.erp.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_BRAND", uniqueConstraints = {@UniqueConstraint(columnNames = {"B_DELETED_TOKEN", "B_ID",}),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Brand {
    @Id
    @Column(name = "B_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "B_NAME", unique = true, nullable = false)
    private String name;
    @Column(name = "B_DESCRIPTION", unique = true, nullable = false)
    private String description;
    @Column(name = "B_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "B_DELETED_TOKEN")
    private UUID deletedToken;
    @JsonIgnore
    @OneToMany(mappedBy = "brand",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Category> categories;
    public Brand(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deletedToken=null;
        this.isDeleted=false;
    }
    public Brand(Long id, String name, String description, List<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.deletedToken=null;
        this.isDeleted=false;
    }
}
