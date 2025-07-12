package br.devdeloop.uepb.excepions;

import jakarta.persistence.PersistenceException;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class AuthExceptionsHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthExceptionsHandler.class);

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> registerAlreadyInDatabaseHandler(BadRequestException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Username already registered."));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> loginBadRequestHandler(BadCredentialsException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Bad credentials."));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> loginAcessDeniedHandler(AccessDeniedException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Map.of("message", "User role mismatch."));
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity shipContainerPersistenceException(PersistenceException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Failure trying to add/update an ship container."));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity shipContainerNoSuchElementException(NoSuchElementException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Ship container not found."));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> developerRuntimeException(RuntimeException e) {
        logger.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Some shit happened. Go to see Java console."));
    }
}