package com.cisco.cisco.services;

import com.cisco.cisco.entities.User;

public interface UserService {
    public User getUser(Long id);
    public void saveUser(User user);
    public User register(User user);
    public User findByEmail(String email);
}
