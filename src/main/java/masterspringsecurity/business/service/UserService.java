package masterspringsecurity.business.service;

import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.dto.user.request.SaveUserRequest;
import masterspringsecurity.domain.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
    UserEntity registerOneCustomer(SaveUserRequest newUser);
    Optional<UserEntity> findOneByUsername(String username);
}
