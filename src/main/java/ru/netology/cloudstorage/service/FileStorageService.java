package ru.netology.cloudstorage.service;

import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Данный интерфейс обеспечивает функционал работы с файлами.
 */

public interface FileStorageService {

    /**
     * Метод сохраняет файл.
     *
     * @param multipartFile представление загруженного файла.
     * @throws
     */
    String saveFile(@NonNull MultipartFile multipartFile);


    /**
     * @param id идентификационный номер файла.
     * @throws IOException если файл с указаным id не найден.
     */
    void deleteFile(@NonNull String id);

    /**
     * Отдаёт сам файл.
     * @param id идентификационный номер файла.
     * @return
     * @throws MalformedURLException если неверн указан URL.
     * @throws FileNotFoundException если файл с указаным id не найден.
     */
    @NonNull
    Resource getFileAsResource(String id);

    File getFile(String uuid);

}
