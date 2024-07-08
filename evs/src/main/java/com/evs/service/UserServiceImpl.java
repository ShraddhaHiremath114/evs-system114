package com.evs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.evs.dao.UserDao;
import com.evs.entity.User;
import com.evs.helper.AppConstants;
import com.evs.helper.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoleList(List.of(AppConstants.ROLE_USER));
       return userDao.save(user);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User u = userDao.findById(user.getId()).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));
        u.setName(user.getName());
        u.setEmail(user.getEmail());
        u.setPassword(user.getPassword());
        u.setPhone(user.getPhone());
        u.setProfilePic(user.getProfilePic());
        u.setEmailVarified(user.isEmailVarified());
        u.setEnabled(user.isEnabled());
        u.setPhoneVarified(user.isPhoneVarified());
        u.setProvider(user.getProvider());
        u.setProviderUserId(user.getProviderUserId());
        User save=userDao.save(u);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(int id) {
        userDao.deleteById(id);
    }

    @Override
    public boolean isUserExist(int id) {
        User u = userDao.findById(id).orElse(null);   
        return u != null ? true : false;
    }

    

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

}
