package ru.netology.cloudstorage.service;

import ru.netology.cloudstorage.model.DTO.LoginRequestDTO;
import ru.netology.cloudstorage.model.DTO.LoginResponseDTO;

public interface AuthenticationService {

    LoginResponseDTO login (LoginRequestDTO loginRequestDTO);

    void logout();
}
