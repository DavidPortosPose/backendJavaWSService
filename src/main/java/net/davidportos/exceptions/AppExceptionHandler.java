package net.davidportos.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import net.davidportos.backendJavaWSService.models.responses.ErrorMessage;
import net.davidportos.backendJavaWSService.models.responses.ValidationErrors;

@ControllerAdvice
public class AppExceptionHandler {

    //Controlador de excpecion especifica de email existente
    @ExceptionHandler(value = EmailException.class)
    public ResponseEntity<Object> handleEmailExistsException(EmailException ex, WebRequest webRequest) {
        
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleErrorValidationException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();

        for(ObjectError error: ex.getBindingResult().getAllErrors()) {
            String fieldNAme = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldNAme, errorMessage);
        }

        ValidationErrors validationErrors = new ValidationErrors(errors, new Date());
       
        return new ResponseEntity<>(validationErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    //Controlador de excepciones generales que no son especificas
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(Exception ex, WebRequest webRequest) {
        
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
    
}
