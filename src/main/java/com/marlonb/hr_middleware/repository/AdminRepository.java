package com.marlonb.hr_middleware.repository;

import com.marlonb.hr_middleware.model.admin.AdminAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminAccount, Long> {
}
