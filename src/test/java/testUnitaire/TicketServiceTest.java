package testUnitaire;

import fr.byteCode.erp.persistance.constants.EntitiesContants;
import fr.byteCode.erp.persistance.dao.TicketDao;
import fr.byteCode.erp.persistance.dto.TicketDto;
import fr.byteCode.erp.persistance.entities.Ticket;
import fr.byteCode.erp.service.convertor.TicketConverter;
import fr.byteCode.erp.service.services.ImplService.TicketService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TicketServiceTest {
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String dateNaissance = "2000-12-31 23:59";
    static LocalDateTime daeNess = LocalDateTime.parse(dateNaissance, formatter);

    @InjectMocks
    private TicketService ticketService;
    @Mock
    private TicketDao ticketDao;
    private Ticket ticket;
    private TicketDto ticketDto;
    private List<Ticket> ticketList;
    private List<TicketDto> ticketDtos;

    @BeforeEach
    void setup() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        System.out.println("***start***");
        MockitoAnnotations.openMocks(this);
        ticket = new Ticket(ONE, "title", "author", "details", daeNess, daeNess, "status", false, null, EntitiesContants.INVENTORY_MANAGER);
        ticketDto = TicketConverter.modelToDto(ticket);
        ticketList = new ArrayList<>();
        ticketDtos = new ArrayList<>();
        ticketList.add(ticket);
        ticketDtos.add(ticketDto);

    }

    @AfterEach
    void tearDown() {
        ticketDtos = null;
        ticket = null;
        ticketDto = null;
    }

    @Test
    @DisplayName("test for save")
    public void saveTicketTest() {
        Mockito.when(ticketDao.saveAndFlush(ticket)).thenReturn(ticket);
        TicketDto ticketResult = ticketService.save(ticketDto);
        assertEquals(ticketResult, ticketDto);
        Mockito.verify(ticketDao, Mockito.times(1)).saveAndFlush(ticket);

    }

    @Test
    @DisplayName("test for update")
    public void updateTicketTest() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Mockito.when(ticketDao.findOne(ArgumentMatchers.anyLong())).thenReturn(ticket);
        Mockito.when(ticketDao.saveAndFlush(ticket)).thenReturn(ticket);
        TicketDto ticketDtoResult = ticketService.findById(ticket.getId());
        TicketDto ticketDtoSaved = ticketService.save(ticketDto);
        assertEquals(ticketDtoResult, ticketDto);
        assertEquals(ticketDtoSaved, ticketDto);
        assertDoesNotThrow(() -> ticketService.findById(ticket.getId()));
        verify(ticketDao, times(2)).findOne(ticket.getId());
        verify(ticketDao, times(1)).saveAndFlush(ticket);
    }

    @DisplayName("test for get all tickets")
    @Test
    public void allTicketTest() {
        //GIVEN
        when(ticketDao.findAll()).thenReturn(ticketList);
        //WHEN
        List<TicketDto> results = ticketService.findALL();
        //THEN
        assertNotNull(results);
        verify(ticketDao, times(1)).findAll();
    }

    @DisplayName("test for delete by id")
    @Test
    public void testDeleteTicket() {
        UUID uuid = UUID.randomUUID();
        //GIVEN
        when(ticketDao.findOne(anyLong())).thenReturn(ticket);
        assertDoesNotThrow(
                () -> {
                    ticketService.delete(ticket.getId());
                }
        );
        verify(ticketDao, times(1)).findOne(ticket.getId());
    }

    @DisplayName("test for get ticket by id")
    @Test
    public void testGetTicketById() {
        when(ticketDao.findOne(anyLong())).thenReturn(ticket);
        TicketDto ticketDtoResult = ticketService.findById(ticket.getId());
        assertEquals(ticketDtoResult, ticketDto);
        assertDoesNotThrow(() -> ticketService.findById(ticket.getId()));
        verify(ticketDao, times(2)).findOne(ticket.getId());
    }
}
