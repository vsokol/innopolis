package store.sokolov.innopolis.homework_25.task_1_2.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.exception.DublicateObjectById;
import store.sokolov.innopolis.homework_25.task_1_2.exception.NoDataFoundException;

import javax.ejb.EJB;
import javax.inject.Inject;
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
    public final static String INSERT_CHECKED_OBJECT_BY_ID = "insert into checked_object (name, descr) values (?, ?) returning id";
    public final static String UPDATE_CHECKED_OBJECT_BY_ID = "update checked_object set name = ?, descr = ? where id = ?";
    public final static String DELETE_CHECKED_OBJECT_BY_ID = "delete from checked_object where id = ?";

    private final ConnectionManager connectionManager;

    @Inject
    public CheckedObjectDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Возвращает список объектов проверки по указанному идентификатору
     *
     * @param id индентификатор объекта проверки
     * @return объект проверки
     */
    @Override
    public ICheckedObject getCheckedObject(long id) throws NoDataFoundException, DublicateObjectById {
        String sMethodName = "getCheckedObjectById";
        logger.info("{}: Старт...", sMethodName);
        List<ICheckedObject> list;
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

    /**
     * Возвращает список всех объектов проверки
     *
     * @return список всех объектов проверки
     */
    @Override
    public List<ICheckedObject> getAllCheckedObject() {
        String sMethodName = "getAllCheckedObject";
        logger.info("{}: Старт...", sMethodName);
        List<ICheckedObject> list;
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

    /**
     * Считывает данные по объектам проверки из ResultSet и возвращает их ввиде списка
     *
     * @param resultSet ResultSet с данными по объеткам проверки
     * @return список объектов проверки
     * @throws SQLException выбрасывается, если не получается получить данные из ResultSet
     */
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

    /**
     * Добавляет новый объект проверки
     * @param checkedObject  объект проверки
     * @return объект проверки с идентификатором
     */
    @Override
    public ICheckedObject addCheckedObject(ICheckedObject checkedObject) {
        String sMethodName = "addCheckedObject";
        logger.info("{}: Старт...", sMethodName);
        try {
            logger.debug("{}: sql = {}", sMethodName, INSERT_CHECKED_OBJECT_BY_ID);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(INSERT_CHECKED_OBJECT_BY_ID);
            statement.setString(1, checkedObject.getName());
            statement.setString(2, checkedObject.getDescr());
            logger.debug("{}: Параметры 1(name) = {}, 2(descr) = {}", sMethodName, checkedObject.getName(), checkedObject.getDescr());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet == null) {
                logger.info("{}: resultSet is NULL", sMethodName);
                return null;
            }
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                checkedObject.setId(id);
                logger.trace("{}: новый CheckedObject = {}", sMethodName, checkedObject);
            }
            if (checkedObject.getId() != null) {
                return checkedObject;
            } else {
                return null;
            }
        } catch (SQLException exception) {
            logger.error("Ошибка при добавлении нового CheckedObject", exception);
        }
        return null;
    }

    /**
     * Обновляет данные по объекту проверки
     *
     * @param checkedObject объект проверки
     */
    @Override
    public void updateCheckedObject(ICheckedObject checkedObject) {
        String sMethodName = "updateCheckedObject";
        logger.info("{}: Старт...", sMethodName);
        try {
            logger.debug("{}: sql = {}", sMethodName, UPDATE_CHECKED_OBJECT_BY_ID);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(UPDATE_CHECKED_OBJECT_BY_ID);
            statement.setString(1, checkedObject.getName());
            statement.setString(2, checkedObject.getDescr());
            statement.setLong(3, checkedObject.getId());
            logger.debug("{}: Параметры 1(name) = {}, 2(descr) = {}, 3(id) = {}", sMethodName, checkedObject.getName(), checkedObject.getDescr(), checkedObject.getId());
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.error("Ошибка при обновлении информации по CheckedObject с id = " + checkedObject.getId(), exception);
        }
    }

    /**
     * Удаляет объект проверки
     *
     * @param checkedObject удаляемый объект проверки
     */
    @Override
    public void removeCheckedObject(ICheckedObject checkedObject) {
        String sMethodName = "deleteCheckedObject";
        logger.info("{}: Старт...", sMethodName);
        try {
            logger.debug("{}: sql = {}", sMethodName, DELETE_CHECKED_OBJECT_BY_ID);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(DELETE_CHECKED_OBJECT_BY_ID);
            statement.setLong(1, checkedObject.getId());
            logger.debug("{}: Параметр 1(id) = {}", sMethodName, checkedObject.getId());
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.error("Ошибка при удалении CheckedObject с id = " + checkedObject.getId(), exception);
        }
    }
}
