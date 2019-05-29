package com.example.springboot_401;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService{
    private UserRepo userRepo;
    public SSUserDetailsService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

   @Override
    public UserDetails loadUserByUsername(String username) throws
           UsernameNotFoundException{
        try{
           User appUser = userRepo.findByUsername(username);
            if (appUser== null){
                System.out.println("User Not found with the provided username "+ appUser.toString());
                return null;
            }
            System.out.println("User from username " + appUser.getUsername().toString());
            return new CustomUserDetails(appUser,getAuthorities(appUser));

        }
        catch (Exception e){
            throw new UsernameNotFoundException("User not Found");
        }

   }

   private Set<GrantedAuthority> getAuthorities(User appUser){
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role:appUser.getRoles()){
            GrantedAuthority grantedAuthority =
            new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
       System.out.println("User authorities are "+ authorities.toString());
        return authorities;
   }



}
