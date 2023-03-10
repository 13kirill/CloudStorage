package ru.netology.cloudstorage.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.repository.UserRepository;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userServiceImpl;

    @Mock
    UserRepository userRepository;

    static User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void setData() {
        testUser = User.builder()
                .username("test")
                .password("$2a$12$iHT74HfOrg2DLDwfeEqkg.4E89HY8GyyV2oCQa2YYfA.kwdT2/mJ6")
                .role("ROLE_USER")
                .build();
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));
        User resultUser = userServiceImpl.findByUsername(testUser.getUsername());
        Assertions.assertEquals(testUser, resultUser);
    }
}