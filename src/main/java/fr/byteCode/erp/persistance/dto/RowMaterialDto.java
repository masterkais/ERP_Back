package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.Product;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RowMaterialDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    private String type;
    private String reference;
    private Boolean state;
    private float buyingPrice;
    private Long siteStockId;
    private Long productId;
    public RowMaterialDto(Long id, String name, String description, String type, String reference, Boolean state, float buyingPrice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.reference = reference;
        this.state = state;
        this.buyingPrice = buyingPrice;
    }


}


