package com.example.Spring.Security.implem;

import com.example.Spring.Security.models.Role;
import com.example.Spring.Security.models.User;
import com.example.Spring.Security.repositories.UserRepository;
import com.example.Spring.Security.security.JwtService;
import com.example.Spring.Security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthImplem implements AuthService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @Override
    public User register(User user,Role role) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.getRoles().add(role);
        return userRepository.save(user);
    }

    @Override
    public String login(User user, String password) {
       if (!passwordEncoder.matches(password, user.getPassword()))
           return null;
       return jwtService.generateToken(user);
    }
}
