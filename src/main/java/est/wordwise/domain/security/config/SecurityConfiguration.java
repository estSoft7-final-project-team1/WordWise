package est.wordwise.domain.security.config;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenFilter jwtTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(AbstractHttpConfigurer::disable)
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login")
                .permitAll()
            )
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/login", "/signup", "/signin", "/chat", "/chat-endpoint",
                        "/check-email", "/check-nickname").permitAll()
                            .requestMatchers("/ws/**").permitAll()
                    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                    .anyRequest().hasAnyAuthority("ROLE_MEMBER", "ROLE_ADMIN")
//                        .anyRequest().authenticated()

            )
            .exceptionHandling(
                exception -> exception.authenticationEntryPoint(
                    (req, resp, ex) -> {
                        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        resp.setContentType("application/json");
                        resp.getWriter().write("{\"error\": \"Unauthorized\"}");
                    }
                )
            )
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class) // 질문
            .logout(LogoutConfigurer::permitAll)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
