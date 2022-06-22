package fr.byteCode.erp;

import fr.byteCode.erp.persistance.dao.GroupDao;
import fr.byteCode.erp.persistance.dao.UserDao;
import fr.byteCode.erp.persistance.entities.Group;
import fr.byteCode.erp.persistance.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableHystrix
public class MsGestionMagasinApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MsGestionMagasinApplication.class, args);
    }
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Bean
    public BCryptPasswordEncoder getBCPE() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void run(String... args) throws Exception {
       /* Group users = new Group(1L, "USER");
        Group admins = new Group(2L, "ADMIN");
        groupDao.saveAndFlush(users);
        groupDao.saveAndFlush(admins);
        User admin = new User();
        admin.setPassword("admin");
        admin.setFirstName("admin");
        admin.setUserName("admin");
        admin.setPassword(getBCPE().encode(admin.getPassword()));
        List<Group> admingroups = new ArrayList<>();
        admingroups.add(admins);
        admingroups.add(users);
        admin.setGroups(admingroups);
        userDao.saveAndFlush(admin);
        User user = new User();
        user.setPassword("user");
        user.setFirstName("user");
        user.setUserName("user");
        user.setPassword(getBCPE().encode(user.getPassword()));
        List<Group> userGroups = new ArrayList<>();
        userGroups.add(users);
        user.setGroups(userGroups);
        userDao.saveAndFlush(user);*/
    }
}
