package ru.netology.cloudstorage.model.DTO;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.netology.cloudstorage.model.entity.StoredFile} entity
 */
@Data
public class StoredFileDto implements Serializable {
    private final String filename;
    private final long size;
}