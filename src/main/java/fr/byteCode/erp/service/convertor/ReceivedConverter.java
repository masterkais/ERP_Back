package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.ReceivedDto;
import fr.byteCode.erp.persistance.entities.Received;

import java.time.LocalDateTime;

public class ReceivedConverter {
    public static Received dtoToModel(ReceivedDto receivedDto) {
        return new Received(receivedDto.getId(), receivedDto.getDate(), receivedDto.getMethod(), receivedDto.getType());
    }

    public static ReceivedDto modelToDto(Received received) {
        return new ReceivedDto(received.getId(), received.getDate(), received.getMethod(), received.getType(), received.getSalesOrder().getId());
    }
}
