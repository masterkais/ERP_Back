package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.Invoice;
import fr.byteCode.erp.persistance.entities.LigneSale;
import fr.byteCode.erp.persistance.entities.Received;
import fr.byteCode.erp.persistance.entities.User;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SalesOrderDto implements Serializable {
    private Long id;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAccpted;
    private int numberPalette;
    private List<Long> deliveryManIds = new ArrayList<>();
    private Long purchasingManagerId;
    private Long invoiceId;
    private Long receivedId;
    private List<Long> ligneSaleIds;
    private int state;
    private Long clientId;
    private String siteStockSaleName;
    private float totalSale;

    public SalesOrderDto(Long id, LocalDateTime dateCreated, LocalDateTime dateAccpted, int numberPalette, List<Long> deliveryManIds, Long purchasingManagerId, List<Long> ligneSaleIds, int state, Long clientId, String siteStockSaleName, float totalSale) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateAccpted = dateAccpted;
        this.numberPalette = numberPalette;
        this.deliveryManIds = deliveryManIds;
        this.purchasingManagerId = purchasingManagerId;
        this.ligneSaleIds = ligneSaleIds;
        this.state = state;
        this.clientId = clientId;
        this.siteStockSaleName = siteStockSaleName;
        this.totalSale = totalSale;
    }
}
