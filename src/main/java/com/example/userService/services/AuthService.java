package com.example.userService.services;

import com.example.userService.exceptions.UserAlreadyExistsException;
import com.example.userService.exceptions.UserNotFoundException;
import com.example.userService.exceptions.WrongPasswordException;
import com.example.userService.models.User;
import com.example.userService.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey key = Jwts.SIG.HS256.key().build();


    public AuthService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean signUp(String email, String password) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User with email: " + email + " already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return true;
    }

    public String login(String email, String password) throws UserNotFoundException, WrongPasswordException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User with email: " + email + " not found");
        }

        boolean matches = bCryptPasswordEncoder.matches(password, userOptional.get().getPassword());
        if (matches) {
            return "token";
        } else {
            throw new WrongPasswordException("Wrong Password");
        }

    }

    private String createJwtToken(Long userId, List<String> roles, String email) {
        Map<String, Object> dataInJwt = new HashMap<>();
        dataInJwt.put("user_id", userId);
        dataInJwt.put("roles", roles);
        dataInJwt.put("email", email);

        String token = Jwts.builder()
                .claims(dataInJwt) // key that is not predefined can be given with claims
                .issuedAt(new Date())
                .signWith(key)  // key already present in JWT specificatio
                .compact();

        return token;

    }
}
