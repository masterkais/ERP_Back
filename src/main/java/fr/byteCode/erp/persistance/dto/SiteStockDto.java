package fr.byteCode.erp.persistance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SiteStockDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    private String adress;
    private boolean state;
    @JsonIgnore
    private List<Long> rowMaterialIds;

    public SiteStockDto(Long id, String name, String description, String adress, boolean state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adress = adress;
        this.state = state;
    }
}

