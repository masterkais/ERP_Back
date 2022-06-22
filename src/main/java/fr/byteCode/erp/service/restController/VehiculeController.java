package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.VehiculeDto;
import fr.byteCode.erp.service.services.InterfaceService.IVehiculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/vehicule")
public class VehiculeController {
    private final IVehiculeService vehiculeService;

    @Autowired
    private VehiculeController(IVehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @GetMapping(value = "/vehicules")
    public List<VehiculeDto> findAll() {
        return vehiculeService.findAllVehicules();
    }

    @GetMapping(value = "/vehicules/{state}")
    public List<VehiculeDto> getAllListVehiculeByState(@PathVariable boolean state) {
        return vehiculeService.getListVehiculeByState(state);
    }
    @GetMapping(value = "/driverId/{id}")
    public VehiculeDto getVehiculeByDriverId(@PathVariable Long id) {
        return vehiculeService.getVehiculeByDriverId(id);
    }
    @GetMapping(value = "/{id}")
    public VehiculeDto getVehicule(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @PostMapping()
    public VehiculeDto save(@RequestBody @Valid VehiculeDto vehiculeDto) {
        return vehiculeService.save(vehiculeDto);
    }
    @PostMapping("/reserveVehiculeToDeliveryMan")
    public VehiculeDto reserveVehiculeToDeliveryMan(@RequestParam Long vehiculeId,@RequestParam Long deliveryManId) {
        return vehiculeService.reserveVehicule(vehiculeId,deliveryManId);
    }

    @PutMapping()
    public VehiculeDto update(@RequestBody @Valid VehiculeDto vehiculeDto) {
        return vehiculeService.update(vehiculeDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        vehiculeService.delete(id);
        return true;
    }
}
