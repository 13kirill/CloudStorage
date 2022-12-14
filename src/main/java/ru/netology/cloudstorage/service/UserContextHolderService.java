package ru.netology.cloudstorage.service;

import org.springframework.lang.NonNull;
import ru.netology.cloudstorage.model.entity.User.User;

public interface UserContextHolderService {
    @NonNull
    User getCurrentUserContext();
}
