package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.RequestTransfertDto;
import fr.byteCode.erp.persistance.entities.RequestTransfert;
import fr.byteCode.erp.persistance.entities.TransfertLine;
import fr.byteCode.erp.persistance.entities.User;

import java.util.ArrayList;
import java.util.List;

public final class RequestTransfertConverter {
    private RequestTransfertConverter() {
        super();
    }

    public static RequestTransfert dtoToModel(RequestTransfertDto requestTransfertDto) {
        return new RequestTransfert(requestTransfertDto.getId(), requestTransfertDto.getNumberPalette(), requestTransfertDto.getState(), requestTransfertDto.getSiteDestinaion(), requestTransfertDto.getSiteSource());
    }

    public static RequestTransfertDto modelToDto(RequestTransfert requestTransfert) {
        List<Long> deliveryManIds = new ArrayList<>();
        if (requestTransfert.getState() == 0) {
            System.out.println(requestTransfert.getId());
            return new RequestTransfertDto(requestTransfert.getId(), requestTransfert.getInventoryManager().getId(), requestTransfert.getState(), requestTransfert.getSiteDestinaion(), requestTransfert.getSiteSource());
        } else if (requestTransfert.getState() == 1) {
            List<Long> transfertLineIds = new ArrayList<>();
            List<TransfertLine> transfertLines = new ArrayList<>();
            transfertLines = requestTransfert.getTransfertLines();
            transfertLines.forEach(d -> {
                transfertLineIds.add(d.getId());
            });
            return new RequestTransfertDto(requestTransfert.getId(), requestTransfert.getDateCreated(), requestTransfert.getInventoryManager().getId(), transfertLineIds, requestTransfert.getState(), requestTransfert.getSiteDestinaion(), requestTransfert.getSiteSource());
        } else if ((requestTransfert.getState() == 2)) {
            List<User> deliveryManList = requestTransfert.getDeliveryManList();
            deliveryManList.forEach(d -> {
                deliveryManIds.add(d.getId());
            });

            List<Long> transfertLineIds = new ArrayList<>();
            List<TransfertLine> transfertLines = new ArrayList<>();
            transfertLines = requestTransfert.getTransfertLines();
            transfertLines.forEach(d -> {
                transfertLineIds.add(d.getId());
            });
            return new RequestTransfertDto(requestTransfert.getId(), requestTransfert.getDateCreated(), requestTransfert.getDateAccpted(), requestTransfert.getNumberPalette(), deliveryManIds, requestTransfert.getInventoryManager().getId(), transfertLineIds, requestTransfert.getState(), requestTransfert.getSiteDestinaion(), requestTransfert.getSiteSource());
        } else if ((requestTransfert.getState() == 3)) {
            List<User> deliveryManList = requestTransfert.getDeliveryManList();
            deliveryManList.forEach(d -> {
                deliveryManIds.add(d.getId());
            });

            List<Long> transfertLineIds = new ArrayList<>();
            List<TransfertLine> transfertLines = new ArrayList<>();
            transfertLines = requestTransfert.getTransfertLines();
            transfertLines.forEach(d -> {
                transfertLineIds.add(d.getId());
            });
            RequestTransfertDto requestTransfertDto = new RequestTransfertDto(requestTransfert.getId(), requestTransfert.getDateCreated(), requestTransfert.getDateAccpted(), requestTransfert.getNumberPalette(), deliveryManIds, requestTransfert.getInventoryManager().getId(), transfertLineIds, requestTransfert.getState(), requestTransfert.getSiteDestinaion(), requestTransfert.getSiteSource());
            requestTransfertDto.setExitVoucherId(requestTransfert.getExitVoucher().getId());
            return requestTransfertDto;
        } else if ((requestTransfert.getState() == 4)) {
            List<User> deliveryManList = requestTransfert.getDeliveryManList();
            deliveryManList.forEach(d -> {
                deliveryManIds.add(d.getId());
            });

            List<Long> transfertLineIds = new ArrayList<>();
            List<TransfertLine> transfertLines = new ArrayList<>();
            transfertLines = requestTransfert.getTransfertLines();
            transfertLines.forEach(d -> {
                transfertLineIds.add(d.getId());
            });
            RequestTransfertDto requestTransfertDto = new RequestTransfertDto(requestTransfert.getId(), requestTransfert.getDateCreated(), requestTransfert.getDateAccpted(), requestTransfert.getNumberPalette(), deliveryManIds, requestTransfert.getInventoryManager().getId(), transfertLineIds, requestTransfert.getState(), requestTransfert.getSiteDestinaion(), requestTransfert.getSiteSource());
            requestTransfertDto.setExitVoucherId(requestTransfert.getExitVoucher().getId());
            requestTransfertDto.setEntryVoucher(requestTransfert.getEntryVoucher().getId());
            return requestTransfertDto;
        } else if ((requestTransfert.getState() == 5)) {
            List<User> deliveryManList = requestTransfert.getDeliveryManList();
            deliveryManList.forEach(d -> {
                deliveryManIds.add(d.getId());
            });

            List<Long> transfertLineIds = new ArrayList<>();
            List<TransfertLine> transfertLines = new ArrayList<>();
            transfertLines = requestTransfert.getTransfertLines();
            transfertLines.forEach(d -> {
                transfertLineIds.add(d.getId());
            });
            RequestTransfertDto requestTransfertDto = new RequestTransfertDto(requestTransfert.getId(), requestTransfert.getDateCreated(), requestTransfert.getDateAccpted(), requestTransfert.getNumberPalette(), deliveryManIds, requestTransfert.getInventoryManager().getId(), transfertLineIds, requestTransfert.getState(), requestTransfert.getSiteDestinaion(), requestTransfert.getSiteSource());
            requestTransfertDto.setExitVoucherId(requestTransfert.getExitVoucher().getId());
            requestTransfertDto.setEntryVoucher(requestTransfert.getEntryVoucher().getId());
            return requestTransfertDto;
        } else return null;
    }
}
