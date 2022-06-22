package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.GroupDto;
import fr.byteCode.erp.persistance.entities.Group;

import java.util.List;

public interface IGroupService extends IGenericService<Group,Long> {
    GroupDto save(GroupDto groupDto);

    GroupDto update(GroupDto groupDto);

    GroupDto findById(Long id);

    void addRoleToUse(String userName,String roleName);

    List<GroupDto> findAllEGroupeDto();

    void delete(Long id);

    GroupDto findByPrivileged(String nom);
}
