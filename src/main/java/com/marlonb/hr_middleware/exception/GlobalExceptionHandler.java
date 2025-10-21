package com.marlonb.hr_middleware.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.marlonb.hr_middleware.exception.enums.ExceptionMessages.*;
import static com.marlonb.hr_middleware.exception.enums.KeyResponse.*;

@ControllerAdvice
public class GlobalExceptionHandler {

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
}
