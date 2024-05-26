package com.example.Spring.Security.config;

import com.example.Spring.Security.enums.RoleEnum;
import com.example.Spring.Security.models.Role;
import com.example.Spring.Security.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        for (RoleEnum roleEnum : RoleEnum.values()){
            if (roleRepository.findByRoleName(roleEnum.name()).isEmpty())
                roleRepository.save(
                    new Role(null, roleEnum.name())
                );
        }
    }
}
