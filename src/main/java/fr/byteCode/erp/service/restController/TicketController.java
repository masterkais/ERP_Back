package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.TicketDto;
import fr.byteCode.erp.service.services.InterfaceService.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/ticket")
public class TicketController {
    private final ITicketService ticketService;
    @Autowired
    public TicketController(ITicketService ticketService){
        this.ticketService=ticketService;   }

    @GetMapping("/tickets")
    public List<TicketDto> getAllTickets(){
        return this.ticketService.findALL();
    }

    @PostMapping("")
    public TicketDto saveTikcet(@RequestBody TicketDto ticketDto){
        return this.ticketService.save(ticketDto);
    }
    @PutMapping("")
    public TicketDto updateTikcet(@RequestBody TicketDto ticketDto){
        return this.ticketService.update(ticketDto);
    }
    @GetMapping("/{id}")
    public TicketDto getTicketById(@PathVariable Long id){
        return  this.ticketService.findById(id);
    }
    @DeleteMapping("/delete/{id}")
    public boolean deleteTicketById(@PathVariable Long id){
        this.ticketService.delete(id);
        return true;
    }
}
