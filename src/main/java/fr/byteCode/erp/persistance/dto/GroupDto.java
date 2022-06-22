package fr.byteCode.erp.persistance.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GroupDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String privileged;

}