package fr.byteCode.erp.persistance.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Client extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    public Client(Long id, String lastName, String firstName, String adress, String fax, String email, String city, String picture, boolean active, LocalDateTime dateNaissanced, LocalDateTime dateCreated, boolean isDeleted, UUID deletedToken, List<Group> groups, String userName, String password, List<RequestTransfert> requestTransferts, List<SalesOrder> salesOrders) {
        super(id, lastName, firstName, adress, fax, email, city, picture, active, dateNaissanced, dateCreated, isDeleted, deletedToken, groups, userName, password, requestTransferts, salesOrders);
    }

    public Client() {
    }

    public Client(Long id, String lastName, String firstName, String adress, String fax, String email, String city, String picture, boolean active, LocalDateTime dateNaissanced, LocalDateTime dateCreated, String login, String password) {
        super(id, lastName, firstName, adress, fax, email, city, picture, active, dateNaissanced, dateCreated, login, password);
    }

    public Client(Long id, String lastName, String firstName, String adress, String fax, String email, String city, String picture, boolean active, LocalDateTime dateNaissanced, LocalDateTime dateCreated, boolean isDeleted, UUID deletedToken, List<Group> groups, String userName, String password) {
        super(id, lastName, firstName, adress, fax, email, city, picture, active, dateNaissanced, dateCreated, isDeleted, deletedToken, groups, userName, password);
    }
}

