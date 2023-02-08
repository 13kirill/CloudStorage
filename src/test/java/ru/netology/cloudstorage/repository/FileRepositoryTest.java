package ru.netology.cloudstorage.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.netology.cloudstorage.MySqlTestContainer;
import ru.netology.cloudstorage.model.DTO.StoredFileDto;
import ru.netology.cloudstorage.model.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {MySqlTestContainer.Initializer.class
})
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeAll
    public static void init(){
        MySqlTestContainer.container.start();
    }

    @Test
    void findByFilename() {
    }

    @Test
    @Sql("classpath:TestData1.sql")
    void findAllByUser() {
        User user = userRepository.findByUsername("login1").orElse(null);

        List<StoredFileDto> expected = new ArrayList<>();

        expected.add(new StoredFileDto("filename1", 10));
        expected.add(new StoredFileDto("filename2", 15));

        List<StoredFileDto> result = fileRepository.findAllByUser(user);

        assertTrue(expected.size() == result.size() && expected.containsAll(result) && result.containsAll(expected));

    }
}