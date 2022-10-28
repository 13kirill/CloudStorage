package ru.netology.cloudstorage.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.cloudstorage.exceptions.ServerException;
import ru.netology.cloudstorage.exceptions.StorageDirectoryNotFoundException;
import ru.netology.cloudstorage.service.FileStorageService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${filestorage.dir}")
    protected String fileStorageDir;

    @PostConstruct
    public void checkFileStorageDir() throws FileNotFoundException {
        Path path = Paths.get(fileStorageDir);
        if (!Files.exists(path)) {
            throw new StorageDirectoryNotFoundException(path);
        }
        if (!Files.isDirectory(path)) {
            throw new FileNotFoundException("Файл не является каталогом");
            //TODO
        }
        if (!Files.isWritable(path)) {
            throw new FileNotFoundException("Файл не существует или не является исполняемымы файлом");
            //TODO
        }
    }

    @Override
    public String saveFile(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString();
        try {
            Path path = Files.createFile(Paths.get(fileStorageDir, uuid));
            multipartFile.transferTo(path);

        } catch (IOException e) {
            try {
                Files.deleteIfExists(Paths.get(fileStorageDir, uuid));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
                //TODO не получилось удалить файл
            }
            throw new RuntimeException(e);
            //TODO не получилось сохранить файл
        }
        return uuid;
    }

    @Override
    public void deleteFile(String uuid) {
        Path path = Paths.get(fileStorageDir, uuid);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
            //TODO не получилось удалить файл
        }
    }

    @Override
    public Resource getFileAsResource(String uuid) {
        Path path = Paths.get(fileStorageDir, uuid);
        validateFile(path);
        try {
            return new InputStreamResource(Files.newInputStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getFile(String uuid) {
        Path path = Paths.get(fileStorageDir, uuid);
        validateFile(path);
        return path.toFile();
    }

    protected void validateFile(Path path) {
        if (!Files.exists(path)) {
            throw new ServerException("Файл не существует.");
            //TODO
        }
        if (!Files.isReadable(path)) {
            throw new ServerException("Файл не доступен для чтения");
            //TODO
        }
        if (!Files.isRegularFile(path)) {
            throw new ServerException("Не является файлом с непрозрачным содержимым");
            //TODO
        }
    }
}
