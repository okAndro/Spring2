package ru.itsinfo.config.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException; // Обратите внимание на изменение
import jakarta.servlet.http.HttpServletRequest; // Обратите внимание на изменение
import jakarta.servlet.http.HttpServletResponse; // Обратите внимание на изменение
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        // Что-то залогировать...
        response.sendRedirect(request.getContextPath() + "/403");
    }
}
