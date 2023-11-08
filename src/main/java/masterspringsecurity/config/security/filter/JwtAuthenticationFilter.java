package masterspringsecurity.config.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.JwtService;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.domain.entity.security.UserEntity;
import masterspringsecurity.persistence.security.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtService.extractJwtFromRequest(request);
        if (!StringUtils.hasText(jwt)) {
            filterChain.doFilter(request,
                                 response);
            return;
        }
        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext()
                                                     .getAuthentication() == null) {
            UserEntity userEntity = userRepository.findByUsernameIgnoreCase(username)
                                                  .orElseThrow(() -> new ObjectNotFoundException("User not found"));
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username,
                                                            null,
                                                            userEntity.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext()
                                 .setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,
                             response);

    }
}
