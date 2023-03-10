package ru.netology.cloudstorage.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import ru.netology.cloudstorage.exceptions.ServerException;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(MockitoJUnitRunner.class)
class MD5FileHashingServiceImplTest {

    @InjectMocks
    MD5FileHashingServiceImpl md5FileHashingServiceImpl;

    static String testAlgoritm;

    @Mock
    static File testFile;

    @Mock
    MessageDigest testMessageDigest;

    @Mock
    Path testPath;


    static byte[] testByteArray;

    static byte[] testDigesByteArray;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void setData() {
        testAlgoritm = "MD5";
        testByteArray = new byte[0];
        testDigesByteArray = new byte[]{1};
    }

    @Test
    void getHashServerException() {
        try (MockedStatic<MessageDigest> mockMessageDigest = Mockito.mockStatic(MessageDigest.class)) {
            mockMessageDigest.when(() -> MessageDigest.getInstance(testAlgoritm)).thenThrow(NoSuchAlgorithmException.class);
            assertThrows(ServerException.class, () -> md5FileHashingServiceImpl.getHash(testFile));
        }
    }

    @Test
    void getHashIOException() {
        try (MockedStatic<MessageDigest> mockMessageDigest = Mockito.mockStatic(MessageDigest.class);
             MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockMessageDigest.when(() -> MessageDigest.getInstance(testAlgoritm)).thenReturn(testMessageDigest);
            Mockito.when(testFile.toPath()).thenReturn(testPath);
            mockFiles.when(() -> Files.readAllBytes(testPath)).thenThrow(IOException.class);
            assertThrows(ServerException.class, () -> md5FileHashingServiceImpl.getHash(testFile));
        }
    }

    @Test
    void getHash() {
        try (MockedStatic<MessageDigest> mockMessageDigest = Mockito.mockStatic(MessageDigest.class);
             MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockMessageDigest.when(() -> MessageDigest.getInstance(testAlgoritm)).thenReturn(testMessageDigest);
            Mockito.when(testFile.toPath()).thenReturn(testPath);
            mockFiles.when(() -> Files.readAllBytes(testPath)).thenReturn(testByteArray);
            Mockito.when(testMessageDigest.digest()).thenReturn(testDigesByteArray);
            String actualResult = md5FileHashingServiceImpl.getHash(testFile);
            assertEquals(actualResult, DatatypeConverter.printHexBinary(testDigesByteArray).toUpperCase());
        }
    }
}