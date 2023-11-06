package masterspringsecurity.config.security;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.common.util.RolePermission;
import masterspringsecurity.config.security.filter.JwtAuthenticationFilter;
import masterspringsecurity.config.security.handler.CustomAccessDeniedHandler;
import masterspringsecurity.config.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
        http.authorizeHttpRequests(HttpSecurityConfig::buildRequestMatchersv3);
        http.addFilterBefore(jwtAuthenticationFilter,
                             UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(exceptionConfig -> {
            exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint);
            exceptionConfig.accessDeniedHandler(customAccessDeniedHandler);
        });
        return http.build();
    }

    private static void buildRequestMatchersv2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*products*/
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/products")
                     .hasAuthority(RolePermission.READ_ALL_PRODUCTS.name());
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/products/{productId}")
                     /*authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET,
                                                                                    "/products/[0-9]*"))*/
                     .hasAuthority(RolePermission.READ_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/products")
                     .hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/products/{productId}")
                     .hasAuthority(RolePermission.UPDATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/products/{productId}/disabled")
                     .hasAuthority(RolePermission.DISABLE_ONE_PRODUCT.name());

        /*categories*/
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/categories")
                     .hasAuthority(RolePermission.READ_ALL_CATEGORIES.name());
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/categories/{categoryId}")
                     .hasAuthority(RolePermission.READ_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/categories")
                     .hasAuthority(RolePermission.CREATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/categories/{categoryId}")
                     .hasAuthority(RolePermission.UPDATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/categories/{categoryId}/disabled")
                     .hasAuthority(RolePermission.DISABLE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/auth/profile")
                     .hasAuthority(RolePermission.READ_MY_PROFILE.name());
        /*public*/
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/customers")
                     .permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/auth/login")
                     .permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/auth/validate")
                     .permitAll();
        authReqConfig.anyRequest()
                     .authenticated();
    }

    private static void buildRequestMatchersv3(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*public endpoints*/
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/customers")
                     .permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/auth/login")
                     .permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/auth/validate")
                     .permitAll();
        authReqConfig.anyRequest()
                     .authenticated();
    }

    private static Customizer<AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry> builderRequestMatchers() {
        return authConfig -> {
            /* Public resources*/
            authConfig.requestMatchers(HttpMethod.POST,
                                       "/auth/authenticate")
                      .permitAll();
            authConfig.requestMatchers(HttpMethod.GET,
                                       "/auth/public-access")
                      .permitAll();
            authConfig.requestMatchers("/error")
                      .permitAll();
            /* Protected resources*/
            authConfig.requestMatchers(HttpMethod.GET,
                                       "/products")
                      .hasAuthority(RolePermission.READ_ALL_PRODUCTS.name());
            authConfig.requestMatchers(HttpMethod.POST,
                                       "/products")
                      .hasAuthority(RolePermission.CREATE_ONE_PRODUCT.name());

            authConfig.anyRequest()
                      .denyAll();
        };
    }
}
