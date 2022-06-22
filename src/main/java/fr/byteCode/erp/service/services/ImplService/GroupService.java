package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.GroupDao;
import fr.byteCode.erp.persistance.dao.UserDao;
import fr.byteCode.erp.persistance.dto.GroupDto;
import fr.byteCode.erp.persistance.entities.Group;
import fr.byteCode.erp.persistance.entities.User;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.GroupeConverter;
import fr.byteCode.erp.service.services.InterfaceService.IGroupService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GroupService extends GenericService<Group, Long> implements IGroupService {
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;

    @Override
    public void addRoleToUse(String userName, String roleName) {
        Group group = groupDao.findByPrivileged(roleName);
        User user = userDao.findByUserName(userName);
        user.getGroups().add(group);
        userDao.save(user);
    }

    @Override
    public GroupDto save(GroupDto groupDto) {
        Objects.requireNonNull(groupDto);
        if (groupDao.findByPrivileged(groupDto.getPrivileged()) != null) {
            throw new HttpCustomException(ApiErrors.OBJECT_EXISTS, new ErrorsResponse().error(groupDto.getPrivileged()));
        }
        Group groupeSaved = groupDao.saveAndFlush(GroupeConverter.dtoToModel(groupDto));
        return GroupeConverter.modelToDto(groupeSaved);
    }

    @Override
    public GroupDto update(GroupDto groupDto) {
        Objects.requireNonNull(groupDto);
        if (groupDao.findByPrivileged(groupDto.getPrivileged()) != null) {
            throw new HttpCustomException(ApiErrors.OBJECT_EXISTS, new ErrorsResponse().error(groupDto.getPrivileged()));
        }
        Group groupeSaved = groupDao.saveAndFlush(GroupeConverter.dtoToModel(groupDto));
        return GroupeConverter.modelToDto(groupeSaved);
    }

    @Override
    public GroupDto findById(Long id) {
        return GroupeConverter.modelToDto(Optional.ofNullable(groupDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));

    }

    @Override
    public List<GroupDto> findAllEGroupeDto() {
        List<Group> groups = groupDao.findAll();
        List<GroupDto> groupDtos = new ArrayList<>();
        groups.forEach(group -> {
            groupDtos.add(GroupeConverter.modelToDto(group));
        });
        return groupDtos;
    }

    @Override
    public GroupDto findByPrivileged(String nom) {
        return GroupeConverter.modelToDto(Optional.ofNullable(groupDao.findByPrivileged(nom)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(nom))));

    }

    public List<GroupDto> findAllGroupByUserId(Long idUser) {
        List<Long> groupIds = this.groupDao.getAllGroupsIdByIdUser(idUser);
        List<GroupDto> groupDtos = new ArrayList<>();
        groupIds.forEach((idGroup) -> {
            Group group = this.groupDao.findOne(idGroup);
            groupDtos.add(GroupeConverter.modelToDto(group));
        });
        return groupDtos;
    }
}
