package com.marlonb.hr_middleware.model.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Attribute
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate () {
        this.createdAt = LocalDateTime.now();
    }
}
