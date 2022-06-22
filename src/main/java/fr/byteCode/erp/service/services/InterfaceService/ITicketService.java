package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.TicketDto;
import fr.byteCode.erp.persistance.entities.Ticket;

import java.util.List;

public interface ITicketService extends IGenericService<Ticket,Long>{
    TicketDto save(TicketDto ticketDto);

    TicketDto update(TicketDto ticketDto);

    TicketDto findById(Long id);

    List<TicketDto> findALL();

    void delete(Long id);
}
