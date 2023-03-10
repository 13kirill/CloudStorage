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
import ru.netology.cloudstorage.model.DTO.StoredFileDto;
import ru.netology.cloudstorage.model.entity.StoredFile;
import ru.netology.cloudstorage.model.entity.User;
import ru.netology.cloudstorage.repository.FileRepository;
import ru.netology.cloudstorage.security.jwt.JwtUser;
import ru.netology.cloudstorage.service.FileHashingService;
import ru.netology.cloudstorage.service.FileService;
import ru.netology.cloudstorage.service.FileStorageService;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;
    private final FileHashingService fileHashingService;

    @Override
    @Transactional
    public void deleteFile(String fileName) {
        StoredFile storedFile = fileRepository.findByFilename(fileName)
                .orElseThrow(EntityNotFoundException::new);
        fileRepository.delete(storedFile);
        fileStorageService.deleteFile(storedFile.getFileUUID());
    }

    @Override
    public StoredFile uploadFile(String filename, MultipartFile multipartFile) {
        String uuid = fileStorageService.saveFile(multipartFile);
        File file = fileStorageService.getFile(uuid);
        String hash;
        User currentUser;
        StoredFile storedFile;
        try {
            currentUser = getCurrentUser();
            hash = fileHashingService.getHash(file);
            storedFile = StoredFile.builder()
                    .filename(filename)
                    .user(currentUser)
                    .hash(hash)
                    .fileUUID(uuid)
                    .size(file.length())
                    .build();

        } catch (ServerException | ClientException | DataAccessException ex) {
            fileStorageService.deleteFile(uuid);
            throw ex;
        }
        return fileRepository.save(storedFile);
    }

    @Override
    public MultiValueMap<String, Object> downloadFile(String filename) {
        StoredFile storedFile = fileRepository.findByFilename(filename)
                .orElseThrow(() -> new ClientException("Файл не найден"));
        File file = fileStorageService.getFile(storedFile.getFileUUID());
        String hash = fileHashingService.getHash(file);
        if (!storedFile.getHash().equals(hash)) {
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
    public void editFilename(String filename1, String filename2) {
        StoredFile storedFile = fileRepository.findByFilename(filename1)
                .orElseThrow(EntityNotFoundException::new);
        storedFile.setFilename(filename2);
    }

    @Override
    public List<StoredFileDto> getInfoAboutAllFiles(int limit) {
        User user = getCurrentUser();
        List<StoredFileDto> files = fileRepository.findAllByUser(user);
        if (files.size() <= limit) {
            return files;
        } else {
            return files.subList(0, limit);
        }
    }

    public User getCurrentUser() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.getUser();
    }
}