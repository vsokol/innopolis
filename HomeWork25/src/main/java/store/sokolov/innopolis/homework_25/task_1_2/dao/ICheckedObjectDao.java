package store.sokolov.innopolis.homework_25.task_1_2.dao;

import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;

import java.sql.SQLException;
import java.util.List;

public interface ICheckedObjectDao {
    ICheckedObject getCheckedObject(long id) ;
    List<ICheckedObject> getAllCheckedObject();
    ICheckedObject addCheckedObject(ICheckedObject checkedObject);
    void updateCheckedObject(ICheckedObject checkedObject);
    void removeCheckedObject(ICheckedObject checkedObject);
}
