package uz.pentagol.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uz.pentagol.exceptions.*;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {AppBadRequest.class,
            ItemNotFound.class,
            ItemNotFound.class,
            UserNotFound.class,
            ItemAlreadyExists.class})
    public ResponseEntity<?> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler({AppForbiddenException.class})
    public ResponseEntity<?> handleForbidden(AppForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }
}
