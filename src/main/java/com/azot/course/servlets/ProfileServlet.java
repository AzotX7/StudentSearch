package com.azot.course.servlets;



import com.azot.course.DTO.MaterialDTO;
import com.azot.course.DTO.UserDTO;
import com.azot.course.models.User;
import com.azot.course.service.MaterialService;
import com.azot.course.service.UserService;
import com.azot.course.database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/profile/*")
public class ProfileServlet extends HttpServlet {

    private final UserService userService;
    private final MaterialService materialService;

    public ProfileServlet() throws SQLException {
        this.userService = new UserService(Database.getConnection());
        this.materialService = new MaterialService(Database.getConnection());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

        if (userDTO == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            switch (action == null ? "/" : action) {
                case "/":
                    displayUserProfile(request, response, userDTO);
                    break;

                case "/edit":
                    showEditProfilePage(request, response, userDTO);
                    break;

                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Неизвестная команда");
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();

        if ("/update".equals(action)) {
            UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

            if (userDTO == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            String username = request.getParameter("username");
            String email = request.getParameter("email");


            if (isValidProfileData(username, email)) {
                try {
                    updateUserProfile(request, response, userDTO, username, email);
                } catch (SQLException e) {
                    request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Имя пользователя и email не могут быть пустыми.");
                request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            }
        }
    }

    private void updateUserProfile(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO,
                                   String username, String email) throws SQLException, ServletException, IOException {

        if (userService.isUsernameExists(username) && !userDTO.getUsername().equals(username)) {
            request.setAttribute("errorMessage", "Имя пользователя уже существует.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        if (userService.isEmailExists(email) && !userDTO.getEmail().equals(email)) {
            request.setAttribute("errorMessage", "Email уже существует.");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }

        UserDTO updatedUser = userService.getUserById(userDTO.getId());
        updatedUser.setUsername(username);
        updatedUser.setEmail(email);

        userService.updateUser(updatedUser);
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        request.getSession().setAttribute("user", userDTO);

        response.sendRedirect(request.getContextPath() + "/profile");
    }

    private void displayUserProfile(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO)
            throws ServletException, IOException {
        try {
            User user = userService.getFullUserByUsername(userDTO.getUsername());
            List<MaterialDTO> userMaterials = materialService.getMaterialsByUserId(user.getId());

            request.setAttribute("user", userDTO);
            request.setAttribute("materials", userMaterials);
            request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Ошибка при обработке запроса: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    private void showEditProfilePage(HttpServletRequest request, HttpServletResponse response, UserDTO userDTO)
            throws ServletException, IOException {
        request.setAttribute("user", userDTO);
        request.getRequestDispatcher("/WEB-INF/views/editProfile.jsp").forward(request, response);
    }

    private boolean isValidProfileData(String username, String email) {
        return username != null && !username.trim().isEmpty() && email != null && !email.trim().isEmpty();
    }
}
