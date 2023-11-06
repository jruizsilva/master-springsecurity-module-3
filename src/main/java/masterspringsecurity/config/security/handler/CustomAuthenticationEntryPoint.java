package masterspringsecurity.config.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import masterspringsecurity.presentation.advice.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ApiError apiError = ApiError.builder()
                                    .backendMessage(authException.getLocalizedMessage())
                                    .method(request.getMethod())
                                    .url(request.getRequestURL()
                                                .toString())
                                    .message("No se encontraron credenciales de autenticación. Por favor, inicie sesión para acceder a esta función")
                                    .timestamp(LocalDateTime.now())
                                    .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String apiErrorJson = objectMapper.writeValueAsString(apiError);
        response.getWriter()
                .write(apiErrorJson);
    }
}
