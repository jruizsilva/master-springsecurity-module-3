package masterspringsecurity.business.service.impl;

import jakarta.servlet.http.HttpServletRequest;
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
import masterspringsecurity.domain.entity.security.JwtTokenEntity;
import masterspringsecurity.domain.entity.security.RoleEntity;
import masterspringsecurity.domain.entity.security.UserEntity;
import masterspringsecurity.persistence.security.JwtTokenRepository;
import masterspringsecurity.persistence.security.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private final RoleService roleService;
    private final JwtTokenRepository jwtTokenRepository;

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
        saveUserToken(jwt,
                      userEntity);
        return new AuthenticationResponse(jwt);
    }

    private void saveUserToken(String jwt,
                               UserEntity userEntity) {
        JwtTokenEntity jwtTokenEntity = JwtTokenEntity.builder()
                                                      .token(jwt)
                                                      .user(userEntity)
                                                      .expirationDate(jwtService.getExpirationDateFromToken(jwt))
                                                      .isValid(true)
                                                      .build();
        jwtTokenRepository.save(jwtTokenEntity);
    }

    @Override
    public RegisteredUserDto registerOneCustomer(SaveUserRequest newUser) {
        validatePassword(newUser);
        RoleEntity defaultRole = roleService.findDefaultRole()
                                            .orElseThrow(() -> new ObjectNotFoundException("Role not found"));
        UserEntity userEntityToSave =
                UserEntity.builder()
                          .username(newUser.getUsername())
                          .password(passwordEncoder.encode(newUser.getPassword()))
                          .name(newUser.getName())
                          .role(defaultRole)
                          .build();
        UserEntity userEntitySaved = userRepository.save(userEntityToSave);
        String jwt = jwtService.generateTokenv2(userEntitySaved,
                                                generateExtraClaims(userEntityToSave));
        saveUserToken(jwt,
                      userEntitySaved);
        return RegisteredUserDto.builder()
                                .id(userEntitySaved.getId())
                                .username(userEntitySaved.getUsername())
                                .name(userEntitySaved.getName())
                                .role(userEntitySaved.getRole()
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

    @Override
    public void logout(HttpServletRequest request) {
        String jwt = jwtService.extractJwtFromRequest(request);
        if (!StringUtils.hasText(jwt)) {
            return;
        }
        Optional<JwtTokenEntity> jwtTokenEntityOptional =
                jwtTokenRepository.findByToken(jwt);
        if (jwtTokenEntityOptional.isEmpty()) {
            return;
        }
        JwtTokenEntity jwtTokenEntity = jwtTokenEntityOptional.get();
        if (Boolean.TRUE.equals(jwtTokenEntity.getIsValid())) {
            jwtTokenEntity.setIsValid(false);
            jwtTokenRepository.save(jwtTokenEntity);
        }
    }
}
