package masterspringsecurity.presentation.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e,
                                                    HttpServletRequest request) {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Error interno del servidor,  por favor intente mas tarde")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                   HttpServletRequest request) {
        System.out.println("handleMethodArgumentNotValidException");
        ApiError apiError = ApiError.builder()
                                    .backendMessage(e.getLocalizedMessage())
                                    .message("Error en la petición enviada")
                                    .url(request.getRequestURL()
                                                .toString())
                                    .method(request.getMethod())
                                    .timestamp(LocalDateTime.now())
                                    .build();
        System.out.println(e.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(apiError);
    }
}
