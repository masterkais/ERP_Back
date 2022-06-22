package fr.byteCode.erp.persistance.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BrandDto {

    private static final long serialVersionUID = 1L;


    private Long id;
    private String name;
    private String description;
    @JsonIgnore
    private List<Long> brandListIds;

    public BrandDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}


