package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.cloudstorage.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName (String name);
}
