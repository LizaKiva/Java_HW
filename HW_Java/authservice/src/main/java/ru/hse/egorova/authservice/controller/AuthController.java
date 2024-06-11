package ru.hse.egorova.authservice.controller;

import ru.hse.egorova.authservice.model.Session;
import ru.hse.egorova.authservice.model.User;
import ru.hse.egorova.authservice.service.UserService;
import ru.hse.egorova.authservice.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Pattern emailPattern
            = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    private static final Pattern passwordPattern
            = Pattern.compile("^(?=.*[a-z])(?=.*[0-9])(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$");

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ) {
        if (!emailPattern.matcher(email).matches()) {
            return ResponseEntity.status(402).body("Invalid email");
        }

        if (!passwordPattern.matcher(password).matches()) {
            return ResponseEntity.status(402).body(
                "Invalid password. Password must contain at least one lowercase, " +
                "one uppercase, one number, one special character and be at least 8 symbols"
            );
        }

        if (userService.findByEmail(email) != null) {
            return ResponseEntity.status(401).body("Email already in use");
        }
        if (userService.findByUsername(username) != null) {
            return ResponseEntity.status(401).body("Username already in use");
        }

        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        user.setEmail(email);

        userService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        User user = userService.findByUsername(username);
        if (user == null || !tokenService.verifyPassword(password, user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        String token = tokenService.generateToken(user);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/myId")
    public ResponseEntity<?> getId(
            @RequestParam String token
    ) {
        Long id = getIdImpl(token);
        if (id == -1) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        return ResponseEntity.ok(id);
    }

    @GetMapping("/listUsers")
    public ResponseEntity<?> findAll(
    ) {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/myAccount")
    public ResponseEntity<?> getAccount(
            @RequestParam String token
    ) {
        Date date = new Date();
        Session session = tokenService.findByToken(token);
        if (session == null || session.getExpires().getTime() < date.getTime()) {
            return ResponseEntity.status(401).body("Invalid token");
        }

        return ResponseEntity.ok(userService.findByUid(session.getUserId()));
    }

    @GetMapping("/myIdImpl")
    public Long getIdImpl(
            @RequestParam String token
    ) {
        Date date = new Date();
        Session session = tokenService.findByToken(token);
        if (session == null || session.getExpires().getTime() < date.getTime()) {
            return (long) -1;
        }

        return session.getUserId();
    }
}
