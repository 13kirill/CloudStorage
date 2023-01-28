package ru.netology.cloudstorage.model.DTO;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String login;
    private String password;
}
