package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.model.DTO.LoginRequestDTO;
import ru.netology.cloudstorage.model.entity.User.User;

import java.util.List;

public interface UserService {

//    User register(User user);

    User register(LoginRequestDTO loginRequestDTO);

    List<User> getAll();

    User findByUserName(String userName);

    User findById(Long id);

    void delete(Long id);


}
