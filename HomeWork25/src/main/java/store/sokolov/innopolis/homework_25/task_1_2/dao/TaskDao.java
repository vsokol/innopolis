package store.sokolov.innopolis.homework_25.task_1_2.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckListItem;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.IConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDao implements ITaskDao {
    private static final Logger logger = LoggerFactory.getLogger(TaskDao.class);
    public static String SELECT_CHECKLIST_ITEM = "select id, type_id, checklist_id, name, is_required, descr from checklist_item where checklist_id = ?";
    public static String INSERT_CHECKED_OBJECT = "insert into checked_object (parent_id, name, descr) values (?, ?, ?)";
    public static String SELECT_CHECKED_OBJECT = "select id, parent_id, name, descr from checked_object";
    public static String UPDATE_CHECKED_OBJECT = "update checked_object set name = ? where id = ?";
    public static String DELETE_CHECKED_OBJECT = "delete from checked_object where id = ?";
    public static String DELETE_CHECKED_OBJECT_WITH_ERROR = "delete from checked_object where id1 = ?";
    private IConnectionManager connectionManager;

    public TaskDao(IConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    /**
     * Применить параметризованный запрос
     */
    @Override
    public List<CheckListItem> getListCheckListItemOnId(long checklistId) throws SQLException {
        String sMethodName = "getListCheckListItemOnId";
        logger.info("{}: Задача - Применить параметризованный запрос", sMethodName);
        List<CheckListItem> list = new ArrayList<>();
        logger.debug("{}: sql = " + SELECT_CHECKLIST_ITEM, sMethodName);
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(SELECT_CHECKLIST_ITEM);
        statement.setLong(1, checklistId);
        logger.debug("{}: Параметр 1 (checklistId) = {}", sMethodName, checklistId);
        ResultSet resultSet = statement.executeQuery();
        logger.info("{}: запрос успешно выполнен", sMethodName);
        while (resultSet.next()) {
            CheckListItem checkListItem = new CheckListItem(resultSet.getLong("id")
                    , resultSet.getLong("checklist_id")
                    , resultSet.getInt("type_id")
                    , resultSet.getString("name")
                    , resultSet.getBoolean("is_required")
                    , resultSet.getString("descr"));
            list.add(checkListItem);
            logger.trace("{}: row = {}", sMethodName, checkListItem);
        }
        logger.info("{}: получено {} записей", sMethodName, list.size());
        return list;
    }

    /**
     * Применить батчинг
     * @param listCheckedObject
     */
    @Override
    public void AddAllCheckedObject(List<ICheckedObject> listCheckedObject) throws SQLException {
        String sMethodName = "AddAllCheckedObject";
        logger.info("{}: Задача - Применить батчинг --", sMethodName);
        if (listCheckedObject == null || listCheckedObject.size() ==0) {
            logger.info("{}: Список добавляемых объектов пустой. Ничего не будет добавлено", sMethodName);
            return;
        }
        logger.debug("{}: sql = {}", sMethodName, INSERT_CHECKED_OBJECT);
        Connection connection = connectionManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_CHECKED_OBJECT);
        for (ICheckedObject item : listCheckedObject) {
            statement.setLong(1, item.getParent().getId());
            statement.setString(2, item.getName());
            statement.setString(3, item.getDescr());
            statement.addBatch();

            logger.trace("{}: Параметры { 1 (parent_id) = {}, 2 (name) = {}, 3 (descr) = {}}}", sMethodName, item.getParent().getId(), item.getName(), item.getDescr());
        }
        statement.executeBatch();
        statement.close();

        if (logger.isTraceEnabled()) {
            logCheckedObject(connection);
        }
    }

    /**
     * Использовать ручное управление транзакциями
     */
    @Override
    public void updateNameForCheckedObjectWithManualManage(long id1, String name1, long id2, String name2) throws SQLException {
        String sMethodName = "updateNameForCheckedObjectWithManualManage";
        logger.info("{}: Задача - Использовать ручное управление транзакциями --", sMethodName);
        Connection connection = connectionManager.getConnection();
        connection.setAutoCommit(false);
        logger.debug("{}: sql = {}", sMethodName, UPDATE_CHECKED_OBJECT);
        PreparedStatement statement = connection.prepareStatement(UPDATE_CHECKED_OBJECT);

        statement.setString(1, name1);
        statement.setLong(2, id1);
        logger.debug("{}: Параметры {1 (name) = '{}', 2 (id) = {}", sMethodName, name1, id1);
        statement.executeUpdate();
        logger.info("{}: запрос выполнен", sMethodName);

        connection.rollback();
        logger.info("{}: откат транзакции", sMethodName);

        statement = connection.prepareStatement(UPDATE_CHECKED_OBJECT);
        statement.setString(1, name2);
        statement.setLong(2, id2);
        logger.debug("{}: Параметры {1 (name) = '{}', 2 (id) = {}", sMethodName, name2, id2);
        statement.executeUpdate();
        logger.info("{}: запрос выполнен", sMethodName);

        connection.commit();
        logger.info("{}: commit транзакции", sMethodName);

        if (logger.isTraceEnabled()) {
            logCheckedObject(connection);
        }
    }

    /**
     * - Предусмотреть использование savepoint при выполнении логики из нескольких запросов
     */
    @Override
    public void updateNameForCheckedObjectWithUseSavepoint(long id, String name1, String name2) throws SQLException {
        String sMethodName = "updateNameForCheckedObjectWithUseSavepoint";
        logger.info("{}: Задача - Предусмотреть использование savepoint при выполнении логики из нескольких запросов --", sMethodName);
        Connection connection = connectionManager.getConnection();
        connection.setAutoCommit(false);
        Savepoint sp1 = connection.setSavepoint();
        logger.info("{}: установлен Savepoint sp1", sMethodName);
        logger.debug("{}: sql = {}", sMethodName, UPDATE_CHECKED_OBJECT);
        PreparedStatement statement = connection.prepareStatement(UPDATE_CHECKED_OBJECT);
        statement.setString(1, name1);
        statement.setLong(2, id);
        logger.debug("{}: Параметры {1 (name) = '{}', 2 (id) = {}}", sMethodName, name1, id);
        statement.executeUpdate();
        Savepoint sp2 = connection.setSavepoint();
        logger.info("{}: установлен Savepoint sp2", sMethodName);

        logger.debug("{}: sql = {}", sMethodName, UPDATE_CHECKED_OBJECT);
        statement = connection.prepareStatement(UPDATE_CHECKED_OBJECT);
        logger.debug("{}: Параметры {1 (name) = '{}', 2 (id) = {}}", sMethodName, name2, id);
        statement.setString(1, name2);
        statement.setLong(2, id);
        statement.executeUpdate();
        Savepoint sp3 = connection.setSavepoint();
        logger.info("{}: установлен Savepoint sp3", sMethodName);

        connection.rollback(sp2);
        logger.info("{}: откат к Savepoint sp2", sMethodName);
        connection.commit();
        logger.info("{}: commit изменений", sMethodName);
        if (logger.isTraceEnabled()) {
            logCheckedObject(connection);
        }
    }

    /**
     * Предусмотреть rollback операций при ошибках
     */
    @Override
    public void deleteCheckedObjectWithRollBack(long id1, long id2) throws SQLException {
        String sMethodName = "deleteCheckedObjectWithRollBack";
        logger.info("{}: Задача - Предусмотреть rollback операций при ошибках --", sMethodName);
        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);

            logger.info("{}: Удаление CheckedObject с иденификатором = {}",sMethodName, id1);
            logger.debug("{}: sql = {}",sMethodName, DELETE_CHECKED_OBJECT);
            PreparedStatement statement = connection.prepareStatement(DELETE_CHECKED_OBJECT);
            statement.setLong(1, id1);
            logger.debug("{}: Параметры {1 (id) = {}", sMethodName, id1);
            statement.execute();

            logger.info("{}: Удаление CheckedObject с иденификатором = {}",sMethodName, id2);
            logger.debug("{}: sql = {}", sMethodName, DELETE_CHECKED_OBJECT_WITH_ERROR);
            statement = connection.prepareStatement(DELETE_CHECKED_OBJECT_WITH_ERROR);
            statement.setLong(1, id2);
            logger.debug("{}: Параметры {1 (id) = {}", sMethodName, id2);
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            logger.error("{}: Ошибка при выполнении метода {}()",sMethodName, sMethodName, e);
            connection.rollback();
            logger.error("{}: Откат транзакции",sMethodName);
        } finally {
            if (logger.isTraceEnabled()) {
                logCheckedObject(connection);
            }
        }
    }

    /**
     * Вывод в консоль выборки из таблицы checked_object
     * @param connection соединение с бд
     * @throws SQLException
     */
    @Override
    public void logCheckedObject(Connection connection) throws SQLException {
        String sMethodName = "logCheckedObject";
        logger.info("{}: старт", sMethodName);
        Statement statementSQL = connection.createStatement();
        logger.debug("{}: sql = {}", sMethodName, SELECT_CHECKED_OBJECT);
        ResultSet resultSet = statementSQL.executeQuery(SELECT_CHECKED_OBJECT);
        logger.info("{}: запрос выполнен", sMethodName);
        int count = 0;
        while (resultSet.next()) {
            String data = "{id = " + resultSet.getLong("id") + "\t"
                    + "parent_id = " + resultSet.getLong("parent_id") + "\t"
                    + "name = '" + resultSet.getString("name") + "'\t"
                    + "descr = '" + resultSet.getString("descr") + "'\t";
            logger.trace("{}: row = {}", sMethodName, data);
            count++;
        }
        logger.info("{}: получено {} записей", sMethodName, count);
    }
}
