package com.ecommerce.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handlerExist(ResourceAlreadyExistsException ex){
        Map<String,Object> map = Map.of("Error","Conflict","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(map);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(ResourceNotFoundException ex){
        Map<String,Object> map = Map.of("Error","Not Found", "message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException ex){
        Map<String,Object> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err->map.put(err.getField(),err.getDefaultMessage()));
        Map<String,Object> body = Map.of("Error","Validation failed","details","map");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleAll(Exception ex){
        Map<String,Object> map = Map.of("Error","InternetServer Error","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }

}
