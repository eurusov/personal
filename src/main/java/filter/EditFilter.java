package filter;

import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "EditFilter", urlPatterns = {"/edit"})
public class EditFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User loggedUser = (User) req.getSession().getAttribute("loggedUser");

        /* Если пользователь не вошел в систему */
        if (loggedUser == null || loggedUser.getId() == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String role = loggedUser.getRole();

        /* Проверим, что параметр id парсится в Long */
        Long id = FilterUtil.getIdOrNullAndBadRequest(req, resp);
        if (id == null) {
            return;
        }

        // TODO: (?) Где проверять что такой id есть в базе? Здесь или в обрабобтчике "/edit"?

        /* Если user является админом,
        или user собирается редактировать свой собственный профиль */
        if (role.equals("admin") || id.equals(loggedUser.getId())) {
            chain.doFilter(request, response);
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
