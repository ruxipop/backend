package com.example.demo.repository;

import com.example.demo.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByUsername(String username);

    Optional<User> findUserById(Integer id);

    Optional<User> findByUsername(String username);



    @Query("SELECT user FROM users user")
    List<User> findAll();

    @Query("SELECT user FROM users user where user.role=:role")
    List<User> findAllByRole(@Param("role") String role);

}
