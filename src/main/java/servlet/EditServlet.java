package servlet;

import model.User;
import service.UserService;
import util.DBService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditServlet", urlPatterns = {"/edit", "/new"})
public class EditServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = UserService.getInstance(DBService.getUserDaoCreator());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("edit-form.jsp");

        String action = req.getServletPath();
        if (action.equals("/edit")) {
            Long id = Long.valueOf(req.getParameter("id")); // коррекность id проверяется в фильтре
            // TODO: (?) Где проверять что такой id есть в базе? Здесь или в фильтре "/edit"?
            User existingUser = userService.getUser(id);
            req.setAttribute("user", existingUser);
        }
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();
        User updatedUser = ServletUtil.getUserFromRequestParam(req);

        if (action.equals("/edit")) {
            User loggedUser = (User) req.getSession().getAttribute("loggedUser");

            /* Обновляем юзера в базе, и если он изменил свой собственный профиль то обновляем атрибут loggedUser в сессии */
            if (userService.updateUser(updatedUser)) {
                if (updatedUser.getId().equals(loggedUser.getId())) {
                    req.getSession().setAttribute("loggedUser", updatedUser);
                }
            }
        } else {
            userService.addUser(updatedUser);
        }
        resp.sendRedirect(req.getContextPath());
    }
}
