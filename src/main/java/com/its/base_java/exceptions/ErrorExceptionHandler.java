package com.its.base_java.exceptions;

import com.its.base_java.component.Translator;
import com.its.base_java.dto.response.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestControllerAdvice
public class ErrorExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        log.error("Upload Over max size:", exc);
        return new ResponseEntity<>(new BaseResponse<>("File too large"), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException exc) {
        log.error("handleBusinessException:", exc);
        return new ResponseEntity<>(new BaseResponse<>(exc.getData(), exc.getMessage()), exc.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exc) {
        log.error("handleGlobalException:", exc);
        return ResponseEntity.badRequest().body(new BaseResponse<>(exc.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException exc) {
        log.error("handleValidationExceptions:", exc);
        Map<String, Object> errors = new HashMap<>();
        exc.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(new BaseResponse<>(errors, Translator.toLocale("required_fields")));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleGlobalNotFoundException(NoHandlerFoundException exc) {
        log.error("handleGlobalException:", exc);
        return new ResponseEntity<>(new BaseResponse<>(exc.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnexpectedRollbackException.class)
    public ResponseEntity<?> handleConstraintViolationException(Exception ex) {
        return ResponseEntity.badRequest().body(new BaseResponse<>(ex.getMessage()));
    }

}
