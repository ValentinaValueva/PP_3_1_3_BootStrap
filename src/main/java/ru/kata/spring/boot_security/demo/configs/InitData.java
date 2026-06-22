package ru.kata.spring.boot_security.demo.configs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleRepository;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Set;

@Component
public class InitData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public InitData(UserRepository userRepository,
                    RoleRepository roleRepository,
                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Role adminRole = roleRepository.save(new Role("ROLE_ADMIN"));
        Role userRole = roleRepository.save(new Role("ROLE_USER"));

        User admin = new User();
        admin.setName("admin");
        admin.setEmail("admin");
        admin.setAge(30);
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(Set.of(adminRole, userRole));

        User user = new User();
        user.setName("user");
        user.setEmail("user");
        user.setAge(20);
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(Set.of(userRole));

        userRepository.save(admin);
        userRepository.save(user);
    }
}