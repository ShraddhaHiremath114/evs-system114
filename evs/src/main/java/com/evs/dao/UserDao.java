package com.evs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.evs.entity.User;
import java.util.List;



public interface UserDao extends JpaRepository<User,Integer>{

    Optional<User>  findByEmail(String email);
    Optional<User>  findById(int id);
    
}
