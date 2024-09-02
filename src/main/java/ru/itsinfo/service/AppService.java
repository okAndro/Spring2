package ru.itsinfo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.itsinfo.config.exception.LoginException;
import ru.itsinfo.model.Role;
import ru.itsinfo.model.User;

import jakarta.servlet.http.HttpSession;  // Замените javax.servlet на jakarta.servlet
import java.util.List;

public interface AppService extends UserDetailsService {
    List<User> findAllUsers();

    User findUser(Long userId) throws NullPointerException;

    void deleteUser(Long userId);

    List<Role> findAllRoles();

    void authenticateOrLogout(Model model, HttpSession session, LoginException authenticationException, String authenticationName);

    boolean saveUser(User user, BindingResult bindingResult, Model model);
}
