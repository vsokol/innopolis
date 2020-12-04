package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/removecheckedobject")
public class CheckedObjectRemoveServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(CheckedObjectRemoveServlet.class);
    @Inject
    private CheckedObjectDao checkedObjectDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        String id = req.getParameter("id");
        logger.info("id = {}", id);
        if (id == null) {
            throw new ServletException("Нет параметра id");
        }
        ICheckedObject checkedObject = null;
        try {
            req.setAttribute("PageTitle", "Remove Checked Object");
            checkedObject = checkedObjectDao.getCheckedObject(Long.valueOf(id));
            req.setAttribute("PageBody", "formremovecheckedobject.jsp");
        } catch (Exception exception) {
            resp.setStatus(404);
            req.setAttribute("PageBody", "notfound.jsp");
        }
        req.setAttribute("checkedobject", checkedObject);
        req.getRequestDispatcher("/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        String id = req.getParameter("id");
        logger.info("id = {}", id);
        if (id == null) {
            throw new ServletException("Нет параметра id");
        }
        String method = req.getParameter("_method");
        if (method == null || method.isEmpty() || !"delete".equals(method)) {
            throw new ServletException("Не задан метод");
        }
        ICheckedObject checkedObject;
        try {
            req.setAttribute("PageTitle", "Remove Checked Object");
            checkedObject = checkedObjectDao.getCheckedObject(Long.valueOf(id));
            checkedObjectDao.removeCheckedObject(checkedObject);
            req.setAttribute("PageBody", "allcheckedobject.jsp");
        } catch (Exception exception) {
            resp.setStatus(404);
            req.setAttribute("PageBody", "notfound.jsp");
        }

        resp.sendRedirect(req.getContextPath() + "/allcheckedobject");
    }
}
