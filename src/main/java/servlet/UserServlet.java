package servlet;

import dao.*;
import daoContext.DaoContext;
import daoContext.HibernateSession;
import daoContext.producer.DaoContextProducer;
import daoContext.producer.SessionProducer;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import service.UserService;
import util.DBException;
import util.DBService;
import util.DaoType;
import util.StringConst;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@WebServlet("/")
public class UserServlet extends HttpServlet {
//    private DaoType daoType;

//    public void init() {
//    }

    public UserDao getUserDao() {
        if (DBService.daoType == DaoType.HIBERNATE) {
            DaoContextProducer sessionProducer = SessionProducer.getInstance();
            return new UserDaoHibernate(sessionProducer.getDaoContext());
        } else if (DBService.daoType == DaoType.JDBC) {
            DaoContext daoContext = DBService.getNewConnection();
            return new UserDaoJdbc(daoContext);
        }
        return null;
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
                default:
                    listUser(request, response);
                    break;
            }
        } catch (SQLException | DBException ex) {
            throw new ServletException(ex);
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = null;
        try (UserDao userDao = getUserDao()) {
            listUser = userDao.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            throws SQLException, ServletException, IOException, DBException {
        User existingUser = null;
        try (UserDao userDao = getUserDao()) {
            long id = Long.parseLong(request.getParameter("id"));
            existingUser = userDao.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, DBException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        User newUser = new User(firstName, lastName, email, country);
        try (UserDao userDao = getUserDao()) {
            userDao.addUser(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("list");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, DBException {
        Long id = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User user = new User(id, firstName, lastName, email, country);
        try (UserDao userDao = getUserDao()) {
            userDao.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("list");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, DBException {
        long id = Long.parseLong(request.getParameter("id"));
        try (UserDao userDao = getUserDao()) {
            userDao.deleteUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("list");
    }
}
