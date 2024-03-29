package ru.netology.cloudstorage.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import ru.netology.cloudstorage.MySqlTestContainer;
import ru.netology.cloudstorage.model.DTO.StoredFileDto;
import ru.netology.cloudstorage.model.entity.StoredFile;
import ru.netology.cloudstorage.model.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {MySqlTestContainer.Initializer.class})
class FileRepositoryTest {

    @Autowired
    FileRepository fileRepository;
    @Autowired
    UserRepository userRepository;

    @BeforeAll
    public static void start() {
        MySqlTestContainer.container.start();
    }

    @AfterEach
    public void deleteData(){
        fileRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Sql("classpath:TestData1.sql")
    void findByFilenameSuccess() {
        User user = userRepository.findByUsername("username1").orElse(null);
        StoredFile expectedStoredFile = StoredFile.builder()
                .filename("filename1")
                .size(10)
                .hash("hash1")
                .fileUUID("uuid1")
                .user(user)
                .build();

        StoredFile resultStoredFile = fileRepository.findByFilename("filename1").orElse(null);

        assertEquals(expectedStoredFile.getFilename(), resultStoredFile.getFilename());
    }

    @Test
    @Sql("classpath:TestData2.sql")
    void findByFilenameNotFound() {

        StoredFile resultStoredFile = fileRepository.findByFilename("filename").orElse(null);

        assertEquals(null, resultStoredFile);
    }

    @Test
    @Sql("classpath:TestData3.sql")
    void findAllByUserSuccess() {
        User user = userRepository.findByUsername("username3").orElse(null);

        List<StoredFileDto> expected = new ArrayList<>();

        expected.add(new StoredFileDto("filename3", 10));
        expected.add(new StoredFileDto("filename33", 15));

        List<StoredFileDto> result = fileRepository.findAllByUser(user);

        assertTrue(expected.size() == result.size() && expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    @Sql("classpath:TestData4.sql")
    void findAllByUserNotFound() {
        User user = userRepository.findByUsername("username").orElse(null);

        List<StoredFileDto> result = fileRepository.findAllByUser(user);

        assertEquals(0, result.size());
    }
}