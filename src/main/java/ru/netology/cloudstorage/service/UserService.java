package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.model.DTO.LoginRequestDTO;
import ru.netology.cloudstorage.model.entity.User;

public interface UserService {

    User register(LoginRequestDTO loginRequestDTO);

    User findByUsername(String userName);

    User findUserByUsername(String username);
}