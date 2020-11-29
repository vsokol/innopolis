package store.sokolov.innopolis.homework_25.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;

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
    //final private static String url = "jdbc:postgresql://localhost:5432/testDB?user=postgres&password=Asdf4321";
    //final private static String url = "jdbc:postgresql://host.docker.internal:5432/testDB?user=postgres&password=Asdf4321";
    final private static String url = "jdbc:postgresql://172.17.0.2:5432/testDB?user=postgres&password=Asdf4321";
    private List<ICheckedObject> listAllCheckedObject;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("HttpServletRequest = {}, HttpServletResponse = {}", req, resp);
        try {
            listAllCheckedObject = new CheckedObjectDao(getInstance(url)).getAllCheckedObject();
            req.setAttribute("listAllCheckedObject", listAllCheckedObject);
            req.getRequestDispatcher("WEB-INF/jsp/allcheckedobject.jsp").forward(req, resp);
        } catch (SQLException exception) {
            logger.error("Ошибка в CheckObjectServlet.doGet", exception);
        }
        super.doGet(req, resp);
    }
}
