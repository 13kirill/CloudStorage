package ru.netology.cloudstorage.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.util.FieldUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.exceptions.ServerException;
import ru.netology.cloudstorage.exceptions.StorageDirectoryNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
//@PrepareForTest({ Files.class })
class FileStorageServiceImplTest {

    @InjectMocks
    @Spy
    FileStorageServiceImpl fileStorageServiceImpl;

    static String testFileStorageDir;

    static String testBadFileStorageDir;

    @Mock
    static Path pathMock;

    @Mock
    static File testFile;


    @Mock
    static Files testFiles;

    @Mock
    static MultipartFile testMultipartFile;

    @Mock
    static Path mockPath;

    @Mock
    static File mockFile;

    @Mock
    static InputStream mockInputStream;

    static MultipartFile testBadMultipartFile;

    static UUID testUUID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        FieldUtils.setProtectedFieldValue("fileStorageDir", fileStorageServiceImpl, testFileStorageDir);
    }

    @BeforeAll
    public static void setData() {
        testFileStorageDir = "D:/Programming/CLOUD";
        testBadFileStorageDir = "C:/Programming/CLOUD";




        testBadMultipartFile = new MockMultipartFile("testFileName",
                "original_file_name",
                "content_type",
                "file_content".getBytes());

        testUUID = UUID.randomUUID();
    }

    @Test
    public void testCheckFileStorageDirSuccess() {

        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isDirectory(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isWritable(Path.of(testFileStorageDir))).thenReturn(true);

            try {
                fileStorageServiceImpl.checkFileStorageDir();
            } catch (Exception e) {
                fail("Should not have thrown any exception");
            }

            mockFiles.verify(() -> Files.exists(Path.of(testFileStorageDir)));
            mockFiles.verify(() -> Files.isDirectory(Path.of(testFileStorageDir)));
            mockFiles.verify(() -> Files.isWritable(Path.of(testFileStorageDir)));
        }
    }

    @Test
    public void testCheckFileStorageDirStorageDirectoryNotFoundException() {

        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(false);
            assertThrows(StorageDirectoryNotFoundException.class,
                    () -> fileStorageServiceImpl.checkFileStorageDir());
        }

    }

    @Test
    public void testCheckFileStorageDirFileNotFoundExceptionNotDirectory() {

        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isDirectory(Path.of(testFileStorageDir))).thenReturn(false);
            assertThrows(FileNotFoundException.class,
                    () -> fileStorageServiceImpl.checkFileStorageDir());
        }

    }

    @Test
    public void testCheckFileStorageDirFileNotFoundExceptionFileNotExist() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isDirectory(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isWritable(Path.of(testFileStorageDir))).thenReturn(false);
            assertThrows(FileNotFoundException.class,
                    () -> fileStorageServiceImpl.checkFileStorageDir());
        }

    }

    @Test
    void saveFile() throws IOException {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class);
             MockedStatic<UUID> mockUUID = Mockito.mockStatic(UUID.class)) {

            mockUUID.when(() -> UUID.randomUUID()).thenReturn(testUUID);
            mockFiles.when(() -> Files
                            .createFile(Paths.get(testFileStorageDir, testUUID.toString())))
                    .thenReturn(pathMock);

            fileStorageServiceImpl.saveFile(testMultipartFile);

            verify(testMultipartFile).transferTo(pathMock);

        }
    }

    @Test
    void saveFileFirstRuntimeException() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class);
             MockedStatic<UUID> mockUUID = Mockito.mockStatic(UUID.class)) {

            mockUUID.when(() -> UUID.randomUUID()).thenReturn(testUUID);
            mockFiles.when(() -> Files
                            .createFile(Paths.get(testFileStorageDir, testUUID.toString())))
                    .thenThrow(IOException.class);

            assertThrows(RuntimeException.class,
                    () -> fileStorageServiceImpl.saveFile(testMultipartFile));
        }
    }

    @Test
    void saveFileSecondRuntimeException() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class);
             MockedStatic<UUID> mockUUID = Mockito.mockStatic(UUID.class)) {

            mockUUID.when(() -> UUID.randomUUID()).thenReturn(testUUID);
            mockFiles.when(() -> Files
                            .createFile(Paths.get(testFileStorageDir, testUUID.toString())))
                    .thenThrow(IOException.class);
            mockFiles.when(() -> Files
                            .deleteIfExists(Paths.get(testFileStorageDir, testUUID.toString())))
                    .thenThrow(IOException.class);
            assertThrows(RuntimeException.class,
                    () -> fileStorageServiceImpl.saveFile(testMultipartFile));
        }
    }

    @Test
    void saveFileChecDelete() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class);
             MockedStatic<UUID> mockUUID = Mockito.mockStatic(UUID.class)) {

            mockUUID.when(() -> UUID.randomUUID()).thenReturn(testUUID);
            mockFiles.when(() -> Files
                            .createFile(Paths.get(testFileStorageDir, testUUID.toString())))
                    .thenThrow(IOException.class);

            assertThrows(RuntimeException.class,
                    () -> fileStorageServiceImpl.saveFile(testMultipartFile));

            mockFiles.verify(() -> Files.deleteIfExists(Paths.get(testFileStorageDir, testUUID.toString())));
        }
    }

    @Test
    void deleteFile() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            fileStorageServiceImpl.deleteFile(testUUID.toString());
            mockFiles.verify(() -> Files.deleteIfExists(Paths.get(testFileStorageDir, testUUID.toString())));
        }
    }

    @Test
    void deleteFileIOException() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files
                            .deleteIfExists(Paths.get(testFileStorageDir, testUUID.toString())))
                    .thenThrow(IOException.class);
            assertThrows(RuntimeException.class,
                    () -> fileStorageServiceImpl.deleteFile(testUUID.toString()));
        }
    }


    @Test
    void getFileAsResource() {
        try (MockedStatic<Paths> mokedPaths = Mockito.mockStatic(Paths.class);
             MockedStatic<Files> mokedFiles = Mockito.mockStatic(Files.class)) {
            mokedPaths.when(() -> Paths.get("D:/Programming/CLOUD", testUUID.toString()))
                    .thenReturn(mockPath);
            doNothing().when(fileStorageServiceImpl).validateFile(mockPath);
            mokedFiles.when(() -> Files.newInputStream(mockPath)).thenReturn(mockInputStream);
            Resource actualResource = fileStorageServiceImpl.getFileAsResource(testUUID.toString());
            Resource expectedResource = new InputStreamResource(mockInputStream);
            Assertions.assertEquals(actualResource, expectedResource);
        }
    }

    @Test
    void getFileAsResourceRuntimeException() {
        try (MockedStatic<Paths> mokedPaths = Mockito.mockStatic(Paths.class);
             MockedStatic<Files> mokedFiles = Mockito.mockStatic(Files.class)) {
            mokedPaths.when(() -> Paths.get("D:/Programming/CLOUD", testUUID.toString()))
                    .thenReturn(mockPath);
            doNothing().when(fileStorageServiceImpl).validateFile(mockPath);
            mokedFiles.when(() -> Files.newInputStream(mockPath)).thenThrow(IOException.class);
  Assertions.assertThrows(RuntimeException.class, () -> fileStorageServiceImpl.getFileAsResource(testUUID.toString()));
        }
    }

    @Test
    public void testGetFile() {
        try (MockedStatic<Paths> mokedPaths = Mockito.mockStatic(Paths.class)) {
            mokedPaths.when(() -> Paths.get("D:/Programming/CLOUD", testUUID.toString()))
                    .thenReturn(mockPath);

            doNothing().when(fileStorageServiceImpl).validateFile(mockPath);

            when(mockPath.toFile()).thenReturn(mockFile);

            File actualFile = fileStorageServiceImpl.getFile(testUUID.toString());

            verify(fileStorageServiceImpl).validateFile(mockPath);

            assertEquals(mockFile, actualFile, "getFile method did not return expected file");
        }
    }


    @Test
    void validateFileSuccess() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isRegularFile(Path.of(testFileStorageDir))).thenReturn(true);

            fileStorageServiceImpl.validateFile(Paths.get(testFileStorageDir));

            mockFiles.verify(() -> Files.exists(Path.of(testFileStorageDir)));
            mockFiles.verify(() -> Files.isReadable(Path.of(testFileStorageDir)));
            mockFiles.verify(() -> Files.isRegularFile(Path.of(testFileStorageDir)));
        }
    }

    @Test
    void validateFileServerExceptionExists() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(false);
            mockFiles.when(() -> Files.isReadable(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isRegularFile(Path.of(testFileStorageDir))).thenReturn(true);

            //fileStorageServiceImpl.validateFile(Paths.get(testFileStorageDir));

            assertThrows(ServerException.class,
                    () -> fileStorageServiceImpl.validateFile(Path.of(testFileStorageDir)));
        }
    }

    @Test
    void validateFileServerExceptionIsReadable() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(Path.of(testFileStorageDir))).thenReturn(false);
            mockFiles.when(() -> Files.isRegularFile(Path.of(testFileStorageDir))).thenReturn(true);

            assertThrows(ServerException.class,
                    () -> fileStorageServiceImpl.validateFile(Path.of(testFileStorageDir)));
        }
    }

    @Test
    void validateFileServerExceptionIsRegularFile() {
        try (MockedStatic<Files> mockFiles = Mockito.mockStatic(Files.class)) {
            mockFiles.when(() -> Files.exists(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(Path.of(testFileStorageDir))).thenReturn(true);
            mockFiles.when(() -> Files.isRegularFile(Path.of(testFileStorageDir))).thenReturn(false);

            assertThrows(ServerException.class,
                    () -> fileStorageServiceImpl.validateFile(Path.of(testFileStorageDir)));
        }
    }
}