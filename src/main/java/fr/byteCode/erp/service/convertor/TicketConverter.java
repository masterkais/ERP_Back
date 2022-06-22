package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.TicketDto;
import fr.byteCode.erp.persistance.entities.Ticket;

public final class TicketConverter {
    private TicketConverter(){super();}
    public static Ticket dtoToModel(TicketDto ticketDto){
        return new Ticket(ticketDto.getId(),ticketDto.getTitle(),ticketDto.getAuthor(),ticketDto.getDetail(),ticketDto.getStartDate(),ticketDto.getEndDate(),ticketDto.getStatus(),false,null,ticketDto.getUser());
    }
    public static TicketDto modelToDto(Ticket ticket){
        return new TicketDto(ticket.getId(),ticket.getTitle(),ticket.getAuthor(),ticket.getDetail(),ticket.getStartDate(),ticket.getEndDate(),ticket.getStatus(),ticket.getUser());
    }
}
