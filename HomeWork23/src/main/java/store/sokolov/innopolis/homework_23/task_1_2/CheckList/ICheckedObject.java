package store.sokolov.innopolis.homework_23.task_1_2.CheckList;

import java.util.HashMap;
import java.util.Map;

public interface ICheckedObject {
    Long getId();

    void setId(Long id);

    ICheckedObject getParent();

    void setParent(ICheckedObject parent);

    String getName();

    void setName(String name);

    String getDescr();

    void setDescr(String descr);

    Map<Long, ICheckedObject> getListOfIncludedCheckedObject();

    void setListOfIncludedCheckedObject(Map<Long, ICheckedObject> listOfIncludedCheckedObject);

    void includeCheckedObject(ICheckedObject checkedObject);

    void excludeCheckedObject(Long idCheckedObject);

    void excludeCheckedObject(ICheckedObject checkedObject);
}
