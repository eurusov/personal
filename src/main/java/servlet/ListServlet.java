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

@WebServlet(name = "ListServlet", urlPatterns = {"/list"})
public class ListServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        if (userService == null) {
            userService = UserService.getInstance(DBService.getUserDaoCreator());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            showUserList(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            showUserList(req, resp);
        } catch (DBException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void showUserList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, DBException {
        User loggedUser = (User) req.getSession().getAttribute("loggedUser");
        if (loggedUser == null || loggedUser.getId() == null) {
            resp.sendRedirect(req.getContextPath());
        } else if (!loggedUser.getRole().equals("admin")) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("user-page.jsp");
            dispatcher.forward(req, resp);
        } else {
            List<User> listUser;
            listUser = userService.getAllUser();
            req.setAttribute("listUser", listUser);
            RequestDispatcher dispatcher = req.getRequestDispatcher("user-list.jsp");
            dispatcher.forward(req, resp);
        }
    }
}
