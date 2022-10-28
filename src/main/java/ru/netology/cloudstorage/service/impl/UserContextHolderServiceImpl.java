package ru.netology.cloudstorage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.repository.UserRepository;
import ru.netology.cloudstorage.service.UserContextHolderService;

@RequiredArgsConstructor
@Service
public class UserContextHolderServiceImpl implements UserContextHolderService {

    private final UserRepository userRepository;
    @Override
    public User getCurrentUserContext() {
        return userRepository.findByName("test").orElseGet(() -> {
            User user = new User();
            user.setName("test");
            user.setPassword("test");
            return userRepository.save(user);
        });
    }
}
