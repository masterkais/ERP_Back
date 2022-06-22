package fr.byteCode.erp.persistance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import fr.byteCode.erp.persistance.entities.Image;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    private float sellingPrice;
    private float buyingPrice;
    private boolean state;
    private boolean active;
    private List<Long> imagesIds;
    @JsonIgnore
    private List<Long> rowMaterialsids;
    private Long categoryId;
    private Long siteStockId;
    public ProductDto(Long id, String name, String description, float sellingPrice, float buyingPrice, boolean state, boolean active, List<Long> imagesIds, int quantityAvailable,List<Long> rowMaerialsIds,Long idCategory,Long siteStockId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.state = state;
        this.active = active;
        this.imagesIds = imagesIds;
        this.rowMaterialsids = rowMaerialsIds;
        this.categoryId = idCategory;
        this.siteStockId=siteStockId;
    }
    public ProductDto(Long id, String name, String description, float sellingPrice, float buyingPrice, boolean state, boolean active, List<Long> imagesIds,Long idCategory,Long siteStockId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.state = state;
        this.active = active;
        this.imagesIds = imagesIds;
        this.categoryId = idCategory;
        this.siteStockId=siteStockId;
    }

    public ProductDto(Long id, String name, String description, float sellingPrice, float buyingPrice, boolean state, boolean active, Long categoryId, Long siteStockId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.state = state;
        this.active = active;
        this.rowMaterialsids = rowMaterialsids;
        this.categoryId = categoryId;
        this.siteStockId = siteStockId;
    }

    public ProductDto(Long id, String name, String description, float sellingPrice, float buyingPrice, boolean state, boolean active) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellingPrice = sellingPrice;
        this.buyingPrice = buyingPrice;
        this.state = state;
        this.active = active;
        this.imagesIds = null;
        this.rowMaterialsids = null;
        this.categoryId = null;
        this.siteStockId = null;
    }
}

