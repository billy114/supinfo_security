package com.example.Spring.Security.services;

import com.example.Spring.Security.models.Role;
import com.example.Spring.Security.models.User;

public interface AuthService {
    User register(User user, Role role);
    String login (User user, String password);
}
