package ru.netology.cloudstorage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.exceptions.ClientException;
import ru.netology.cloudstorage.model.DTO.LoginRequestDTO;
import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(LoginRequestDTO loginRequestDTO) {
        if (userRepository.findByUsername(loginRequestDTO.getLogin()) == null) {
            throw new ClientException("Error create user account: Incorrect user name, user with such name already exists");
        }
        String encodedPassword = passwordEncoder.encode(loginRequestDTO.getPassword());
        User user = new User();
        user.setUsername(loginRequestDTO.getLogin());
        user.setPassword(encodedPassword);

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username).orElse(null);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    @Override
    public User findUserByUsername(String username) {
        User result = userRepository.findUserByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }
}