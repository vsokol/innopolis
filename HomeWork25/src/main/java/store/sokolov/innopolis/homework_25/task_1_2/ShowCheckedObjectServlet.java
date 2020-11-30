package store.sokolov.innopolis.homework_25.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.dao.ICheckedObjectDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/showcheckedobject")
public class ShowCheckedObjectServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(ShowCheckedObjectServlet.class);
    @Inject
    private ICheckedObjectDao checkedObjectDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        String id = (String)req.getAttribute("id");
        logger.info("id = {}", id);
        ICheckedObject checkedObject = checkedObjectDao.getCheckedObjectById(Long.valueOf(id));
        req.setAttribute("checkedobject", checkedObject);
        req.getRequestDispatcher("WEB-INF/jsp/showcheckedobject.jsp").forward(req, resp);
    }
}
