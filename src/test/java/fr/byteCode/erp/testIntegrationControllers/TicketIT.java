package fr.byteCode.erp.testIntegrationControllers;

import fr.byteCode.erp.persistance.dto.TicketDto;
import fr.byteCode.erp.persistance.entities.Ticket;
import fr.byteCode.erp.service.convertor.TicketConverter;
import org.junit.jupiter.api.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static fr.byteCode.erp.persistance.constants.LongConstants.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TicketIT extends ITConfig {
    private HttpHeaders httpHeaders;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String dateNaissance = "2000-12-31 23:59";
    static LocalDateTime daeNess = LocalDateTime.parse(dateNaissance, formatter);
    private Ticket ticket;
    private TicketDto ticketDto;
    private static final String TICKET_URL = "/api/ticket/";

    @BeforeAll
    public void setUp() {

        this.httpHeaders = getHttpHeaders();
    }

    @Order(1)
    @Test
    void saveTicketIT() {
        ticket = new Ticket(ONE, "title", "author", "details", daeNess, daeNess, "status", false, null, null);
        HttpEntity entity = new HttpEntity(TicketConverter.modelToDto(ticket), httpHeaders);
        ResponseEntity<TicketDto> ticketDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + TICKET_URL,
                HttpMethod.POST, entity, TicketDto.class);
        assertNotNull(ticketDtoResponseEntity);
        assertEquals(HttpStatus.OK, ticketDtoResponseEntity.getStatusCode());
    }

    @Order(2)
    @Test
    void getTicketIT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<TicketDto> ticketDtoResponseEntity = getTestRestTemplate().exchange(getRootUrl() + TICKET_URL + ONE,
                HttpMethod.GET, entity, TicketDto.class);

        assertNotNull(ticketDtoResponseEntity);
        assertEquals(HttpStatus.OK, ticketDtoResponseEntity.getStatusCode());
    }

    @Order(3)
    @Test
    void getAllTicketlT() {
        HttpEntity entity = new HttpEntity(httpHeaders);

        ResponseEntity<List<TicketDto>> responseEntity = this.getTestRestTemplate().exchange(
                getRootUrl().concat(TICKET_URL + "tickets"), HttpMethod.GET, entity,
                new ParameterizedTypeReference<List<TicketDto>>() {
                });
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1L, responseEntity.getBody().get(0).getId());
    }

    @Test
    @Order(4)
    public void deleteTicketIT() {
        HttpEntity entity = new HttpEntity(ticketDto, httpHeaders);
        ResponseEntity<Boolean> ResponseEntity = getTestRestTemplate().exchange(getRootUrl() + TICKET_URL + "delete/" + ONE,
                HttpMethod.DELETE, entity, Boolean.class);
        assertNotNull(ResponseEntity);
        assertEquals(HttpStatus.OK, ResponseEntity.getStatusCode());
    }


}
