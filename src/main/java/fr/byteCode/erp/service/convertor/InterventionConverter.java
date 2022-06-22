package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.InterventionDto;
import fr.byteCode.erp.persistance.entities.Intervention;

public final class InterventionConverter {
    private InterventionConverter(){super();}
    public  static Intervention dtoToModel(InterventionDto interventionDto){
        return new Intervention(interventionDto.getId(),interventionDto.getTilte(),interventionDto.getStartDate(),interventionDto.getEndDate(),interventionDto.getIntervenent(),interventionDto.getRapport(),interventionDto.getStatus(),false,null,interventionDto.getTicket());
    }
    public static InterventionDto modelToDto(Intervention intervention){
        return new InterventionDto(intervention.getId(),intervention.getTilte(),intervention.getStartDate(),intervention.getEndDate(),intervention.getIntervenent(),intervention.getRapport(),intervention.getStatus(),intervention.getTicket());
    }
}
