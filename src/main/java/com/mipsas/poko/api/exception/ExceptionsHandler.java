package com.mipsas.poko.api.exception;

import static com.mipsas.poko.api.exception.ErrorStatus.INCORRECT_REQUEST;
import java.util.List;
import java.util.Set;
import static java.util.stream.Collectors.toList;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ValidationErrorResponse(INCORRECT_REQUEST, getErrorMessages(ex.getBindingResult())), BAD_REQUEST);
    }

    @ExceptionHandler({PokoGenericException.class})
    public ResponseEntity<Object> handleAppException(final PokoGenericException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e) {
        return new ResponseEntity<>(new ValidationErrorResponse(INCORRECT_REQUEST, getErrorMessages(e.getConstraintViolations())), BAD_REQUEST);
    }

    private List<FieldError> getErrorMessages(final BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(error -> new FieldError(error.getField(), error.getDefaultMessage()))
                .collect(toList());
    }

    private List<FieldError> getErrorMessages(final Set<ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream()
                .map(v -> new FieldError(v.getPropertyPath().toString(), v.getMessage()))
                .collect(toList());
    }
}