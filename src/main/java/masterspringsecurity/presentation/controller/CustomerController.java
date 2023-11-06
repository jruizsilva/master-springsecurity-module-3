package masterspringsecurity.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.impl.AuthenticationServiceImpl;
import masterspringsecurity.domain.dto.user.RegisteredUserDto;
import masterspringsecurity.domain.dto.user.request.SaveUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final AuthenticationServiceImpl authenticationService;

    @PostMapping
    public ResponseEntity<RegisteredUserDto> registerOne(@RequestBody @Valid final SaveUserRequest newUser) {
        RegisteredUserDto registeredUserDto = authenticationService.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(registeredUserDto);
    }
}
