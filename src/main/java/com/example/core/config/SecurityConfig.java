package com.example.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;

import com.example.core.filter.JwtFilter;
import com.example.core.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;
    
    @Autowired 
    private CustomUserDetailsService userDetailsService;

    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(withDefaults())
                .csrf(csrf -> csrf
                    .ignoringRequestMatchers("/h2-console/**") // allow POSTs to H2 console
                    .disable()
                )
                .headers(headers -> headers
                    .frameOptions(frame -> frame.sameOrigin()) // allow H2 iframe
                )
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/register", "/auth/login").permitAll()
                    .requestMatchers("/h2-console/**").permitAll() // ✅ allow H2 access                    
                    .requestMatchers("/accounts/**").authenticated()
                    .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .userDetailsService(userDetailsService)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
