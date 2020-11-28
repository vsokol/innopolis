package store.sokolov.innopolis.homework_25.task_1_2.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.IConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.dao.DublicateObjectById;
import store.sokolov.innopolis.homework_25.task_1_2.dao.ICheckedObjectDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckedObjectDao implements ICheckedObjectDao {
    private final Logger logger = LoggerFactory.getLogger(CheckedObjectDao.class);
    public final static String SELECT_CHECKED_OBJECT_BY_ID = "select id, parent_id, name, descr from checked_object where id = ?";
    public final static String SELECT_CHECKED_OBJECT_BY_PARENT_ID = "select id, parent_id, name, descr from checked_object where parent_id = ?";
    private final IConnectionManager connectionManager;
    private final Connection connection;


    public CheckedObjectDao(IConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        connection = connectionManager.getConnection();
    }

    @Override
    public ICheckedObject getCheckedObjectById(long id) throws SQLException {
        String sMethodName = "getCheckedObjectById";
        logger.info("{}: Старт...", sMethodName);
        try {
            logger.debug("{}: sql = {}", sMethodName, SELECT_CHECKED_OBJECT_BY_ID);
            PreparedStatement statement = connection.prepareStatement(SELECT_CHECKED_OBJECT_BY_ID);
            statement.setLong(1, id);
            logger.debug("{}: Параметр 1 (id) = {}", sMethodName, id);
            ResultSet resultSet = statement.executeQuery();

            ICheckedObject checkedObject = null;
            while (resultSet.next()) {
                if (checkedObject == null) {
                    checkedObject = new CheckedObject(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getString("descr"));
                } else {
                    String message = "Прочитано более одного объекта CheckedObject с идентификатором " + resultSet.getLong("id");
                    logger.error("{}: {}",sMethodName, message);
                    throw new DublicateObjectById(message);
                }
                logger.trace("{}: CheckedObject = {}", sMethodName, checkedObject);
            }
            return checkedObject;
        } catch (SQLException exception) {
            logger.error("Ошибка при чтении CheckedObject из БД", exception);
            throw exception;
        }
    }
}
