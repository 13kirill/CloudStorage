package ru.netology.cloudstorage.service.impl;

import org.springframework.stereotype.Service;
import ru.netology.cloudstorage.exceptions.ServerException;
import ru.netology.cloudstorage.service.FileHashingService;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class MD5FileHashingServiceImpl implements FileHashingService {
    @Override
    public String getHash(File file) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new ServerException("Не найден алгоритм для создания hash. MD5.");
        }
        try {
            md.update(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new ServerException("TODO");
            //Ошибка при попытке прочтения файла
        }
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }
}
