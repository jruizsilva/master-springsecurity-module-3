package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.JwtService;
import masterspringsecurity.business.service.RoleService;
import masterspringsecurity.business.service.UserService;
import masterspringsecurity.common.exception.InvalidPasswordException;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.RegisteredUserDto;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.dto.user.request.SaveUserRequest;
import masterspringsecurity.domain.entity.security.RoleEntity;
import masterspringsecurity.domain.entity.security.UserEntity;
import masterspringsecurity.persistence.security.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                                                        authenticationRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserEntity userEntity =
                userRepository.findByUsernameIgnoreCase(authenticationRequest.getUsername())
                              .orElseThrow(() -> new ObjectNotFoundException("User not found"));
        String jwt = jwtService.generateTokenv2(userEntity,
                                                generateExtraClaims(userEntity));
        return new AuthenticationResponse(jwt);
    }

    @Override
    public RegisteredUserDto registerOneCustomer(SaveUserRequest newUser) {
        validatePassword(newUser);
        RoleEntity defaultRole = roleService.findDefaultRole()
                                            .orElseThrow(() -> new ObjectNotFoundException("Role not found"));
        UserEntity userEntity =
                UserEntity.builder()
                          .username(newUser.getUsername())
                          .password(passwordEncoder.encode(newUser.getPassword()))
                          .name(newUser.getName())
                          .role(defaultRole)
                          .build();
        UserEntity userEntitySaved = userRepository.save(userEntity);
        String jwt = jwtService.generateTokenv2(userEntitySaved,
                                                generateExtraClaims(userEntity));
        return RegisteredUserDto.builder()
                                .id(userEntity.getId())
                                .username(userEntity.getUsername())
                                .name(userEntity.getName())
                                .role(userEntity.getRole()
                                                .getName())
                                .jwt(jwt)
                                .build();
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
                                  .getName());
        extraClaims.put("authorities",
                        userEntity.getAuthorities());
        return extraClaims;
    }

    public boolean validateToken(String jwt) {
        try {
            jwtService.extractUsername(jwt);
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public UserEntity findLoggedInUser() {
        UsernamePasswordAuthenticationToken auth =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
                                                                           .getAuthentication();
        String username = (String) auth.getPrincipal();
        return userRepository.findByUsernameIgnoreCase(username)
                             .orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }
}
