package masterspringsecurity.business.service;

import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;

public interface UserService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
