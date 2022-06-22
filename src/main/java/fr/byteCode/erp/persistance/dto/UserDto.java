package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.Group;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto implements Serializable {
    private Long id;
    private String lastName;
    private String firstName;
    private String adress;
    private String fax;
    private String email;
    private String city;
    private String picture;
    private boolean active;
    private LocalDateTime dateNaissanced;
    private LocalDateTime dateCreated;
    private String login;
    private String password;
    private List<Group> groupIds;
    public UserDto(Long id, String lastName, String firstName, String adress, String fax, String email, String city, String picture, boolean active, LocalDateTime dateNaissanced, LocalDateTime dateCreated,String login,String password,List<Group> groupsId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.adress = adress;
        this.fax = fax;
        this.email = email;
        this.city = city;
        this.picture = picture;
        this.active = active;
        this.dateNaissanced = dateNaissanced;
        this.dateCreated = dateCreated;
        this.login=login;
        this.password=password;
        this.groupIds=groupsId;

    }
}
