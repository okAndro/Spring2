package ru.itsinfo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.itsinfo.config.handler.CustomAccessDeniedHandler;
import ru.itsinfo.config.handler.CustomAuthenticationFailureHandler;
import ru.itsinfo.config.handler.CustomAuthenticationSuccessHandler;
import ru.itsinfo.config.handler.CustomUrlLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {

    private final UserDetailsService appService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    private final CustomUrlLogoutSuccessHandler urlLogoutSuccessHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public ApplicationSecurityConfig(UserDetailsService appService,
                                     PasswordEncoder passwordEncoder,
                                     CustomAuthenticationSuccessHandler authenticationSuccessHandler,
                                     CustomAuthenticationFailureHandler authenticationFailureHandler,
                                     CustomUrlLogoutSuccessHandler urlLogoutSuccessHandler,
                                     CustomAccessDeniedHandler accessDeniedHandler) {
        this.appService = appService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.urlLogoutSuccessHandler = urlLogoutSuccessHandler;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/", "index", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/") // указываем страницу с формой логина
                        .permitAll()  // даем доступ к форме логина всем
                        .successHandler(authenticationSuccessHandler) // указываем логику обработки при удачном логине
                        .failureHandler(authenticationFailureHandler) // указываем логику обработки при неудачном логине
                        .usernameParameter("email") // Указываем параметры логина и пароля с формы логина
                        .passwordParameter("password")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/?logout")
                        .logoutSuccessHandler(urlLogoutSuccessHandler)
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(accessDeniedHandler)
                )
                .httpBasic();

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appService).passwordEncoder(passwordEncoder);
    }
}
