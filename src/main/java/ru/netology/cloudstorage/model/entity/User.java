package ru.netology.cloudstorage.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    @Column(unique = true, nullable = false)
    protected String username;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    protected String role;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = CascadeType.ALL)
    protected List<StoredFile> storedFiles;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}