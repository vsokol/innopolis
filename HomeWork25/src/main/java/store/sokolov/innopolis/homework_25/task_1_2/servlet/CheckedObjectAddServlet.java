package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/addcheckedobject")
public class CheckedObjectAddServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(CheckedObjectAddServlet.class);
    @Inject
    private CheckedObjectDao checkedObjectDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        req.setAttribute("PageTitle", "Add Checked Object");
        req.setAttribute("PageBody", "formaddcheckedobject.jsp");
        req.getRequestDispatcher("/layout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        req.setCharacterEncoding("utf-8");
        String name = req.getParameter("name");
        String descr = req.getParameter("descr");
        CheckedObject checkedObject = new CheckedObject(name, descr);
        checkedObjectDao.addCheckedObject(checkedObject);

        resp.sendRedirect(req.getContextPath() + "/allcheckedobject");
    }
}
