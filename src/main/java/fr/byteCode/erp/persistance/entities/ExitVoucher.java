package fr.byteCode.erp.persistance.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "T_EXIT_VOUCHER", uniqueConstraints = { @UniqueConstraint(columnNames = { "EXV_DELETED_TOKEN", "EXV_ID", }),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExitVoucher implements Serializable {
    @Id
    @Column(name = "EXV_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "EXV_DATE_EXIT")
    @EqualsAndHashCode.Include
    private LocalDateTime dateExit;
    @Column(name = "EXV_IS_DELETED", columnDefinition = "bit default 0")
    private boolean isDeleted;
    @Column(name = "EXV_DELETED_TOKEN")
    private UUID deletedToken;
    @OneToOne(mappedBy = "exitVoucher")
    private RequestTransfert requestTransfert;

    public ExitVoucher(LocalDateTime now, RequestTransfert requestTransfert) {
        this.dateExit=now;
        this.requestTransfert=requestTransfert;
    }
    public ExitVoucher(Long id,LocalDateTime now) {
        this.id=id;
        this.dateExit=now;
    }
}
