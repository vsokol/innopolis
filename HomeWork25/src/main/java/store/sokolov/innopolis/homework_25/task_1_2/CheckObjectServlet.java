package store.sokolov.innopolis.homework_25.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;
import store.sokolov.innopolis.homework_25.task_1_2.dao.ICheckedObjectDao;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager.getInstance;

@WebServlet(urlPatterns = "/")
public class CheckObjectServlet extends HttpServlet {
    final private static Logger logger = LoggerFactory.getLogger(CheckObjectServlet.class);
    @Inject
    private ICheckedObjectDao checkedObjectDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        try {
            List<ICheckedObject> listAllCheckedObject = checkedObjectDao.getAllCheckedObject();
            req.setAttribute("listAllCheckedObject", listAllCheckedObject);
            logger.debug(String.valueOf(listAllCheckedObject));
            req.getRequestDispatcher("WEB-INF/jsp/allcheckedobject.jsp").forward(req, resp);
        } catch (SQLException exception) {
            logger.error("Ошибка в CheckObjectServlet.doGet", exception);
        }
        super.doGet(req, resp);
    }
}
