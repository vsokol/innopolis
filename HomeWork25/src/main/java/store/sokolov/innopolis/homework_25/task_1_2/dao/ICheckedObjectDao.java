package store.sokolov.innopolis.homework_25.task_1_2.dao;

import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;

import java.sql.SQLException;

public interface ICheckedObjectDao {
    ICheckedObject getCheckedObjectById(long id) throws SQLException;
}
