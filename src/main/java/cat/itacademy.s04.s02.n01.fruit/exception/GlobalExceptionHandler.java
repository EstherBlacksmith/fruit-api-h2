package cat.itacademy.s04.s02.n01.fruit.exception;

import cat.itacademy.s04.s02.n01.fruit.fruit.exception.FruitNotFoundException;
import cat.itacademy.s04.s02.n01.fruit.fruit.exception.InvalidFruitRequestException;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.InvalidProviderRequestException;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderDuplicateNameException;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderHasFruitsAssociatedException;
import cat.itacademy.s04.s02.n01.fruit.provider.exception.ProviderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(FruitNotFoundException.class)
    public ResponseEntity<String> handleNotFound(FruitNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidFruitRequestException.class)
    public ResponseEntity<String> handleBadRequest(InvalidFruitRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ProviderDuplicateNameException.class)
    public ResponseEntity<String> handleProviderExists(ProviderDuplicateNameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public ResponseEntity<String> handleProviderNotFoud(ProviderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidProviderRequestException.class)
    public ResponseEntity<String> handleProviderBadRequest(InvalidProviderRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ProviderHasFruitsAssociatedException.class)
    public ResponseEntity<String> handleProviderFruitsAssociated(ProviderHasFruitsAssociatedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }



}