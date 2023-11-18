package masterspringsecurity.config.security.authorization;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.domain.entity.PublicOperationEntity;
import masterspringsecurity.domain.entity.security.GrantedPermissionEntity;
import masterspringsecurity.domain.entity.security.OperationEntity;
import masterspringsecurity.domain.entity.security.UserEntity;
import masterspringsecurity.persistence.security.OperationRepository;
import masterspringsecurity.persistence.security.PublicOperationRepository;
import masterspringsecurity.persistence.security.UserRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final OperationRepository operationRepository;
    private final PublicOperationRepository publicOperationRepository;
    private final UserRepository userRepository;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestContext) {
        HttpServletRequest request = requestContext.getRequest();
        String url = extractUrl(request);
        String httpMethod = request.getMethod();
        boolean isPublic = isPublic(url,
                                    httpMethod);
        if (isPublic) {
            return new AuthorizationDecision(true);
        }
        boolean isGranted = isGranted(url,
                                      httpMethod,
                                      authentication.get());
        return new AuthorizationDecision(isGranted);
    }

    private boolean isGranted(String url,
                              String httpMethod,
                              Authentication authentication) {
        if (!(authentication instanceof UsernamePasswordAuthenticationToken)) {
            throw new AuthenticationCredentialsNotFoundException("User not logged in");
        }
        List<OperationEntity> operationEntityList = obtainedOperations(authentication);
        boolean isGranted = operationEntityList.stream()
                                               .anyMatch(getOperationEntityPredicate(url,
                                                                                     httpMethod));
        return isGranted;
    }

    private Predicate<OperationEntity> getOperationEntityPredicate(String url,
                                                                   String httpMethod) {
        return operation -> {
            String basePath = operation.getModule()
                                       .getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);

            return matcher.matches() && httpMethod.equals(operation.getHttpMethod());
        };
    }

    private List<OperationEntity> obtainedOperations(Authentication authentication) {
        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
        String username = (String) authToken.getPrincipal();
        UserEntity userEntity =
                userRepository.findByUsernameIgnoreCase(username)
                              .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        return userEntity.getRole()
                         .getPermissionList()
                         .stream()
                         .map(GrantedPermissionEntity::getOperation)
                         .toList();

    }

    private boolean isPublic(String url,
                             String httpMethod) {
        List<PublicOperationEntity> publicOperationEntities = publicOperationRepository.findAll();

        return publicOperationEntities.stream()
                                      .anyMatch(getPublicOperationEntityPredicate(url,
                                                                                  httpMethod));
    }

    private Predicate<PublicOperationEntity> getPublicOperationEntityPredicate(String url,
                                                                               String httpMethod) {
        return operation -> {
            String basePath = operation.getModule()
                                       .getBasePath();
            Pattern pattern = Pattern.compile(basePath.concat(operation.getPath()));
            Matcher matcher = pattern.matcher(url);

            return matcher.matches() && httpMethod.equals(operation.getHttpMethod());
        };
    }

    private String extractUrl(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String url = request.getRequestURI();
        url = url.replace(contextPath,
                          "");
        return url;

    }
}
