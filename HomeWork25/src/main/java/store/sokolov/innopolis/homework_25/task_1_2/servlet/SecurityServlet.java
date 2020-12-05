package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.dao.UserDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/security_check")
public class SecurityServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(CheckObjectServlet.class);
    @Inject
    private UserDao userDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        logger.info("login = {}", login);
        if (login == null || password == null) {
            throw new ServletException("Доступ запрещен");
        }
        password = DigestUtils.md5Hex(password);
        Boolean isAccessDenied = userDao.isAccessDenied(login, password);
        if (isAccessDenied) {
            // доступ запрещен
        }

        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("login", login);

        req.setAttribute("PageTitle", "Main page");
        req.setAttribute("PageBody", "main.jsp");
        req.getRequestDispatcher("/layout.jsp").forward(req, resp);
    }
}
