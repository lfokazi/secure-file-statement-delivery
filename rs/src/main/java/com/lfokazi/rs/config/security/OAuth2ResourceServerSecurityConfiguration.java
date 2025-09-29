package com.lfokazi.rs.config.security;

import com.okta.spring.boot.oauth.Okta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfiguration {
    private static final String ROLES_AUTHORITY_CLAIM_KEY = "user_roles";
    private static final String ROLES_AUTHORITY_PREFIX = "ROLE_";

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        var authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName(ROLES_AUTHORITY_CLAIM_KEY);
        authoritiesConverter.setAuthorityPrefix(ROLES_AUTHORITY_PREFIX);

        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

        return converter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(outh2 -> outh2
                .jwt(jwt -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri))));

        Okta.configureResourceServer401ResponseBody(http);

        http.authorizeHttpRequests(authorizeRequests -> {
            authorizeRequests.requestMatchers("/v1/api/**")
                    .hasAnyRole(Roles.Workforce);
            authorizeRequests.requestMatchers("/v1/api/cs/**")
                    .hasAnyRole(Roles.Customer);

            authorizeRequests.anyRequest().authenticated();
        });

        return http.build();
    }
}
