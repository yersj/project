package com.cisco.cisco.services;

import com.cisco.cisco.entities.User;

import java.util.List;

public interface UserService {
    public User getUser(Long id);
    public void saveUser(User user);
    public User register(User user);
    public User findByEmail(String email);
    List<User> getAllStudentsByCourseId(Long id);
}
