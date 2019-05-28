package com.example.springboot_401;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository <Role,Long> {
    Role findByRole(String role);
}
