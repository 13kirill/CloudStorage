package ru.netology.cloudstorage.service;

import org.springframework.lang.NonNull;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Данный интерфейс обеспечивает функционал работы с БД.
 */
public interface FileService {

    /**
     * Метод удаляет файл из БД.
     * @param fileName имя файла.
     */
    void deleteFile(@NonNull String fileName);

    /**
     * Метод добавляет файл в БД.
     * @param fileName имя файла
     * @param file ???
     */
    void uploadFile(@NonNull String fileName, @NonNull MultipartFile file);

    /**
     * ???
     * @param fileName имя файла.
     * @return
     */
    @NonNull
    MultiValueMap<String, Object> downloadFile(@NonNull String fileName);

    /**
     * Метод изменяет имя файла в БД.
     *
     * @param fileName    имя файла.
     * @param newFileName
     */
    void editFileName(@NonNull String fileName, @NonNull String newFileName);

}
