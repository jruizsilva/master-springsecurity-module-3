package masterspringsecurity.business.facade;

import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;

public interface UserFacade {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
