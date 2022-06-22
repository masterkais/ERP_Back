package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "T_LIGNESALE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"LS_DELETED_TOKEN", "LS_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LigneSale implements Serializable {
    @Id
    @Column(name = "LS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "LS_QUANTITY")
    private int quantity;
    @Column(name = "LS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "LS_DELETED_TOKEN")
    private UUID deletedToken;
    @ManyToOne
    @JoinColumn(name ="SO_ID" ,referencedColumnName = "SO_ID")
    private  SalesOrder salesOrder;
    @OneToOne
    @JoinColumn(name = "CA_ID", referencedColumnName = "CA_ID")
    private Category category;
    private int state;

    public LigneSale(Long id, int quantity, int state) {
        this.id = id;
        this.quantity = quantity;
        this.state = state;
        this.isDeleted=false;
        this.deletedToken=null;
    }
}
