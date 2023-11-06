package masterspringsecurity.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.UserFacade;
import masterspringsecurity.business.service.impl.AuthenticationServiceImpl;
import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserFacade userFacade;
    private final AuthenticationServiceImpl authenticationService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam final String jwt) {
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(userFacade.login(authenticationRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid final AuthenticationRequest requestAuthentication) {
        return ResponseEntity.ok(authenticationService.login(requestAuthentication));
    }

    @PreAuthorize("permitAll")
    @GetMapping("/public-access")
    public ResponseEntity<String> publicAccess() {
        return ResponseEntity.ok("Public access");
    }

    @PreAuthorize("hasAuthority('READ_MY_PROFILE')")
    @GetMapping("/profile")
    public ResponseEntity<UserEntity> findMyProfile() {
        UserEntity userEntity = authenticationService.findLoggedInUser();
        return ResponseEntity.ok(userEntity);
    }
}
