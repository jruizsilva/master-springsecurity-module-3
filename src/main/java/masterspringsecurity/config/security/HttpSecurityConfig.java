package masterspringsecurity.config.security;

import lombok.RequiredArgsConstructor;
import masterspringsecurity.common.util.RolePermissionEnum;
import masterspringsecurity.config.security.filter.JwtAuthenticationFilter;
import masterspringsecurity.config.security.handler.CustomAccessDeniedHandler;
import masterspringsecurity.config.security.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class HttpSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final AuthorizationManager<RequestAuthorizationContext> authorizationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authenticationProvider(authenticationProvider);
        http.headers(httpSecurityHeadersConfigurer -> {
            httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
        });
        http.authorizeHttpRequests(authReqConfig -> {
            authReqConfig.anyRequest()
                         .access(authorizationManager);
        });
        http.addFilterBefore(jwtAuthenticationFilter,
                             UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(exceptionConfig -> {
            exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint);
            exceptionConfig.accessDeniedHandler(customAccessDeniedHandler);
        });
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",
                                         configuration);
        return source;
    }

    private static void buildRequestMatchersv2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
        /*products*/
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/products")
                     .hasAuthority(RolePermissionEnum.READ_ALL_PRODUCTS.name());
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/products/{productId}")
                     /*authReqConfig.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET,
                                                                                    "/products/[0-9]*"))*/
                     .hasAuthority(RolePermissionEnum.READ_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/products")
                     .hasAuthority(RolePermissionEnum.CREATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/products/{productId}")
                     .hasAuthority(RolePermissionEnum.UPDATE_ONE_PRODUCT.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/products/{productId}/disabled")
                     .hasAuthority(RolePermissionEnum.DISABLE_ONE_PRODUCT.name());

        /*categories*/
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/categories")
                     .hasAuthority(RolePermissionEnum.READ_ALL_CATEGORIES.name());
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/categories/{categoryId}")
                     .hasAuthority(RolePermissionEnum.READ_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.POST,
                                      "/categories")
                     .hasAuthority(RolePermissionEnum.CREATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/categories/{categoryId}")
                     .hasAuthority(RolePermissionEnum.UPDATE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.PUT,
                                      "/categories/{categoryId}/disabled")
                     .hasAuthority(RolePermissionEnum.DISABLE_ONE_CATEGORY.name());
        authReqConfig.requestMatchers(HttpMethod.GET,
                                      "/auth/profile")
                     .hasAuthority(RolePermissionEnum.READ_MY_PROFILE.name());
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
                      .hasAuthority(RolePermissionEnum.READ_ALL_PRODUCTS.name());
            authConfig.requestMatchers(HttpMethod.POST,
                                       "/products")
                      .hasAuthority(RolePermissionEnum.CREATE_ONE_PRODUCT.name());

            authConfig.anyRequest()
                      .denyAll();
        };
    }
}
