package fr.byteCode.erp.service.services.ImplService;

import fr.byteCode.erp.persistance.dao.GroupDao;
import fr.byteCode.erp.persistance.dao.UserDao;
import fr.byteCode.erp.persistance.dto.UserDto;
import fr.byteCode.erp.persistance.entities.Group;
import fr.byteCode.erp.persistance.entities.User;
import fr.byteCode.erp.persistance.util.errors.ApiErrors;
import fr.byteCode.erp.service.convertor.UserConverter;
import fr.byteCode.erp.service.services.InterfaceService.IUserService;
import fr.byteCode.erp.service.services.utils.errors.ErrorsResponse;
import fr.byteCode.erp.service.services.utils.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_CREATED;
import static fr.byteCode.erp.persistance.constants.Constants.LOG_ENTITY_UPDATED;

@Service
@Slf4j
public class UserService extends GenericService<User, Long> implements IUserService {
    private UserDao userDao;
    private GroupDao groupDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService(UserDao userDao, GroupDao groupDao,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.groupDao = groupDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findUserByName(String userName) {
        User user=userDao.findByUserName(userName);
        List<Group> groups=new ArrayList<>();
        List<Long> groupsIds=this.groupDao.getAllGroupsIdByIdUser(user.getId());
        groupsIds.forEach((id)->{
            groups.add(this.groupDao.findOne(id));
        });
        user.setGroups(groups);
        return userDao.findByUserName(userName);
    }

    @Override
    public UserDto save(UserDto userDto) {
        Objects.requireNonNull(userDto);
        userDto.setActive(false);
        User userSimple = UserConverter.dtoToModel(userDto);
        if (userDao.findByUserName(userDto.getLogin()) != null) {
            throw new RuntimeException("this user aleready exist");
        }
        String hachPW = bCryptPasswordEncoder.encode(userSimple.getPassword());
        userSimple.setPassword(hachPW);
        List<Group> groups = userDto.getGroupIds();
        userSimple.setGroups(groups);
        User savedUser = userDao.saveAndFlush(userSimple);
        log.info(LOG_ENTITY_CREATED, savedUser);
        return UserConverter.modelToDto(savedUser);


    }

    @Override
    public UserDto update(UserDto userDto) {
        Objects.requireNonNull(userDto);
        User user=userDao.findOne(userDto.getId());
        user.setGroups(userDto.getGroupIds());
        user.setDateCreated(userDto.getDateCreated());
        user.setActive(userDto.isActive());
        user.setDateNaissanced((userDto.getDateNaissanced()));
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setAdress(userDto.getAdress());
        user.setCity(userDto.getCity());
        User savedUser = userDao.saveAndFlush(user);
        log.info(LOG_ENTITY_UPDATED, savedUser);
        return UserConverter.modelToDto(savedUser);
    }

    @Override
    public UserDto findById(Long id) {
        return UserConverter.modelToDto(Optional.ofNullable(userDao.findOne(id)).orElseThrow(
                () -> new HttpCustomException(ApiErrors.ENTITY_NOT_FOUND, new ErrorsResponse().error(id))));
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> usertList = userDao.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        usertList.forEach(user -> {
            userDtos.add(UserConverter.modelToDto(user));
        });
        return userDtos;
    }

    @Override
    public List<UserDto> findAllDriversByRequest(Long id) {
        List<Long> userIdList = userDao.getAllDriverMansIdByIdRequest(id);
        List<User> usertList=new ArrayList<>();
        userIdList.forEach((data)->{
            usertList.add(this.userDao.findOne(data));
        });
        List<UserDto> userDtos = new ArrayList<>();
        usertList.forEach(user -> {
            userDtos.add(UserConverter.modelToDto(user));
        });
        return userDtos;
    }

    @Override
    public void addPrevilegeToUser(String userName, String privileged) {
        User user = userDao.findByUserName(userName);
        Group group = groupDao.findByPrivileged(privileged);
        user.getGroups().add(group);
        userDao.saveAndFlush(user);
    }

    @Override
    public List<UserDto> findUsersByPrivilege(String privilege) {
        Group group = groupDao.findByPrivileged(privilege);
        System.out.println(group.getPrivileged());
        List<Long> usersIds = new ArrayList<>();
        usersIds = userDao.getAllUsersIdByIdGroups(group.getId());
        System.out.println(usersIds.size());
        List<UserDto> userDtoList = new ArrayList<>();
        usersIds.forEach((usersId) -> {
            User user=userDao.findOne(usersId);
            if(user!=null && user.isDeleted()==false) {
                userDtoList.add(UserConverter.modelToDto(user));
            }
        });
        return userDtoList;
    }
    @Override
    public void delete(Long id){
        userDao.deleteById(id);
    }

}
