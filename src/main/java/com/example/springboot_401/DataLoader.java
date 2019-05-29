package com.example.springboot_401;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public void run(String ... strings) throws Exception{
        Role admin = new Role();
        admin.setRole("ADMIN");

        Role user = new Role();
        user.setRole("USER");

        roleRepo.save(admin);
        roleRepo.save(user);

        Role adminRole = roleRepo.findByRole("ADMIN");
        Role userRole = roleRepo.findByRole("USER");

        User user1 = new User();
        user1.setEmail("Valle@Valle,com");
        user1.setEnabled(true);
        user1.setFirstName("Anthony");
        user1.setLastName("Valle");
        user1.setPassword("p");
        user1.setRoles(Arrays.asList(adminRole));
        user1.setUsername("valleant");
        userRepo.save(user1);

        User user2 = new User();
        user2.setUsername("user");
        user2.setRoles(Arrays.asList(userRole));
        user2.setPassword("password");
        user2.setFirstName("user");
        user2.setLastName("user");
        user2.setEmail("user@user.com");
        user2.setEnabled(true);
        userRepo.save(user2);

    }

}
