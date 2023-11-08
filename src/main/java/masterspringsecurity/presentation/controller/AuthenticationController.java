package masterspringsecurity.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.UserFacade;
import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.LogoutResponse;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.entity.security.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserFacade userFacade;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam final String jwt) {
        boolean isTokenValid = userFacade.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid final AuthenticationRequest requestAuthentication) {
        return ResponseEntity.ok(userFacade.login(requestAuthentication));
    }

    @GetMapping("/public-access")
    public ResponseEntity<String> publicAccess() {
        return ResponseEntity.ok("Public access");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findMyProfile() {
        UserEntity userEntity = userFacade.findLoggedInUser();
        return ResponseEntity.ok(userEntity);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest  request) {
        return ResponseEntity.ok(userFacade.logout(request));
    }
}
