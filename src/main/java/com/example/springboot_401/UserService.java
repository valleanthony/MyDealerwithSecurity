package com.example.springboot_401;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {


    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    // Returns the currently logged in User object
    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepo.findByUsername(currentusername);
        return user;
    }

    public void saveUser(User user) {
        user.setRoles(Arrays.asList(roleRepo.findByRole("USER")));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void saveAdmin(User user) {
        user.setRoles(Arrays.asList(roleRepo.findByRole("ADMIN")));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void saveSalesRep(User user){
        user.setRoles(Arrays.asList(roleRepo.findByRole("SalesRep")));
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

}