package masterspringsecurity.business.facade.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.facade.UserFacade;
import masterspringsecurity.business.service.UserService;
import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.LogoutResponse;
import masterspringsecurity.domain.dto.user.RegisteredUserDto;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.dto.user.request.SaveUserRequest;
import masterspringsecurity.domain.entity.security.UserEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {
    private final UserService userService;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        return userService.login(authenticationRequest);
    }

    @Override
    public RegisteredUserDto registerOneCustomer(SaveUserRequest newUser) {
        return userService.registerOneCustomer(newUser);
    }

    @Override
    public boolean validateToken(String jwt) {
        return userService.validateToken(jwt);
    }

    @Override
    public UserEntity findLoggedInUser() {
        return userService.findLoggedInUser();
    }

    @Override
    public LogoutResponse logout(HttpServletRequest request) {
        userService.logout(request);
        return new LogoutResponse("Logout success");
    }
}
