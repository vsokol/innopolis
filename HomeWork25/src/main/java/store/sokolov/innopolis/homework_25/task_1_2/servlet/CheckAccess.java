package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckAccess {
    static void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String login = (String) httpSession.getAttribute("login");
        if (login == null || login.isEmpty()) {
            resp.setStatus(404);
            req.setAttribute("PageTitle", "Access denied");
            req.setAttribute("PageBody", "access_denied.jsp");
            req.getRequestDispatcher("/layout.jsp").forward(req, resp);
        }
    }
}
