package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.User;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;
import store.sokolov.innopolis.homework_25.task_1_2.dao.UserDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

@WebServlet(urlPatterns = {"/allusers", "/showuser", "/adduser", "/edituser", "/removeuser", "/unlockuser", "/changeuserpassword"})
public class UserServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(UserServlet.class);
    @Inject
    private UserDao userDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        logger.info("getRequestURI = {}", requestURI);
        if ("/allusers".equals(requestURI)) {
            List<User> listUser = userDao.getAllUsers();
            req.setAttribute("users", listUser);
            req.setAttribute("PageTitle", "Users");
            req.setAttribute("PageBody", "allusers.jsp");
        } else if ("/showuser".equals(requestURI) || "/edituser".equals(requestURI) || "/removeuser".equals(requestURI)) {
            String id = req.getParameter("id");
            logger.info("id = {}", id);
            if (id == null) {
                throw new ServletException("Нет параметра id");
            }
            User user = null;
            try {
                user = userDao.getUser(Long.valueOf(id));
                if ("/showuser".equals(requestURI)) {
                    req.setAttribute("PageTitle", "User");
                    req.setAttribute("PageBody", "showuser.jsp");
                } else if ("/edituser".equals(requestURI)) {
                    req.setAttribute("PageTitle", "Edit User");
                    req.setAttribute("PageBody", "edituser.jsp");
                } else if ("/removeuser".equals(requestURI)) {
                    req.setAttribute("PageTitle", "Remove User");
                    req.setAttribute("PageBody", "removeuser.jsp");
                }
            } catch (Exception exception) {
                resp.setStatus(404);
                req.setAttribute("PageBody", "notfound.jsp");
            }
            req.setAttribute("user", user);
        } else if ("/adduser".equals(requestURI)) {
            req.setAttribute("PageTitle", "Add User");
            req.setAttribute("PageBody", "formadduser.jsp");
        }
        req.getRequestDispatcher("/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("utf-8");
        String method = req.getParameter("_method");
        if (method == null || method.isEmpty()) {
            throw new ServletException("Не задан метод");
        }

        if ("post".equals(method) || "put".equals(method)) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String name = req.getParameter("name");
            String fullName = req.getParameter("fullName");
            // TODO: нужен апдейт без поля isLock
            User user = new User(login, name, false, fullName);
            user.setPassword(DigestUtils.md5Hex(password));
            userDao.addUser(user);
        } else if ("delete".equals(method)) {

        }

        resp.sendRedirect(req.getContextPath() + "/allusers");
    }
}
