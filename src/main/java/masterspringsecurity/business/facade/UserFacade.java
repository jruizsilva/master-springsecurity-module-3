package masterspringsecurity.business.facade;

import jakarta.servlet.http.HttpServletRequest;
import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.LogoutResponse;
import masterspringsecurity.domain.dto.user.RegisteredUserDto;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.dto.user.request.SaveUserRequest;
import masterspringsecurity.domain.entity.security.UserEntity;

public interface UserFacade {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    RegisteredUserDto registerOneCustomer(SaveUserRequest newUser);
    boolean validateToken(String jwt);
    UserEntity findLoggedInUser();
    LogoutResponse logout(HttpServletRequest request);
}
