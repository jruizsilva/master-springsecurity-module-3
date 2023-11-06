package masterspringsecurity.business.service.impl;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.business.service.JwtService;
import masterspringsecurity.business.service.UserService;
import masterspringsecurity.common.exception.ObjectNotFoundException;
import masterspringsecurity.domain.dto.user.AuthenticationResponse;
import masterspringsecurity.domain.dto.user.RegisteredUserDto;
import masterspringsecurity.domain.dto.user.request.AuthenticationRequest;
import masterspringsecurity.domain.dto.user.request.SaveUserRequest;
import masterspringsecurity.domain.entity.UserEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                                                        authRequest.getPassword());
        authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = userService.findOneByUsername(authRequest.getUsername())
                                             .get();
        String jwt = jwtService.generateTokenv2(userDetails,
                                                generateExtraClaims((UserEntity) userDetails));

        return AuthenticationResponse.builder()
                                     .jwt(jwt)
                                     .build();
    }

    public RegisteredUserDto registerOneCustomer(SaveUserRequest newUser) {
        UserEntity userEntity = userService.registerOneCustomer(newUser);

        String jwt = jwtService.generateTokenv2(userEntity,
                                                generateExtraClaims(userEntity));
        return RegisteredUserDto.builder()
                                .id(userEntity.getId())
                                .username(userEntity.getUsername())
                                .name(userEntity.getName())
                                .role(userEntity.getRole()
                                                .name())
                                .jwt(jwt)
                                .build();
    }

    private Map<String, Object> generateExtraClaims(UserEntity userEntity) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name",
                        userEntity.getName());
        extraClaims.put("role",
                        userEntity.getRole()
                                  .name());
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
        return userService.findOneByUsername(username)
                          .orElseThrow(() -> new ObjectNotFoundException("User not found"));
    }
}
