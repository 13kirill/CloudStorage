package ru.netology.cloudstorage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.exceptions.ClientException;
import ru.netology.cloudstorage.exceptions.ServerException;
import ru.netology.cloudstorage.model.DTO.FileDTO;
import ru.netology.cloudstorage.model.entity.StoredFile;
import ru.netology.cloudstorage.model.entity.User.User;
import ru.netology.cloudstorage.repository.FileRepository;
import ru.netology.cloudstorage.service.FileHashingService;
import ru.netology.cloudstorage.service.FileService;
import ru.netology.cloudstorage.service.FileStorageService;
import ru.netology.cloudstorage.service.UserContextHolderService;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;
    private final FileHashingService fileHashingService;
    private final UserContextHolderService userContextHolderService;

    @Override
    @Transactional
    public void deleteFile(String fileName) {
        StoredFile storedFile = fileRepository.findByFileName(fileName)
                .orElseThrow(EntityNotFoundException::new);
        fileRepository.delete(storedFile);
        fileStorageService.deleteFile(storedFile.getFileUUID());
    }

    @Override
    public void uploadFile(String fileName, MultipartFile multipartFile) {
        String uuid = fileStorageService.saveFile(multipartFile);
        File file = fileStorageService.getFile(uuid);
        String hash;
        User currentUser;
        try {
            currentUser = userContextHolderService.getCurrentUserContext();
            hash = fileHashingService.getHash(file);
            StoredFile storedFile = StoredFile.builder()
                    .fileName(fileName)
                    .user(currentUser)
                    .hash(hash)
                    .fileUUID(uuid)
                    .size(file.length())
                    .build();
            fileRepository.save(storedFile);
        } catch (ServerException | ClientException | DataAccessException ex) {
            fileStorageService.deleteFile(uuid);
            throw ex;
        }
    }

    @Override
    public MultiValueMap<String, Object> downloadFile(String fileName) {
        //todo проверяем есть ли у пользователя право смотреть файл
        StoredFile storedFile = fileRepository.findByFileName(fileName).orElseThrow(() -> new ClientException("Файл не найден"));
        File file = fileStorageService.getFile(storedFile.getFileUUID());
        String hash = fileHashingService.getHash(file);
        if(!storedFile.getHash().equals(hash)){
            throw new ClientException("Файл был изменён");
        }
        Resource resource = fileStorageService.getFileAsResource(storedFile.getFileUUID());
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.put("file", List.of(resource));
        multiValueMap.put("hash", List.of(hash));
        return multiValueMap;
    }

    @Transactional
    @Override
    public void editFileName(String fileName1, String fileName2) {
        StoredFile storedFile = fileRepository.findByFileName(fileName1)
                .orElseThrow(EntityNotFoundException::new);
        storedFile.setFileName(fileName2);
    }

    @Override
    public List<FileDTO> getInfoAboutAllFiles(int limit) {
        User user = getCurrentUser();
        List<FileDTO> files = fileRepository.findAllByUser(user);
        if (files.size() <= limit) {
            return files;
        } else {
            return files.subList(0, limit);
        }
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}