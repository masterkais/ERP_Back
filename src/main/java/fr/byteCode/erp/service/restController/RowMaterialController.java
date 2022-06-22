package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.RowMaterialDto;
import fr.byteCode.erp.service.services.ImplService.RowMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rowMaterial")
public class RowMaterialController {
    private final RowMaterialService rowMaterialService;

    @Autowired
    public RowMaterialController(RowMaterialService rowMaterialService) {
        this.rowMaterialService = rowMaterialService;
    }
    @GetMapping(value = "/rowMaterials")
    public List<RowMaterialDto> findAll() {
        return rowMaterialService.findAllRowMaterials();
    }
    @GetMapping(value = "/{id}")
    public RowMaterialDto getRowMaterial(@PathVariable Long id) {
        return rowMaterialService.findById(id);
    }
    @PostMapping()
    public RowMaterialDto save(@RequestBody @Valid RowMaterialDto rowMaterialDto) {
        return rowMaterialService.save(rowMaterialDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        rowMaterialService.delete(id);
        return true;
    }
}
