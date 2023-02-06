package ru.netology.cloudstorage.model.DTO;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Validated
public class LoginRequestDTO {

    private String login;

    private String password;
}
