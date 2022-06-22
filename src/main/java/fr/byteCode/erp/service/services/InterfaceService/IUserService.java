package fr.byteCode.erp.service.services.InterfaceService;

import fr.byteCode.erp.persistance.dto.UserDto;
import fr.byteCode.erp.persistance.entities.User;

import java.util.List;

public interface IUserService extends IGenericService<User, Long> {
    UserDto save(UserDto UserDto);

    UserDto update(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAllUsers();

    void delete(Long id);

    User findUserByName(String userName);

    List<UserDto> findAllDriversByRequest(Long id);

    void addPrevilegeToUser(String userName, String privileged);

    List<UserDto> findUsersByPrivilege(String privilege);
}
