package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;
import store.sokolov.innopolis.homework_25.task_1_2.exception.NoDataFoundException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/showcheckedobject", name = "checkedobject")
public class ShowCheckedObjectServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(ShowCheckedObjectServlet.class);
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
            req.setAttribute("PageTitle", "Checked Object");
            checkedObject = checkedObjectDao.getCheckedObject(Long.valueOf(id));
            req.setAttribute("PageBody", "showcheckedobject.jsp");
        } catch (Exception exception) {
            resp.setStatus(404);
            req.setAttribute("PageBody", "notfound.jsp");
        }
        req.setAttribute("checkedobject", checkedObject);
        req.getRequestDispatcher("/layout.jsp")
                .forward(req, resp);
    }
}
