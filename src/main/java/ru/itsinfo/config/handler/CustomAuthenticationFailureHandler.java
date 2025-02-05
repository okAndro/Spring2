package ru.itsinfo.config.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import ru.itsinfo.config.exception.LoginException;

import jakarta.servlet.ServletException; // Обратите внимание на изменение
import jakarta.servlet.http.HttpServletRequest; // Обратите внимание на изменение
import jakarta.servlet.http.HttpServletResponse; // Обратите внимание на изменение
import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public CustomAuthenticationFailureHandler() {
        super("/?error");
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        // Запишем, чтобы не вводить заново ошибочные данные формы
        if (isAllowSessionCreation()) {
            LoginException loginException = new LoginException(exception.getMessage());
            request.getParameterMap().entrySet().forEach((entry) -> {
                if (entry.getKey().equals("email")) {
                    loginException.setEmail(entry.getValue()[0]);
                } else if (entry.getKey().equals("password")) {
                    loginException.setPassword(entry.getValue()[0]);
                }
            });

            request.getSession().setAttribute("Authentication-Exception", loginException);
        }

        super.onAuthenticationFailure(request, response, exception);
    }
}
