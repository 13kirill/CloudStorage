package ru.netology.cloudstorage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.cloudstorage.model.DTO.LoginRequestDTO;
import ru.netology.cloudstorage.model.DTO.LoginResponseDTO;
import ru.netology.cloudstorage.service.AuthenticationService;
import ru.netology.cloudstorage.service.UserService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

//    private final UserService userService;
//
//    @Autowired
//    public AuthenticationController(UserService userService) {
//        this.userService = userService;
//    }

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){


        return null;
    }
}