package fr.byteCode.erp.service.restController;

import fr.byteCode.erp.persistance.dto.UserDto;
import fr.byteCode.erp.persistance.entities.User;
import fr.byteCode.erp.service.convertor.UserConverter;
import fr.byteCode.erp.service.services.InterfaceService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserController(IUserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/users")
    public List<UserDto> findAll() {

        return userService.findAllUsers();
    }
    @GetMapping(value = "/drivers/{id}")
    public List<UserDto> findAllDriversByRequest(@PathVariable Long id) {

        return userService.findAllDriversByRequest(id);
    }

    @GetMapping(value = "/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping(value = "/getUsersByPrivilege/{privilege}")
    public List<UserDto> getAllUsersByPriviliege(@PathVariable String privilege) {
        return userService.findUsersByPrivilege(privilege);
    }

    @PostMapping("")
    public UserDto save(@RequestBody @Valid UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping()
    public UserDto update(@RequestBody @Valid UserDto userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        userService.delete(id);
        return true;
    }

    @PostMapping(value = "/verifierPassword/{password}")
    public boolean verifierPassword(@PathVariable String password, @RequestBody UserDto userDto) {
        UserDto userDtoFound = this.userService.findById(userDto.getId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatches = bCryptPasswordEncoder.matches(password, userDtoFound.getPassword());
        if (isPasswordMatches) {
            return true;
        } else return false;
    }

    @GetMapping("/getUserActive")
    List<UserDto> getAllUserActive() {
        List<UserDto> listUsers = userService.findAllUsers();
        List<UserDto> resultUsers = new ArrayList<>();
        listUsers.forEach(userDto -> {
            if (userDto.isActive() == true) {
                resultUsers.add(userDto);
            }
        });
        return resultUsers;
    }

    @GetMapping("/getUserNoActive")
    List<UserDto> getAllUserNoActive() {
        List<UserDto> listUsers = userService.findAllUsers();
        List<UserDto> resultUsers = new ArrayList<>();
        listUsers.forEach(userDto -> {
            if (userDto.isActive() == false) {
                resultUsers.add(userDto);
            }
        });
        return resultUsers;
    }

    @GetMapping("/findUserByUserName/{userName}")
    UserDto findUserByUserName(@PathVariable String userName) {
        User user = userService.findUserByName(userName);
        if (user != null) {
            UserDto userDto = UserConverter.modelToDto(user);
            return userDto;
        } else {
            return null;
        }
    }

    @GetMapping("/existUserName/{userName}")
    boolean existUserName(@PathVariable String userName) {
        User user = userService.findUserByName(userName);
        int counter = 0;
        List<UserDto> userDtoList = this.userService.findAllUsers();
        for(int i=0;i<userDtoList.size();i++) {
            if (userDtoList.get(i).getLogin().equals(userName) && userDtoList.get(i).getId() == user.getId()) {
                counter++;
            }
        }
        if (counter == 1) {

            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/getCurretnUser")
    public UserDto getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        User user;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            System.out.println("***" + username);
            user = this.userService.findUserByName(username);
        } else {
            username = principal.toString();
            user = this.userService.findUserByName(username);
        }

        return UserConverter.modelToDto(user);
    }
}
