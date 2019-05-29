package com.example.springboot_401;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Long> {
    User findByUsername(String username);
    Long countbyEmail(String email);
    Long countbyUsername(String username);
}
