package filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AdminFilter", urlPatterns = {"/list", "/delete"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User loggedUser = (User) req.getSession().getAttribute("loggedUser");

        /* Проверим, что в случае delete параметр id парсится в Long */
        String action = req.getServletPath();
        if (action.equals("/delete") && FilterUtil.getIdOrNullAndBadRequest(req, resp) == null) {
            return;
        }

        /* Если пользователь вошел в систему и он админ */
        if (loggedUser != null && loggedUser.getRole() != null && loggedUser.getRole().equals("admin")) {
            chain.doFilter(request, response);
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
