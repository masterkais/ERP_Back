package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.GroupDto;
import fr.byteCode.erp.persistance.entities.Group;

public class GroupeConverter {
    private GroupeConverter() {
        super();
    }

    public static Group dtoToModel(GroupDto groupDto) {
        return new Group(groupDto.getId(), groupDto.getPrivileged());
    }

    public static GroupDto modelToDto(Group group) {
        return new GroupDto(group.getId(), group.getPrivileged());
    }


}
