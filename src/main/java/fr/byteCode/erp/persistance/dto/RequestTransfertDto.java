package fr.byteCode.erp.persistance.dto;

import fr.byteCode.erp.persistance.entities.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class RequestTransfertDto {
    private Long id;
    private LocalDateTime dateCreated;
    private LocalDateTime dateAccpted;
    private int numberPalette;
    private List<Long> deliveryManIds;
    private Long inventaryManagerId;
    private Long exitVoucherId;
    private Long entryVoucher;
    private List<Long> transfertLineIds;
    private int state;
    private String siteDestinaion;
    private String siteSource;

    public RequestTransfertDto(Long id, LocalDateTime dateCreated, LocalDateTime dateAccpted, int numberPalette, List<Long> deliveryManIds, Long inventaryManagerId, Long exitVoucherId, Long entryVoucher, List<Long> transfertLineIds,int state,String siteDestinaion,String siteSource) {
        this.id = id;
        this.siteDestinaion=siteDestinaion;
        this.dateCreated = dateCreated;
        this.dateAccpted = dateAccpted;
        this.numberPalette = numberPalette;
        this.deliveryManIds = deliveryManIds;
        this.inventaryManagerId = inventaryManagerId;
        this.exitVoucherId = exitVoucherId;
        this.entryVoucher = entryVoucher;
        this.transfertLineIds = transfertLineIds;
        this.state=state;
        this.siteSource=siteSource;
    }
    public RequestTransfertDto(Long id, LocalDateTime dateCreated, LocalDateTime dateAccpted, int numberPalette, List<Long> deliveryManIds, Long inventaryManagerId, List<Long> transfertLineIds,int state,String siteDestinaion,String siteSource) {
        this.id = id;
        this.siteDestinaion=siteDestinaion;
        this.dateCreated = dateCreated;
        this.dateAccpted = dateAccpted;
        this.numberPalette = numberPalette;
        this.deliveryManIds = deliveryManIds;
        this.inventaryManagerId = inventaryManagerId;
        this.exitVoucherId = null;
        this.entryVoucher = null;
        this.transfertLineIds = transfertLineIds;
        this.state=state;
        this.siteSource=siteSource;
    }
    public RequestTransfertDto(Long id, LocalDateTime dateCreated, LocalDateTime dateAccpted, int numberPalette, List<Long> deliveryManIds, Long inventaryManagerId,int state,String siteDestinaion,String siteSource) {
        this.id = id;
        this.siteDestinaion=siteDestinaion;
        this.dateCreated = dateCreated;
        this.dateAccpted = dateAccpted;
        this.numberPalette = numberPalette;
        this.deliveryManIds = deliveryManIds;
        this.inventaryManagerId = inventaryManagerId;
        this.exitVoucherId = null;
        this.entryVoucher = null;
        this.transfertLineIds = null;
        this.state=state;
        this.siteSource=siteSource;
    }

    public RequestTransfertDto(Long id,Long inventaryManagerId,int state,String siteDestinaion,String siteSource) {
        this.inventaryManagerId = inventaryManagerId;
        this.id = id;
        this.siteDestinaion=siteDestinaion;
        this.dateCreated =LocalDateTime.now();
        this.dateAccpted = null;
        this.numberPalette = 0;
        this.deliveryManIds = null;
        this.inventaryManagerId = inventaryManagerId;
        this.exitVoucherId = null;
        this.entryVoucher = null;
        this.transfertLineIds = null;
        this.state = state;
        this.siteSource=siteSource;
    }

    public RequestTransfertDto(Long id, LocalDateTime dateCreated, Long inventaryManagerId, List<Long> transfertLineIds, int state,String siteDestinaion,String siteSource) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.inventaryManagerId = inventaryManagerId;
        this.transfertLineIds = transfertLineIds;
        this.state = state;
        this.siteDestinaion=siteDestinaion;
        this.siteSource=siteSource;
    }
    public RequestTransfertDto(Long id, LocalDateTime dateCreated, Long inventaryManagerId, List<Long> transfertLineIds, int state,String siteDestinaion,String siteSource,Long exitVoucherId) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.inventaryManagerId = inventaryManagerId;
        this.transfertLineIds = transfertLineIds;
        this.state = state;
        this.siteDestinaion=siteDestinaion;
        this.siteSource=siteSource;
        this.exitVoucherId=exitVoucherId;
    }
}
