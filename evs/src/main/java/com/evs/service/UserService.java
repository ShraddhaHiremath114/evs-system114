package com.evs.service;

import java.util.List;
import java.util.Optional;

import com.evs.entity.User;

public interface UserService {
User saveUser(User user);

Optional<User> getUserById(int id);

Optional<User> updateUser(User user);

void deleteUser(int id);

boolean isUserExist(int id);



List<User> getAllUsers();


    
} 
