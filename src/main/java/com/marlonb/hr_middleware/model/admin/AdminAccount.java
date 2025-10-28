package com.marlonb.hr_middleware.model.admin;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FilterDef(name = "activeFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "activeFilter", condition = "is_deleted = :isDeleted")
@SQLDelete(sql = "UPDATE admin_data SET isDeleted = true, deleted_at = NOW() WHERE id = ?")
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

    // Attribute for Soft Delete
    private boolean isDeleted = false;
    private LocalDateTime deletedAt;

    @PrePersist
    void onCreate () {
        this.createdAt = LocalDateTime.now();
    }
}
