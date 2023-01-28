package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudstorage.model.DTO.FileDTO;
import ru.netology.cloudstorage.model.entity.User.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);

    Optional<User> findByName(String test);

}
