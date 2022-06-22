package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "T_VEHICULE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"V_DELETED_TOKEN", "V_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Vehicule implements Serializable {
    @Id
    @Column(name = "V_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "V_LABLE")
    private String lable;
    @Column(name = "V_SERIAL")
    private String serialNumber;
    @Column(name = "V_DATE_PURCHASE")
    private LocalDateTime datePurchase;
    @Column(name = "V_STATE")
    private boolean state;
    @Column(name = "V_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "V_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne
    private User deliveryMan;

    public Vehicule(Long id, String lable, String serialNumber, LocalDateTime datePurchase, boolean state) {
        this.id = id;
        this.lable = lable;
        this.serialNumber = serialNumber;
        this.datePurchase = datePurchase;
        this.state = state;
        this.isDeleted = false;
        this.deletedToken = null;
    }
    public Vehicule(Long id, String lable, String serialNumber, LocalDateTime datePurchase, boolean state, DeliveryMan deliveryMan) {
        this.id = id;
        this.lable = lable;
        this.serialNumber = serialNumber;
        this.datePurchase = datePurchase;
        this.state = state;
        this.deliveryMan = deliveryMan;
        this.isDeleted = false;
        this.deletedToken = null;
    }

}
