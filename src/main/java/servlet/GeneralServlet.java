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

@WebServlet(name = "GeneralServlet", urlPatterns = {"/"})
public class GeneralServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = UserService.getInstance(DBService.getUserDaoCreator());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        switch (action) {
            case "/delete":
                deleteUserFromDB(req, resp);
                break;
            case "/logout":
                doLogout(req, resp);
                break;
            default:
                userEntryPoint(req, resp);
        }
    }

    private void userEntryPoint(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        if (loggedUser == null || loggedUser.getId() == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (loggedUser.getRole().equals("admin")) {
            resp.sendRedirect(req.getContextPath() + "/list");
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("user-page.jsp");
            dispatcher.forward(req, resp);
        }
    }

    private void deleteUserFromDB(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long id = Long.valueOf(req.getParameter("id")); // коррекность id проверяется в фильтре
        userService.deleteUser(id);
        resp.sendRedirect("list"); // TODO: add ability to user delete himself and change "list" to "" here
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath());
    }
}
