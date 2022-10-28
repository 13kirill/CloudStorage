package ru.netology.cloudstorage.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoredFile {

    @ManyToOne(optional = false)
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String fileName;

    @Column (nullable = false)
    private long size;

    @Column (nullable = false)
    private String hash;

    @Column (nullable = false)
    private String fileUUID;
}