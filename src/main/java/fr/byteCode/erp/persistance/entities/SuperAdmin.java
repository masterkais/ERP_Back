package fr.byteCode.erp.persistance.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class SuperAdmin extends User {
    public SuperAdmin(Long id, String lastName, String firstName, String adress, String fax, String email, String city, String picture, boolean active, LocalDateTime dateNaissanced, LocalDateTime dateCreated, boolean isDeleted, UUID deletedToken, List<Group> groups, String userName, String password) {
        super(id, lastName, firstName, adress, fax, email, city, picture, active, dateNaissanced, dateCreated, isDeleted, deletedToken, groups, userName, password);
    }

    public SuperAdmin() {
    }


    public SuperAdmin(Long id, String lastName, String firstName, String adress, String fax, String email, String city, String picture, boolean active, LocalDateTime dateNaissanced, LocalDateTime dateCreated, String login, String password) {
        super(id, lastName, firstName, adress, fax, email, city, picture, active, dateNaissanced, dateCreated, login, password);
    }
}
