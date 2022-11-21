package ru.job4j.dreamjob.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * AuthFilter - сервлетный фильтр
 *
 * @author Ilya Kaltygin
 */

@Component
public class AuthFilter implements Filter {
    private static final Set<String> URLS = Set.of("loginPage", "login", "formAddUser", "index", "success", "fail", "registration");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (checkURL(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    private boolean checkURL(String uri) {
        return AuthFilter.URLS.stream()
                .anyMatch(uri::endsWith);
    }
}
