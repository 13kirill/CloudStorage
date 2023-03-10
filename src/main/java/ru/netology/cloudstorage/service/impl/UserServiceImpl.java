package ru.netology.cloudstorage.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.service.UserService;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username).orElse(null);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }
}