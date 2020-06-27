package homework.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(WebRequest request, Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseObject responseObject = ResponseObject.of(status.value(), "try again");
        return handleExceptionInternal(ex, responseObject, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    public ResponseEntity<Object> handleException(WebRequest request, ResponseStatusException ex) {
        ex.printStackTrace();
        ResponseObject responseObject = ResponseObject.of(ex.getStatus().value(), ex.getReason());
        return handleExceptionInternal(ex, responseObject, new HttpHeaders(), ex.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ResponseObject responseObject = ResponseObject.of(status.value(), "Method Argument Not Valid");
        return handleExceptionInternal(ex, responseObject, new HttpHeaders(), status, request);
    }

}
