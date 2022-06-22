package fr.byteCode.erp.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "T_TRANSFERT_LINE_DETAIL", uniqueConstraints = {@UniqueConstraint(columnNames = {"TLD_DELETED_TOKEN", "TLD_ID",}),
})
@Getter
@Setter
@AllArgsConstructor
public class TransfertLineDetail implements Serializable {
    @Id
    @Column(name = "TLD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TLD_IS_DELETED", columnDefinition = "bit default 0")
    @JsonIgnore
    private boolean isDeleted;
    @JsonIgnore
    @Column(name = "TlD_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "PR_ID", referencedColumnName = "PR_ID")
    private Product product;
    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "TL_ID", referencedColumnName = "TL_ID")
    @JsonIgnore
    private TransfertLine transfertLine;

    public TransfertLineDetail(Long id) {
        this.id = id;
        this.deletedToken = null;
        this.isDeleted = false;
    }

    public TransfertLineDetail() {
        this.deletedToken = null;
        this.isDeleted = false;
    }
}
