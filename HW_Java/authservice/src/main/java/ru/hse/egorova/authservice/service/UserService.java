package ru.hse.egorova.authservice.service;

import ru.hse.egorova.authservice.model.User;
import ru.hse.egorova.authservice.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                return user;
            }
        }

        return null;
    }

    public User findByUsername(String username) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username)) {
                return user;
            }
        }

        return null;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUid(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

