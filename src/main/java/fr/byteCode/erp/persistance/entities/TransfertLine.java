package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "T_TRANSFERT_LINE", uniqueConstraints = {@UniqueConstraint(columnNames = {"TL_DELETED_TOKEN", "TL_ID",}),
})
@Getter
@Setter
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TransfertLine  implements Serializable {
    @Id
    @Column(name = "TL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "TL_QUANTITY")
    private int quantity;
    @Column(name = "TL_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "TL_DELETED_TOKEN")
    private UUID deletedToken;
    @ManyToOne
    @JoinColumn(name ="RT_ID" ,referencedColumnName = "RT_ID")
    private  RequestTransfert requestTransfert;
    @OneToOne
    @JoinColumn(name = "CA_ID", referencedColumnName = "CA_ID")
    private Category category;
    private int state;
    public TransfertLine(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.category=null;
        this.deletedToken=null;
        this.isDeleted=false;
        this.state=0;
    }
    public TransfertLine() {
        this.deletedToken=null;
        this.isDeleted=false;
    }
}
