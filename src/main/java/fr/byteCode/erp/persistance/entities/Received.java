package fr.byteCode.erp.persistance.entities;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "T_RECIVED", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"RECIVED_DELETED_TOKEN", "RECEIVED_ID"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"isDeleted", "deletedToken"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Received implements Serializable {
    @Id
    @Column(name = "RECEIVED_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "RECIVED_DATE")
    @EqualsAndHashCode.Include
    private LocalDateTime date;

    @Column(name = "RECIVED_METHOD")
    @EqualsAndHashCode.Include
    private String method;

    @Column(name = "RECIVED_TYPE")
    @EqualsAndHashCode.Include
    private String type;

    @Column(name = "RECIVED_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "RECIVED_DELETED_TOKEN")
    private UUID deletedToken;

    @OneToOne(mappedBy = "received")
    private SalesOrder salesOrder;

    public Received(Long id, LocalDateTime date, String method, String type) {
        this.id = id;
        this.date = date;
        this.method = method;
        this.type = type;
        this.isDeleted=false;
        this.deletedToken=null;
    }
}
