package com.example.springboot_401;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CarsRepo extends CrudRepository<Cars,Long> {

    Iterable<Cars> findByCreatorID (Long creatorID);

}
