package fr.byteCode.erp.service.convertor;

import fr.byteCode.erp.persistance.dto.UserDto;
import fr.byteCode.erp.persistance.entities.Group;
import fr.byteCode.erp.persistance.entities.User;

import java.util.ArrayList;
import java.util.List;

public final class UserConverter {
    private UserConverter(){
        super();
    }
    public static User dtoToModel(UserDto userDto){
        return new User(userDto.getId(),userDto.getLastName(),userDto.getFirstName(), userDto.getAdress(), userDto.getFax(), userDto.getEmail(), userDto.getCity(), userDto.getPicture(), userDto.isActive(), userDto.getDateNaissanced(), userDto.getDateCreated(),userDto.getLogin(),userDto.getPassword());
    }
    public static UserDto modelToDto(User user){
        List<Long> groupsId=new ArrayList<>();
        List<Group> groups=new ArrayList<>();
        if(user.getGroups()!=null) {
            groups=user.getGroups();
            groups.forEach(g -> {
                groupsId.add(g.getId());
            });
        }
        return new UserDto(user.getId(),user.getLastName(),user.getFirstName(), user.getAdress(), user.getFax(), user.getEmail(), user.getCity(), user.getPicture(), user.isActive(), user.getDateNaissanced(), user.getDateCreated(),user.getUserName(),user.getPassword(),user.getGroups());

    }
}
