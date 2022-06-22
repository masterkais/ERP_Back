package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.RequestTransfertDto;
import fr.byteCode.erp.persistance.entities.RequestTransfert;

import java.util.List;

public interface IRequestTransfertService extends IGenericService<RequestTransfert,Long> {
    RequestTransfertDto deliverOrder(Long requestTransfertId, List<Long> delivertManIds, List<Long> vehiculeIds) throws Exception;

    RequestTransfertDto submitRequestTransfert(Long requestTransfertId) throws Exception;

    RequestTransfertDto update(RequestTransfertDto requestTransfertDto);

    RequestTransfertDto findById(Long id);

    List<RequestTransfertDto> findAllRequestTransferts();

    void delete(Long id);

    List<RequestTransfertDto> findAllRequestTransfertsByIdUser(Long idUser);

    RequestTransfertDto createNewRequestTransfertEmpty(Long inventaryManagerId, String siteDestination, String siteSource);

    RequestTransfertDto acceptDeliverOrder(Long requestTransfertId);

    float getMantantRequest(Long requestId);
}
