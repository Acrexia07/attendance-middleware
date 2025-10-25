package com.marlonb.hr_middleware.exception;

import com.marlonb.hr_middleware.exception.custom.DuplicateResourceFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.marlonb.hr_middleware.exception.enums.ExceptionMessages.*;
import static com.marlonb.hr_middleware.exception.enums.KeyResponse.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    // HTTP STATUS CODE: 500 - INTERNAL SERVER ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handlesServletRelatedExceptions (Exception ex,
                                                                             HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new ErrorResponseDto(
                                    LocalDateTime.now(),
                                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                    INTERNAL_SERVER_ERROR_MESSAGE.getErrorMessage(),
                                    Map.of(SERVER_KEY_VALUE.getKeyValue(), List.of(ex.getMessage())),
                                    request.getRequestURI()
                             ));
    }

    // HTTP STATUS CODE: 409 - CONFLICT
    @ExceptionHandler(DuplicateResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlesDuplicateResourceExceptions (DuplicateResourceFoundException ex,
                                                                                HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(new ErrorResponseDto(
                                     LocalDateTime.now(),
                                     HttpStatus.CONFLICT.value(),
                                     DUPLICATE_RESOURCE_ERROR_MESSAGE.getErrorMessage(),
                                     Map.of(RESOURCE_KEY_VALUE.getKeyValue(), List.of(ex.getMessage())),
                                     request.getRequestURI()
                             ));
    }

    // HTTP STATUS CODE: 401 - UNAUTHORIZED
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handlesInvalidCredentialExceptions (BadCredentialsException ex,
                                                                                HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new ErrorResponseDto(
                                     LocalDateTime.now(),
                                     HttpStatus.UNAUTHORIZED.value(),
                                     UNAUTHORIZED_ERROR_MESSAGE.getErrorMessage(),
                                     Map.of(CREDENTIALS_KEY_VALUE.getKeyValue(), List.of(ex.getMessage())),
                                     request.getRequestURI()
                             ));
    }
}
