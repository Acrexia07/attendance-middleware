package com.marlonb.hr_middleware.security;

import com.marlonb.hr_middleware.model.admin.AdminAccount;
import com.marlonb.hr_middleware.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.marlonb.hr_middleware.message.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class AdminUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AdminAccount admin = adminRepository.findByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND.getErrorMessage());
        }

        return new AdminPrincipal(admin);
    }
}
