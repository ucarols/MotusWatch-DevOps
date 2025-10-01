package com.mottu.motuswatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService users(PasswordEncoder enc) {
        UserDetails admin = User.withUsername("admin")
                .password(enc.encode("admin123"))
                .authorities("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password(enc.encode("user123"))
                .authorities("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/login", "/error", "/acesso-negado").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/usuarios/**").hasAuthority("ADMIN")
                        .requestMatchers("/motos/*/excluir").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/motos/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/motos/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/usuarios/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/motos", true)
                        .permitAll()
                )
                .logout(l -> l.logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .headers(h -> h.frameOptions(o -> o.disable()))
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .exceptionHandling(e -> e.accessDeniedHandler((req, res, ex) ->
                        res.sendRedirect(req.getContextPath() + "/acesso-negado")
                ));

        return http.build();
    }
}
