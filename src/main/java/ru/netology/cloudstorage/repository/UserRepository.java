package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudstorage.model.entity.User.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String name);

    Optional<User> findByName(String test);
}
