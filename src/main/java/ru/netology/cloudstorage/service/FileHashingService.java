package ru.netology.cloudstorage.service;

import org.springframework.lang.NonNull;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface FileHashingService {

    @NonNull
    String getHash(@NonNull File file);
}
