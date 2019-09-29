package servlet;

import model.User;

import javax.servlet.http.HttpServletRequest;

final class ServletUtil {
    private ServletUtil() {
    }

    static User getUserFromRequestParam(HttpServletRequest req) {
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
