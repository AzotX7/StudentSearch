package com.azot.course.util;

import com.azot.course.DTO.UserDTO;
import com.azot.course.user.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class RoleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String path = httpRequest.getRequestURI();
        HttpSession session = httpRequest.getSession(false);
        UserDTO userDTO = (session != null) ? (UserDTO) session.getAttribute("user") : null;

        if (path.contains("/login") || path.contains("/register") || path.contains("/logout") || path.equals(httpRequest.getContextPath() + "/")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (userDTO != null) {
            Role role = userDTO.getRole();

            if (role == Role.ADMIN) {
                filterChain.doFilter(servletRequest, httpResponse);
                return;
            } else if (role == Role.USER) {
                if (path.contains("/materials") || path.contains("/comments") || path.contains("/profile") || path.contains("/myMaterials")) {
                    filterChain.doFilter(servletRequest, httpResponse);
                    return;
                } else {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/materials");
                    return;
                }
            }
        }

        if (userDTO == null && !path.contains("/login")) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else {
            filterChain.doFilter(servletRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {
    }
}