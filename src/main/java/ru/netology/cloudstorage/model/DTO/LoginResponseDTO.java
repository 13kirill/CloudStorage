package ru.netology.cloudstorage.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    @JsonProperty(value = "auth-token")
    private String authToken;
}
