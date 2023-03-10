package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.model.entity.User;

public interface UserService {

    User findByUsername(String userName);
}