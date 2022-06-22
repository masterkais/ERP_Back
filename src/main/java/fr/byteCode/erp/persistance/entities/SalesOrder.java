package fr.byteCode.erp.persistance.entities;


import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_SALESORDER", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"SO_DELETED_TOKEN", "SO_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesOrder implements Serializable {

    @Id
    @Column(name = "SO_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "SO_DATE_CREATED")
    private LocalDateTime dateCreated;
    @Column(name = "SO_DATE_ACCEPTED")
    private LocalDateTime dateAccpted;
    @Column(name = "SO_NBR_PALETTE")
    private int numberPalette;
    @Column(name = "SO_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "SO_DELETED_TOKEN")
    private UUID deletedToken;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<User> deliveryManList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "U_ID", referencedColumnName = "U_ID")
    private User purchasingManager;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "INVOICE_ID", referencedColumnName = "INVOICE_ID")
    private Invoice invoice;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RECEIVED_ID", referencedColumnName = "RECEIVED_ID")
    private Received received;

    @OneToMany(mappedBy = "salesOrder", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<LigneSale> ligneSales;
    @Column(name = "SO_STATE")
    private int state;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "U_ID")
    private User client;
    private String siteStockSale;
    private float totalSale;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "DN_ID", referencedColumnName = "DN_ID")
    private DeliveryNote deliveryNote;

    public SalesOrder(Long id, LocalDateTime dateCreated, LocalDateTime dateAccpted, int numberPalette, int state,String siteStockSale,float totalSale) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateAccpted = dateAccpted;
        this.numberPalette = numberPalette;
        this.state = state;
        this.isDeleted=false;
        this.deletedToken=null;
        this.siteStockSale=siteStockSale;
        this.totalSale=totalSale;
    }
}
