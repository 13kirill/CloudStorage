package ru.netology.cloudstorage.service.impl;

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
import org.springframework.core.io.FileSystemResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.exceptions.ClientException;
import ru.netology.cloudstorage.exceptions.ServerException;
import ru.netology.cloudstorage.model.DTO.StoredFileDto;
import ru.netology.cloudstorage.model.entity.StoredFile;
import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.repository.FileRepository;
import ru.netology.cloudstorage.security.jwt.JwtUser;
import ru.netology.cloudstorage.service.FileHashingService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FileServiceImplTest {

    @InjectMocks
    FileServiceImpl fileServiceImpl;

    @Mock
    FileStorageServiceImpl fileStorageServiceImpl;

    @Mock
    FileHashingService fileHashingService;

    @Mock
    SecurityContext securityContext;

    @Mock
    Authentication authentication;

    @Mock
    FileRepository fileRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static User testUser;

    static JwtUser testJwtUser;

    static MultipartFile testMultipartFile;

    static String testUuid;

    @Mock
    static File testFile;

    static StoredFile testStoredFile;

    @Mock
    static StoredFile mockTestStoredFile;

    static String testFileName;

    static String testNewFileName;

    static String testHash;

    static String badTestHash;

    static long testSize;

    @Mock
    static FileSystemResource testResource;

    static MultiValueMap<String, Object> testMultiValueMap;

    static List<StoredFileDto> testListStoredFileDto;

    static List<StoredFileDto> badTestListStoredFileDto;

    static int testLimit;

    @BeforeAll
    public static void setData() {

        testMultiValueMap = new LinkedMultiValueMap<>();

        testUser = User.builder()
                .username("test")
                .password("$2a$12$iHT74HfOrg2DLDwfeEqkg.4E89HY8GyyV2oCQa2YYfA.kwdT2/mJ6")
                .role("ROLE_USER")
                .build();

        testJwtUser = new JwtUser(testUser);

        testMultipartFile = new MockMultipartFile("testFileName",
                "original_file_name",
                "content_type",
                "file_content".getBytes());

        testUuid = "testUuid";

        testFileName = "testFileName";

        testNewFileName = "testNewFileName";

        testHash = "testHash";

        badTestHash = "badTestHash";

        testSize = 10;

        testStoredFile = StoredFile.builder()
                .filename(testFileName)
                .size(testSize)
                .hash(testHash)
                .fileUUID(testUuid)
                .user(testUser)
                .build();

        testLimit = 3;

        testListStoredFileDto = new ArrayList<>();
        testListStoredFileDto.add(new StoredFileDto(testNewFileName + 1, 10));
        testListStoredFileDto.add(new StoredFileDto(testNewFileName + 2, 15));
        testListStoredFileDto.add(new StoredFileDto(testNewFileName + 3, 20));

        badTestListStoredFileDto = new ArrayList<>();
        badTestListStoredFileDto.add(new StoredFileDto(testNewFileName + 1, 10));
        badTestListStoredFileDto.add(new StoredFileDto(testNewFileName + 2, 15));
        badTestListStoredFileDto.add(new StoredFileDto(testNewFileName + 3, 20));
        badTestListStoredFileDto.add(new StoredFileDto(testNewFileName + 4, 25));

    }


    @Test
    void deleteFileSuccess() {
        when(fileRepository.findByFilename(testFileName)).thenReturn(Optional.of(mockTestStoredFile));

        fileServiceImpl.deleteFile(testFileName);

        verify(fileRepository).delete(mockTestStoredFile);
        verify(fileStorageServiceImpl).deleteFile(mockTestStoredFile.getFileUUID());
    }

    @Test
    void uploadFile() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);

        when(fileStorageServiceImpl.saveFile(testMultipartFile)).thenReturn(testUuid);
        when(fileStorageServiceImpl.getFile(testUuid)).thenReturn(testFile);
        when(fileHashingService.getHash(testFile)).thenReturn(testHash);
        when(testFile.length()).thenReturn(testSize);
        when(fileRepository.save(any(StoredFile.class))).thenAnswer(i -> i.getArguments()[0]);

        StoredFile resultStoredFile = fileServiceImpl.uploadFile(testFileName, testMultipartFile);

        Assertions.assertEquals(testStoredFile.getFilename(), resultStoredFile.getFilename());
    }

    @Test
    void uploadFileException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);

        when(fileStorageServiceImpl.saveFile(testMultipartFile)).thenReturn(testUuid);
        when(fileStorageServiceImpl.getFile(testUuid)).thenReturn(testFile);
        when(fileHashingService.getHash(testFile)).thenThrow(ServerException.class);

        Assertions.assertThrows(ServerException.class, () -> fileServiceImpl.uploadFile(testFileName, testMultipartFile));
    }

    @Test
    void downloadFile() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);

        when(fileRepository.findByFilename(testFileName)).thenReturn(Optional.of(testStoredFile));
        when(fileStorageServiceImpl.getFile(testUuid)).thenReturn(testFile);
        when(fileHashingService.getHash(testFile)).thenReturn(testHash);
        when(fileStorageServiceImpl.getFileAsResource(testUuid)).thenReturn(testResource);

        MultiValueMap<String, Object> expectedMultiValueMap = fileServiceImpl.downloadFile(testFileName);

        testMultiValueMap.put("file", List.of(testResource));
        testMultiValueMap.put("hash", List.of(testHash));

        Assertions.assertEquals(expectedMultiValueMap, testMultiValueMap);
    }

    @Test
    void downloadFileClientException() {
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);

        when(fileRepository.findByFilename(testFileName)).thenReturn(Optional.of(testStoredFile));
        when(fileStorageServiceImpl.getFile(testUuid)).thenReturn(testFile);
        when(fileHashingService.getHash(testFile)).thenReturn(badTestHash);

        Assertions.assertThrows(ClientException.class, () -> fileServiceImpl.downloadFile(testFileName));
    }

    @Test
    void editFilename() {

        when(fileRepository.findByFilename(testFileName)).thenReturn(Optional.of(mockTestStoredFile));

        fileServiceImpl.editFilename(testFileName, testNewFileName);

        verify(mockTestStoredFile).setFilename(testNewFileName);

    }

    @Test
    void getInfoAboutAllFiles() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);

        when(fileRepository.findAllByUser(testUser)).thenReturn(testListStoredFileDto);

        List<StoredFileDto> expectedListStoredFileDto = fileServiceImpl.getInfoAboutAllFiles(testLimit);

        Assertions.assertEquals(expectedListStoredFileDto.subList(0, testLimit), testListStoredFileDto.subList(0, testLimit));
    }

    @Test
    void getInfoAboutAllFilesSubList() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);

        when(fileRepository.findAllByUser(testUser)).thenReturn(badTestListStoredFileDto);

        List<StoredFileDto> expectedListStoredFileDto = fileServiceImpl.getInfoAboutAllFiles(testLimit);

        Assertions.assertEquals(expectedListStoredFileDto.subList(0, testLimit), badTestListStoredFileDto.subList(0, testLimit));
    }

    @Test
    public void getCurrentUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(testJwtUser);

        User expectedUser = testJwtUser.getUser();
        User resultUser = fileServiceImpl.getCurrentUser();
        Assertions.assertEquals(expectedUser, resultUser);
    }
}