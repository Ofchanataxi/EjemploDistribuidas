package ec.edu.espe.autorms.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig {
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((oauthHttp)-> oauthHttp
                        .requestMatchers(HttpMethod.GET, "/authorized").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/autores/**").hasAuthority("SCOPE_read")
                        .requestMatchers(HttpMethod.POST, "/api/autores/**").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.PUT, "/api/autores/**").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.DELETE, "/api/autores/**").hasAuthority("SCOPE_write")
                        .anyRequest().authenticated())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2Login((login) -> login.loginPage("/oauth2/authorization/autor-ms"))
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                    .jwt(Customizer.withDefaults())
                );
        return http.build();
    }
}
