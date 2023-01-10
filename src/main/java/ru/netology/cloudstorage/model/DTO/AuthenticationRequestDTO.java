package ru.netology.cloudstorage.model.DTO;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
