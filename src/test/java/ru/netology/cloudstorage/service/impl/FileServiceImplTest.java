package ru.netology.cloudstorage.service.impl;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import ru.netology.cloudstorage.MySqlTestContainer;
import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.security.jwt.JwtUser;

@RunWith(MockitoJUnitRunner.class)
class FileServiceImplTest {

    @InjectMocks
    FileServiceImpl fileServiceImpl;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    static User testUser;

    static JwtUser testJwtUser;

    @BeforeClass
    public static void setData() {
        testUser = User.builder()
                .username("test")
                .password("$2a$12$iHT74HfOrg2DLDwfeEqkg.4E89HY8GyyV2oCQa2YYfA.kwdT2/mJ6")
                .role("ROLE_USER")
                .build();

        testJwtUser = new JwtUser(testUser);
    }


    @Test
    void deleteFile() {
    }

    @Test
    void uploadFile() {
    }

    @Test
    void downloadFile() {
    }

    @Test
    void editFilename() {
    }

    @Test
    void getInfoAboutAllFiles() {
    }

    @Test
    public void getCurrentUser() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);
        User expectedUser = testJwtUser.getUser();
        User resultUser = fileServiceImpl.getCurrentUser();
        Assertions.assertEquals(expectedUser, resultUser);
    }
}