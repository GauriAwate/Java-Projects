package com.example.bookstoreweb.service;

import com.example.bookstoreweb.entity.User;
import com.example.bookstoreweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
