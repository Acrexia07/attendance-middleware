package com.marlonb.hr_middleware.model.admin;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "admin_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE admin_data SET isDeleted = true, deleted_at = NOW() WHERE id = ?")
@FilterDef(name = "activeFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "activeFilter", condition = " is_deleted = :isDeleted")
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
