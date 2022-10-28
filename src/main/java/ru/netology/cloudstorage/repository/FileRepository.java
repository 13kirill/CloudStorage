package ru.netology.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.netology.cloudstorage.model.entity.StoredFile;

import java.util.Optional;
@Repository
public interface FileRepository extends JpaRepository<StoredFile, Long> {

    Optional<StoredFile> findByFileName(String fileName1);
}
