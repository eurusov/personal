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
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/"})
public class UserServlet extends HttpServlet {

    private UserService userService;

    public void init() {
        if (userService == null) {
            userService = new UserService(DBService.getUserDaoCreator());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertUser(request, response);
                    break;
                case "/delete":
                    deleteUser(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateUser(request, response);
                    break;
                case "/list":
                    listUser(request, response);
                case "/login":
                    doLogin(request, response);
                default:
                    login(request, response);
                    break;
            }
        } catch (DBException e) {
            throw new ServletException(e);
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws DBException, IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
//        System.out.println("email: " + email);
//        System.out.println("password: " + password);
        User user = userService.getUser(email, password);
        if (user != null) {
            if (user.getRole().equals("admin")) {
                listUser(request, response);
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
                request.setAttribute("user", user);
                dispatcher.forward(request, response);

            }

        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException, DBException {
        List<User> listUser = userService.getAllUser();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, DBException {
        Long id = Long.valueOf(request.getParameter("id"));
        User existingUser = userService.getUser(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
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
        userService.updateUser(getUserFromRequest(request));
        response.sendRedirect("list");
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
