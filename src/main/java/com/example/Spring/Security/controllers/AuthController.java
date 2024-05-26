package com.example.Spring.Security.controllers;

import com.example.Spring.Security.enums.RoleEnum;
import com.example.Spring.Security.models.Role;
import com.example.Spring.Security.models.User;
import com.example.Spring.Security.repositories.RoleRepository;
import com.example.Spring.Security.repositories.UserRepository;
import com.example.Spring.Security.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("auth")
public class AuthController {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    @PostMapping("register-student")
    public ResponseEntity<?> registerStudent(@RequestBody User entity){
        Optional<User> user = userRepository.findByEmail(entity.getEmail());
        if (user.isPresent())
            return new ResponseEntity<>(
                    "Email exist",
                    HttpStatus.CONFLICT
            );

        Optional<Role> studentRole = roleRepository.findByRoleName(RoleEnum.STUDENT.name());
        if (studentRole.isEmpty())
            return new ResponseEntity<>(
                    "Une erreur est survenue",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );

        return new ResponseEntity<>(
                authService.register(entity, studentRole.get()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("register-prof")
    public ResponseEntity<?> registerProf(@RequestBody User entity){
        Optional<User> user = userRepository.findByEmail(entity.getEmail());
        if (user.isPresent())
            return new ResponseEntity<>(
                    "Email exist",
                    HttpStatus.CONFLICT
            );

        Optional<Role> profRole = roleRepository.findByRoleName(RoleEnum.PROF.name());
        if (profRole.isEmpty())
            return new ResponseEntity<>(
                    "Une erreur est survenue",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        return new ResponseEntity<>(
                authService.register(entity, profRole.get()),
                HttpStatus.CREATED
        );
    }

    @PostMapping("login")
    public ResponseEntity<?> login (@RequestBody Map<String, String> request){
        String email = request.get("email");
        String password = request.get("password");
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty())
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );

        String jwt = authService.login(user.get(),password);
        if (jwt == null)
            return new ResponseEntity<>(
                "Password incorrect",
                HttpStatus.FORBIDDEN
        );

        return new ResponseEntity<>(
                jwt,
                HttpStatus.OK
        );
    }
}
