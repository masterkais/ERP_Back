package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.ReceivedDto;
import fr.byteCode.erp.service.services.ImplService.ReceivedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/receive")
public class ReceivedController {
    private final ReceivedService receivedService;
    @Autowired
    public ReceivedController(ReceivedService receivedService){
        this.receivedService=receivedService;
    }
    @GetMapping(value = "/")
    public List<ReceivedDto> findAll() {
        return receivedService.findAllRecived();
    }
    @GetMapping(value = "/{id}")
    public ReceivedDto getReceived(@PathVariable Long id) {
        return receivedService.findById(id);
    }



    @PostMapping()
    public ReceivedDto save(@RequestBody @Valid ReceivedDto receivedDto) {
        return receivedService.save(receivedDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        receivedService.delete(id);
        return true;
    }

}
