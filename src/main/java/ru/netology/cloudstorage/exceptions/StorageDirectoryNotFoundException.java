package ru.netology.cloudstorage.exceptions;

import java.nio.file.Path;

public class StorageDirectoryNotFoundException extends ServerException{
    public StorageDirectoryNotFoundException(Path path) {
        super("Отсутствует директория для загрузки и сохранения файлов. Путь: " + path);
    }
}
