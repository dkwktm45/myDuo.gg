package com.project.MyDuo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.MyDuo.jwt.JwtTokenUtil;
import com.project.MyDuo.security.filter.ExceptionHandlerFilter;
import com.project.MyDuo.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig{

    //private final JwtEntryPoint jwtEntryPoint;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService customUserDetailService;

    private final ObjectMapper objectMapper;

    private final String[] AUTH_WHITELLIST = {
            "/account/", "/account/join", "/account/login",
            "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/ws-stomp/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                .authorizeHttpRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(AUTH_WHITELLIST).permitAll()
                .anyRequest().authenticated()

                .and().headers().frameOptions().sameOrigin()

                //.and()
                //.exceptionHandling()
                    //.authenticationEntryPoint(jwtEntryPoint)
                .and().cors().configurationSource(corsConfigurationSource())

                .and()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)

                .and()
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenUtil, customUserDetailService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlerFilter(objectMapper), JwtAuthenticationFilter.class)
                .csrf().disable()
                    .httpBasic();
                
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(AUTH_WHITELLIST);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //2개부족

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.addAllowedHeader("*");
        configuration.setMaxAge((long) 3600);
        configuration.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
