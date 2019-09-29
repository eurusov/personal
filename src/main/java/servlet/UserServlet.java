package servlet;

import model.User;
import service.DBException;
import service.UserService;
import util.DBService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/"})
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = UserService.getInstance(DBService.getUserDaoCreator());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        try {
            switch (action) {
                case "/insert":
                    insertUserIntoDB(req, resp);
                    break;
                case "/login":
                    doLogin(req, resp);
                    break;
                default:
                    doGet(req, resp);
            }
        } catch (DBException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(req, resp);
                    break;
                case "/delete":
                    deleteUserFromDB(req, resp);
                    break;
                case "/edit":
                    showEditForm(req, resp);
                    break;
                case "/logout":
                    doLogout(req, resp);
                    break;
                default:
                    userEntryPoint(req, resp);
                    break;
            }
        } catch (DBException e) {
            throw new ServletException(e);
        }
    }

    private void doLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        showLoginForm(req, resp);
    }

    private void userEntryPoint(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, DBException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        if (loggedUser == null || loggedUser.getId() == null) {
            showLoginForm(req, resp);
        } else if (loggedUser.getRole().equals("admin")) {
            resp.sendRedirect(req.getContextPath() + "/list");
//            RequestDispatcher dispatcher = req.getRequestDispatcher("/list");
//            dispatcher.forward(req, resp);
        } else {
            showUserWelcomeForm(req, resp);
        }
    }

    private void doLogin(HttpServletRequest req, HttpServletResponse resp) throws DBException, IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = userService.getUser(email, password);
        if (user != null) {
            req.getSession().setAttribute("loggedUser", user); // save loggedUser in httpSession
            userEntryPoint(req, resp);
        }
    }

    private void showUserWelcomeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("user-page.jsp");
        dispatcher.forward(req, resp);
    }

    private void showLoginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("login.jsp");
        dispatcher.forward(req, resp);
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("edit-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException, DBException {
        Long id = Long.valueOf(req.getParameter("id"));
        User existingUser = userService.getUser(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("edit-form.jsp");
        req.setAttribute("user", existingUser);
        dispatcher.forward(req, resp);
    }

    private void insertUserIntoDB(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, DBException {
        userService.addUser(getUserFromRequestParam(req));
        resp.sendRedirect("");
    }

    private void deleteUserFromDB(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, DBException {
        Long id = Long.valueOf(req.getParameter("id"));
        userService.deleteUser(id);
        resp.sendRedirect("list"); // TODO: add ability to user delete himself and change "list" to "" here
    }

    private User getUserFromRequestParam(HttpServletRequest req) {
        String idStr = req.getParameter("id");
        Long id = (idStr == null) ? null : Long.valueOf(idStr);
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String country = req.getParameter("country");
        String role = req.getParameter("role");
        return new User(id, email, password, firstName, lastName, country, role);
    }
}
