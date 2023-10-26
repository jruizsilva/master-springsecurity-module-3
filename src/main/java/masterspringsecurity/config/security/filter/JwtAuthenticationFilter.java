package masterspringsecurity.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import masterspringsecurity.business.service.JwtService;
import masterspringsecurity.domain.entity.UserEntity;
import masterspringsecurity.persistence.UserRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,
                                 response);
            return;
        }
        String token = authorizationHeader.substring(7);
        String username = jwtService.extractUsername(token);

        if (username != null && SecurityContextHolder.getContext()
                                                     .getAuthentication() == null) {
            UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username)
                                                  .orElseThrow(() -> new ObjectNotFoundException("User not found"));
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username,
                                                            null,
                                                            userEntity.getAuthorities());
            SecurityContextHolder.getContext()
                                 .setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,
                             response);

    }
}
