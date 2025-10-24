package com.marlonb.hr_middleware.service;

import com.marlonb.hr_middleware.model.dto.LoginRequestDto;
import com.marlonb.hr_middleware.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import static com.marlonb.hr_middleware.message.ErrorMessages.*;

@Service
@RequiredArgsConstructor
public class authService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String verifyUserCredentials (@Valid @RequestBody LoginRequestDto loginRequest) {

        // Authenticate user credentials using AuthenticationManager.
        // If authentication succeeds, generate a JWT token for the username.
        // If authentication fails, throw a BadCredentialsException.
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.username(),
                            loginRequest.password()
                    ));

            return jwtService.generateToken(loginRequest.username());
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException(INVALID_CREDENTIALS_FOUND.getErrorMessage());
        }
    }
}
