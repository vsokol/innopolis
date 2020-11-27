package store.sokolov.innopolis.homework_25.task_1_2.dao;

import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckListItem;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;

import java.sql.*;
import java.util.List;

public interface ITaskDao {
    /**
     * Применить параметризованный запрос
     */
    List<CheckListItem> getListCheckListItemOnId(long checklistId) throws SQLException;
    /**
     * Применить батчинг
     * @param listCheckedObject
     */
    void AddAllCheckedObject(List<ICheckedObject> listCheckedObject) throws SQLException;
    /**
     * Использовать ручное управление транзакциями
     */
    void updateNameForCheckedObjectWithManualManage(long id1, String name1, long id2, String name2) throws SQLException;
    /**
     * - Предусмотреть использование savepoint при выполнении логики из нескольких запросов
     */
    void updateNameForCheckedObjectWithUseSavepoint(long id, String name1, String name2) throws SQLException;
    /**
     * Предусмотреть rollback операций при ошибках
     */
    void deleteCheckedObjectWithRollBack(long id1, long id2) throws SQLException;

    void logCheckedObject(Connection connection) throws SQLException;
}
