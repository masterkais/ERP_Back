package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "T_ENTRY_VOUCHER", uniqueConstraints = { @UniqueConstraint(columnNames = { "ENV_DELETED_TOKEN", "ENV_ID", }),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "isDeleted", "deletedToken" })
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EntryVoucher implements Serializable {
    @Id
    @Column(name = "ENV_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "ENV_DATE_ENTRY")
    @EqualsAndHashCode.Include
    private LocalDateTime dateEntry;
    @Column(name = "ENV_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "ENV_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne(mappedBy = "entryVoucher")
    private RequestTransfert requestTransfert;

    public EntryVoucher(LocalDateTime dateEntry, RequestTransfert requestTransfert) {
        this.dateEntry = dateEntry;
        this.requestTransfert = requestTransfert;
        this.deletedToken=null;
        this.isDeleted=false;
    }
    public EntryVoucher(Long id,LocalDateTime dateEntry) {
        this.id=id;
        this.dateEntry = dateEntry;
        this.deletedToken=null;
        this.isDeleted=false;
    }
}
