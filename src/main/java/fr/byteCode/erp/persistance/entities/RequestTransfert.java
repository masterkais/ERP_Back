package fr.byteCode.erp.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_REQUEST_TRANSFERT", uniqueConstraints = { @UniqueConstraint(columnNames = { "RT_DELETED_TOKEN", "RT_ID", }),
})
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RequestTransfert implements Serializable {
    @Id
    @Column(name = "RT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "RT_DATE_CREATED")
    @EqualsAndHashCode.Include
    private LocalDateTime dateCreated;
    @Column(name = "RT_DATE_ACCEPTED")
    @EqualsAndHashCode.Include
    private LocalDateTime dateAccpted;
    @Column(name = "RT_NBR_PALETTE")
    @EqualsAndHashCode.Include
    private int numberPalette;
    @Column(name = "RT_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "RT_DELETED_TOKEN")
    private UUID deletedToken;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<User> deliveryManList=new ArrayList<>();
    @ManyToOne
    @JoinColumn(name ="U_ID" ,referencedColumnName = "U_ID")
    private  User inventoryManager;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ENV_ID", referencedColumnName = "ENV_ID")
    private EntryVoucher entryVoucher;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "EXV_ID", referencedColumnName = "EXV_ID")
    private ExitVoucher exitVoucher;
    @OneToMany(mappedBy = "requestTransfert",fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<TransfertLine> transfertLines;
    private int state;
    @Column(name = "TL_SITEDESTINATION")
    private String siteDestinaion;
    @Column(name = "TL_SITESOURCE")
    private String siteSource;
    public RequestTransfert(Long id, int numberPalette, List<User> deliveryManList, User inventoryManager,String siteDestinaion,String siteSource) {
        this.id = id;
        this.numberPalette = numberPalette;
        this.deliveryManList = deliveryManList;
        this.inventoryManager = inventoryManager;
        this.isDeleted=false;
        this.deletedToken=null;
        this.transfertLines=null;
        this.exitVoucher=null;
        this.entryVoucher=null;
        this.dateAccpted = null;
        this.state=0;
        this.dateCreated=LocalDateTime.now();
        this.siteDestinaion=siteDestinaion;
        this.siteSource=siteSource;
    }
    public RequestTransfert(Long id, int numberPalette,String siteDestinaion,String siteSource) {
        this.id = id;
        this.numberPalette = numberPalette;
        this.siteDestinaion=siteDestinaion;
        this.isDeleted=false;
        this.deletedToken=null;
        this.transfertLines=null;
        this.exitVoucher=null;
        this.entryVoucher=null;
        this.dateAccpted = null;
        this.state=0;
        this.dateCreated=LocalDateTime.now();
        this.siteSource=siteSource;

    }
    public RequestTransfert(Long id, int numberPalette,int state,String siteDestinaion,String siteSource){
        this.id = id;
        this.siteDestinaion=siteDestinaion;
        this.siteSource=siteSource;
        this.numberPalette = numberPalette;
        this.isDeleted=false;
        this.deletedToken=null;
        this.transfertLines=null;
        this.exitVoucher=null;
        this.entryVoucher=null;
        this.dateAccpted = null;
        this.dateCreated=LocalDateTime.now();
        this.state=state;

    }

    public RequestTransfert() {
        this.dateCreated = LocalDateTime.now();
        this.siteDestinaion="";
        this.siteSource="";
        this.dateAccpted = null;
        this.numberPalette = 0;
        this.isDeleted = false;
        this.deletedToken = null;
        this.deliveryManList = null;
        this.inventoryManager = null;
        this.entryVoucher = null;
        this.exitVoucher = null;
        this.transfertLines = null;
        this.state =0;
    }
}
