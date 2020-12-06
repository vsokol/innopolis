package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/editcheckedobject")
public class CheckedObjectEditServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(CheckedObjectEditServlet.class);
    @Inject
    private CheckedObjectDao checkedObjectDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CheckAccess.check(req, resp);
        String id = req.getParameter("id");
        logger.info("id = {}", id);
        if (id == null) {
            throw new ServletException("Нет параметра id");
        }
        ICheckedObject checkedObject = null;
        try {
            req.setAttribute("PageTitle", "Edit Checked Object");
            checkedObject = checkedObjectDao.getCheckedObject(Long.valueOf(id));
            req.setAttribute("PageBody", "formeditcheckedobject.jsp");
        } catch (Exception exception) {
            resp.setStatus(404);
            req.setAttribute("PageBody", "notfound.jsp");
        }
        req.setAttribute("checkedobject", checkedObject);
        req.getRequestDispatcher("/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CheckAccess.check(req, resp);
        req.setCharacterEncoding("utf-8");
        String method = req.getParameter("_method");
        if (method == null || method.isEmpty() || !"put".equals(method)) {
            throw new ServletException("Не задан метод");
        }
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String descr = req.getParameter("descr");
        CheckedObject checkedObject = new CheckedObject(Long.valueOf(id), name, descr);
        checkedObjectDao.updateCheckedObject(checkedObject);

        resp.sendRedirect(req.getContextPath() + "/allcheckedobject");
    }
}
