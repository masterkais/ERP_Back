package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.TicketDao;
import fr.byteCode.erp.persistance.dto.TicketDto;
import fr.byteCode.erp.persistance.entities.Ticket;
import fr.byteCode.erp.service.convertor.TicketConverter;
import fr.byteCode.erp.service.services.InterfaceService.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Slf4j
@Service
public class TicketService extends GenericService<Ticket, Long> implements ITicketService {
@Autowired
    private TicketDao ticketDao;
    private TicketService(TicketDao ticketDao){

        this.ticketDao=ticketDao;
    }

    @Override
    public TicketDto save(TicketDto ticketDto) {
        Objects.requireNonNull(ticketDto);
        Ticket ticketSaved = ticketDao.saveAndFlush(TicketConverter.dtoToModel(ticketDto));
        return TicketConverter.modelToDto(ticketSaved);
    }

    @Override
    public TicketDto update(TicketDto ticketDto) {
        Objects.requireNonNull(ticketDto);
        Ticket ticketSaved = ticketDao.saveAndFlush(TicketConverter.dtoToModel(ticketDto));
        return TicketConverter.modelToDto(ticketSaved);
    }

    @Override
    public TicketDto findById(Long id) {

        return TicketConverter.modelToDto(this.ticketDao.findOne(id));
    }

    @Override
    public List<TicketDto> findALL() {

        List<Ticket> tickets=this.ticketDao.findAll();
        List<TicketDto> ticketDtos=new ArrayList<>();
        tickets.forEach(ticket ->{
            ticketDtos.add(TicketConverter.modelToDto(ticket));
        });
        return ticketDtos;
    }
    @Override
    public void delete(Long id) {
        UUID uuid = UUID.randomUUID();
        if (findById(id) != null) {
            ticketDao.delete(id, uuid);
        }
    }
}
