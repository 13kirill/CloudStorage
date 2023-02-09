package ru.netology.cloudstorage.service;

import org.springframework.lang.NonNull;

import java.io.File;

public interface FileHashingService {

    @NonNull
    String getHash(@NonNull File file);
}
