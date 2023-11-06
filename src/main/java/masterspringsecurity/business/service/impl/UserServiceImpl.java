package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.JwtService;
import masterspringsecurity.business.service.UserService;
import masterspringsecurity.common.exception.InvalidPasswordException;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.common.util.Role;
import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.dto.user.request.SaveUserRequest;
import masterspringsecurity.domain.entity.UserEntity;
import masterspringsecurity.persistence.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                        authenticationRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserEntity userEntity =
                userRepository.findByUsernameIgnoreCase(authenticationRequest.getUsername())
                              .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        String jwt = jwtService.generateToken(userEntity,
                                              generateExtraClaims(userEntity));
        return new AuthenticationResponse(jwt);
    }

    @Override
    public UserEntity registerOneCustomer(SaveUserRequest newUser) {
        validatePassword(newUser);
        UserEntity userEntity =
                UserEntity.builder()
                          .username(newUser.getUsername())
                          .password(passwordEncoder.encode(newUser.getPassword()))
                          .name(newUser.getName())
                          .role(Role.CUSTOMER)
                          .build();
        return userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findOneByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    private void validatePassword(SaveUserRequest newUser) {
        if (!newUser.getPassword()
                    .equals(newUser.getRepeatedPassword())) {
            throw new InvalidPasswordException("Passwords do not match");
        }
    }

    private Map<String, Object> generateExtraClaims(UserEntity userEntity) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",
                        userEntity.getName());
        extraClaims.put("role",
                        userEntity.getRole()
                                  .name());
        extraClaims.put("permissions",
                        userEntity.getAuthorities());
        return extraClaims;
    }
}
