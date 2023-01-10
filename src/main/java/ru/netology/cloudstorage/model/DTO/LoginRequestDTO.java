package ru.netology.cloudstorage.model.DTO;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
public class LoginRequestDTO {

    private String login;

    private String password;
}
