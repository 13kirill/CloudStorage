package ru.netology.cloudstorage.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.netology.cloudstorage.MySqlTestContainer;
import ru.netology.cloudstorage.model.entity.User;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {MySqlTestContainer.Initializer.class})
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    public static void start() {
        MySqlTestContainer.container.start();
    }

    @Test
    @Sql("classpath:TestData5.sql")
    void findByUsernameSuccess() {
        User expectedUser = User.builder()
                .id(1)
                .username("username1")
                .password("password1")
                .role("ROLE_USER")
                .build();

        User resultUser = userRepository.findByUsername("username1").orElse(null);

        assertThat(resultUser).usingRecursiveComparison().ignoringFields("id", "storedFiles").isEqualTo(expectedUser);
    }

    @Test
    @Sql("classpath:TestData2.sql")
    void findByUsernameNotFound() {

        User resultUser = userRepository.findByUsername("username1").orElse(null);

        assertEquals(null, resultUser);
    }
}