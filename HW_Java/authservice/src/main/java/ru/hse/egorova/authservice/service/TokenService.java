package ru.hse.egorova.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.hse.egorova.authservice.model.Session;
import ru.hse.egorova.authservice.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.hse.egorova.authservice.repository.SessionRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TokenService {
    private static final String jwtSecret = "my_super_secret_key_228";
    private static final long jwtExpiration = 3600000;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private SessionRepository sessionRepository;

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);

        String token = Jwts.builder()
                      .setSubject(Long.toString(user.getId()))
                      .setIssuedAt(now)
                      .setExpiration(expiryDate)
                      .signWith(SignatureAlgorithm.HS512, jwtSecret)
                      .compact();

        Session session = new Session();
        session.setToken(token);
        session.setExpires(expiryDate);
        session.setUserId(user.getId());

        sessionRepository.save(session);

        return token;
    }

    public Session findByToken(String token) {
        List<Session> sessions = sessionRepository.findAll();
        for (Session session : sessions) {
            if (Objects.equals(session.getToken(), token)) {
                return session;
            }
        }

        return null;
    }
}

