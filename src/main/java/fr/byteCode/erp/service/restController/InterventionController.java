package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.InterventionDto;
import fr.byteCode.erp.service.services.InterfaceService.IInterventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/intervention")
public class InterventionController {
    private final IInterventionService interventionService;
    @Autowired
    public InterventionController(IInterventionService interventionService){
        this.interventionService=interventionService;
    }
    @GetMapping("/interventions")
    public List<InterventionDto> getAllIntervention(){
        return this.interventionService.findALL();
    }

    @PostMapping("")
    public InterventionDto saveIntervention(@RequestBody InterventionDto interventionDto){
        return this.interventionService.save(interventionDto);
    }
    @PutMapping("")
    public InterventionDto updateIntervention(@RequestBody InterventionDto interventionDto){
        return this.interventionService.update(interventionDto);
    }
    @GetMapping("/{id}")
    public InterventionDto getInterventionById(@PathVariable Long id){
        return  this.interventionService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteInterventionById(@PathVariable Long id){
        this.interventionService.delete(id);
        return true;
    }
}
