package fr.byteCode.erp.persistance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryDto {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String description;
    @JsonIgnore
    private List<Long> productListIds;
    private Long idBrand;
    public CategoryDto(Long id, String name, String description,Long idBrand) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.idBrand=idBrand;
        this.productListIds=null;
    }
}

