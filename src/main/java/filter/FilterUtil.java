package filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

final class FilterUtil {

    private FilterUtil() {
    }

    /**
     * Если в реквесте есть параметр id и он парсится в Long, то возвращаем его в Long,
     * иначе выдаем SC_BAD_REQUEST и возвращаем null.
     */
    static Long getIdOrNullAndBadRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            return Long.valueOf(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}
