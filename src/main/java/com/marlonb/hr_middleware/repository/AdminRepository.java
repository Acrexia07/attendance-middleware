package com.marlonb.hr_middleware.repository;

import com.marlonb.hr_middleware.model.admin.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<AdminAccount, Long> {
    AdminAccount findByUsername (String username);
    boolean existsByUsername (String username);

    @Query(value = "SELECT * from admin_data WHERE is_deleted = 1", nativeQuery = true)
    List<AdminAccount> findAllDeleted();
}
