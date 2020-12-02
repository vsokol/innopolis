package store.sokolov.innopolis.homework_25.task_1_2.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.IConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.db.DBUtil;
import store.sokolov.innopolis.homework_25.task_1_2.exception.DublicateObjectById;
import store.sokolov.innopolis.homework_25.task_1_2.exception.NoDataFoundException;

import javax.ejb.EJB;
import javax.inject.Inject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EJB
public class CheckedObjectDao implements ICheckedObjectDao {
    private final Logger logger = LoggerFactory.getLogger(CheckedObjectDao.class);
    public final static String SELECT_CHECKED_OBJECT = "select id, parent_id, name, descr from checked_object";
    public final static String SELECT_CHECKED_OBJECT_BY_ID = "select id, parent_id, name, descr from checked_object where id = ?";
    public final static String SELECT_CHECKED_OBJECT_BY_PARENT_ID = "select id, parent_id, name, descr from checked_object where parent_id = ?";

    private final ConnectionManager connectionManager;

    @Inject
    public CheckedObjectDao(ConnectionManager connectionManager) throws IOException, SQLException {
        this.connectionManager = connectionManager;
    }

    @Override
    public ICheckedObject getCheckedObjectById(long id) {
        String sMethodName = "getCheckedObjectById";
        logger.info("{}: Старт...", sMethodName);
        List<ICheckedObject> list = null;
        try {
            logger.debug("{}: sql = {}", sMethodName, SELECT_CHECKED_OBJECT_BY_ID);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(SELECT_CHECKED_OBJECT_BY_ID);
            statement.setLong(1, id);
            logger.debug("{}: Параметр 1 (id) = {}", sMethodName, id);
            ResultSet resultSet = statement.executeQuery();

            list = getCheckedObjectFromResultSet(resultSet);
            if (list.size() == 0) {
                // не найдено ни одного объекта с таким идентификатором
                String message = "Не найдено ни одного объекта с идентификатором " + id;
                logger.error("{}: {}", sMethodName, message);
                throw new NoDataFoundException(message);
            }
            if (list.size() > 2) {
                String message = "Прочитано более одного объекта CheckedObject с идентификатором " + resultSet.getLong("id");
                logger.error("{}: {}", sMethodName, message);
                throw new DublicateObjectById(message);
            }
            return list.get(0);
        } catch (SQLException exception) {
            logger.error("Ошибка при чтении CheckedObject из БД", exception);
        }
        return null;
    }

    @Override
    public List<ICheckedObject> getAllCheckedObject() {
        String sMethodName = "getAllCheckedObject";
        logger.info("{}: Старт...", sMethodName);
        List<ICheckedObject> list = null;
        try {
            logger.debug("{}: sql = {}", sMethodName, SELECT_CHECKED_OBJECT);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(SELECT_CHECKED_OBJECT);
            logger.debug("{}: statement = {}", sMethodName, statement);
            ResultSet resultSet = statement.executeQuery();
            list = getCheckedObjectFromResultSet(resultSet);
            return list;
        } catch (SQLException exception) {
            logger.error("Ошибка при чтении списка CheckedObject из БД", exception);
        }
        return new ArrayList<>();
    }

    private List<ICheckedObject> getCheckedObjectFromResultSet(ResultSet resultSet) throws SQLException {
        String sMethodName = "getCheckedObjectFromResutSet";
        logger.info("{}: Старт...", sMethodName);
        List<ICheckedObject> list = new ArrayList<>();
        if (resultSet == null) {
            logger.info("{}: resultSet is NULL", sMethodName);
            return list;
        }
        while (resultSet.next()) {
            list.add(new CheckedObject(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("descr")));
            logger.trace("{}: CheckedObject = {}", sMethodName, list);
        }
        return list;
    }
}