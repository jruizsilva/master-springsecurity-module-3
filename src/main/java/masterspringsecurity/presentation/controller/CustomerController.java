package masterspringsecurity.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.UserFacade;
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
    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<RegisteredUserDto> registerOneCustomer(@RequestBody @Valid final SaveUserRequest newUser) {
        RegisteredUserDto registeredUserDto = userFacade.registerOneCustomer(newUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(registeredUserDto);
    }
}
