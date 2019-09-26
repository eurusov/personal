package servlet;

import model.User;
import service.DBException;
import service.UserService;
import util.DBService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/"})
public class UserServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        if (userService == null) {
            userService = new UserService(DBService.getUserDaoCreator());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/insert":
                    insertUser(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                case "/login":
                    doLogin(request, response);
                default:
                    doGet(request, response);
            }
        } catch (DBException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/list":
                    showUserListForm(request, response);
                    break;
                case "/logout":
                    doLogout(request, response);
                    break;
                default:
                    userEntryPoint(request, response);
                    break;
            }
        } catch (DBException e) {
            throw new ServletException(e);
        }
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        showLoginForm(request, response);
    }

    private void userEntryPoint(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, DBException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        if (loggedUser == null) {
            showLoginForm(req, resp);
        } else if (loggedUser.getRole().equals("admin")) {
            showUserListForm(req, resp);
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

    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    private void showUserListForm(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, DBException {
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        if (loggedUser == null || loggedUser.getId() == null) {
            showLoginForm(request, response);
        } else if (!loggedUser.getRole().equals("admin")) {
            showUserWelcomeForm(request, response);
        } else {
            List<User> listUser = userService.getAllUser();
            request.setAttribute("listUser", listUser);
            RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DBException {
        Long id = Long.valueOf(request.getParameter("id"));
        User existingUser = userService.getUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DBException {
        userService.addUser(getUserFromRequest(request));
        response.sendRedirect("list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DBException {
        User updatedUser = getUserFromRequest(request);
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        if (userService.updateUser(updatedUser)) {
            if (updatedUser.getId().equals(loggedUser.getId())) {
                request.getSession().setAttribute("loggedUser", updatedUser);
            }
        }
        if (loggedUser.getRole().equals("admin")) {
            response.sendRedirect("list");
        } else {
            response.sendRedirect("");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, DBException {
        Long id = Long.valueOf(request.getParameter("id"));
        userService.deleteUser(id);
        response.sendRedirect("list");
    }

    private User getUserFromRequest(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        Long id = (idStr == null) ? null : Long.valueOf(idStr);
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String country = request.getParameter("country");
        String role = request.getParameter("role");
        return new User(id, email, password, firstName, lastName, country, role);
    }
}
