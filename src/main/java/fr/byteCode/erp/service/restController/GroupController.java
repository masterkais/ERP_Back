package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.GroupDto;
import fr.byteCode.erp.service.services.InterfaceService.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/group")
public class GroupController {
    private final IGroupService groupService;
    @Autowired
    public GroupController(IGroupService groupService) {
        this.groupService = groupService;
    }
    @GetMapping(value = "/groups")
    public List<GroupDto> findAll() {
        return groupService.findAllEGroupeDto();
    }
    @GetMapping(value = "/{id}")
    public GroupDto getGroup(@PathVariable Long id) {
        return groupService.findById(id);
    }
    @GetMapping(value = "/search/{privileged}")
    public List<GroupDto> getGroupByPrivileged(@PathVariable String privileged) {
        List<GroupDto> groupDtos=new ArrayList<>();
        groupDtos.add(groupService.findByPrivileged(privileged));
        return groupDtos;
    }
    @PostMapping()
    public GroupDto save(@RequestBody @Valid GroupDto groupDto) {
        return groupService.save(groupDto);
    }
    @PutMapping()
    public GroupDto update(@RequestBody @Valid GroupDto groupDto) {
        return groupService.update(groupDto);
    }
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        groupService.delete(id);
        return true;
    }

}
