package ru.itsinfo.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException; // Обратите внимание на изменение
import jakarta.servlet.http.HttpServletRequest; // Обратите внимание на изменение
import jakarta.servlet.http.HttpServletResponse; // Обратите внимание на изменение
import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    // Spring Security использует объект Authentication, пользователя авторизованной сессии.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            setDefaultTargetUrl("/admin");
        } else if(roles.contains("ROLE_USER")) {
            setDefaultTargetUrl("/user");
        } else {
            setDefaultTargetUrl("/");
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
