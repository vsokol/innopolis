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
import java.util.List;

@WebServlet(urlPatterns = "/allcheckedobject", name = "checkedobjects")
public class CheckObjectServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(CheckObjectServlet.class);
    @Inject
    private CheckedObjectDao checkedObjectDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        List<ICheckedObject> listAllCheckedObject = checkedObjectDao.getAllCheckedObject();
        req.setAttribute("checkedobjects", listAllCheckedObject);
        req.setAttribute("PageTitle", "Checked Objects");
        req.setAttribute("PageBody", "allcheckedobject.jsp");
        logger.debug(String.valueOf(listAllCheckedObject));
        req.getRequestDispatcher("/layout.jsp").forward(req, resp);
    }
}
