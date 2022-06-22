package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.RequestTransfertDto;
import fr.byteCode.erp.service.services.InterfaceService.IRequestTransfertService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/requestTransfert")
public class RequestTransfertController {
    private final IRequestTransfertService requestTransfertService;
    @Autowired
    private RequestTransfertController(IRequestTransfertService requestTransfertService) {
        this.requestTransfertService = requestTransfertService;
    }

    @GetMapping(value = "/requestTransfers")
    public List<RequestTransfertDto> findAll() {
        return requestTransfertService.findAllRequestTransferts();
    }
    @GetMapping(value = "/requestTransfers/{idUser}")
    public List<RequestTransfertDto> findAllByUser(@PathVariable Long idUser) {
        return requestTransfertService.findAllRequestTransfertsByIdUser(idUser);
    }

    @GetMapping(value = "/{id}")
    public RequestTransfertDto getReqyestTransfert(@PathVariable Long id) {
        return requestTransfertService.findById(id);
    }

    @PostMapping("/submitRequestTransfert/{requestTransfertId}")
    public RequestTransfertDto submitRequestTransfert(@PathVariable Long requestTransfertId) throws Exception {
        return requestTransfertService.submitRequestTransfert(requestTransfertId);
    }

    @PutMapping()
    public RequestTransfertDto update(@RequestBody @Valid RequestTransfertDto requestTransfertDto) {
        return requestTransfertService.update(requestTransfertDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        requestTransfertService.delete(id);
        return true;
    }

  /*  @ApiOperation(value = "Création une nouvelle commande vide", notes = "cette methode permet de creer une nouvelle commande vide et apres on peut ajouter des lignes de commande", response = RequestTransfertDto.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "l'objet a etait creer avec succe"),
            @ApiResponse(code = 400, message = "l'objet  n'est pas valide")})*/
    @PostMapping("/createNewEmptyRequestTransfert")
    public RequestTransfertDto createNewEmptyRequestTransfert(@RequestParam Long inventaryManagerId,@RequestParam String siteDestinationName,@RequestParam String siteSourceName) {
        return requestTransfertService.createNewRequestTransfertEmpty(inventaryManagerId,siteDestinationName,siteSourceName);
    }

    /*@ApiOperation(value = "livrer une commande ", notes = "cette methode permet de livrer une commande de tel facon que la commande status va passer de l'etat en attente (etat=1) vers l'etat(2)  et un enxitVoucher a était créer", response = RequestTransfertDto.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "l'objet a etait creer avec succe"),
            @ApiResponse(code = 400, message = "l'objet  n'est pas valide")})*/
    @PostMapping("/deliverOrder")
    public RequestTransfertDto deliverOrder(@RequestParam Long requestTransfertId,@RequestParam List<Long> delivertManIds,@RequestParam List<Long> vehiculeIds) throws Exception {
        return requestTransfertService.deliverOrder(requestTransfertId,delivertManIds,vehiculeIds);
    }

   /* @ApiOperation(value = "livrer une commande ", notes = "cette methode permet d'accepter une commande de tel facon que la commande status va passer de l'etat commande valid (etat=2) vers l'etat(3) commande accepée  et un entryVoucher a était créer", response = RequestTransfertDto.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "l'objet a etait accepter"),
            @ApiResponse(code = 400, message = "l'objet  n'est pas valide")})*/
    @PostMapping("/acceptDeliverOrder")
    public RequestTransfertDto acceptDeliverOrder(@RequestParam Long requestTransfertId) throws Exception {
        return requestTransfertService.acceptDeliverOrder(requestTransfertId);
    }
    @GetMapping(value = "/montantTotal/{id}")
    public float getMontantRequest(@PathVariable Long id){
        return requestTransfertService.getMantantRequest(id);
    }

}
