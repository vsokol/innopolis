package store.sokolov.innopolis.homework_25.task_1_2.CheckList;

import java.util.HashMap;
import java.util.Map;

public class CheckedObject implements ICheckedObject {
    private Long id;
    private ICheckedObject parent;
    private String name;
    private String descr;
    private Map<Long, ICheckedObject> listOfIncludedCheckedObject;

    public CheckedObject(Long id, String name) {
        this(id, name, null, null);
    }

    public CheckedObject(Long id, String name, String descr) {
        this.id = id;
        this.name = name;
        this.descr = descr;
    }

    public CheckedObject(Long id, String name, ICheckedObject parent, String descr) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.descr = descr;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public ICheckedObject getParent() {
        return parent;
    }

    @Override
    public void setParent(ICheckedObject parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescr() {
        return descr;
    }

    @Override
    public void setDescr(String descr) {
        this.descr = descr;
    }

    @Override
    public Map<Long, ICheckedObject> getListOfIncludedCheckedObject() {
        return listOfIncludedCheckedObject;
    }

    @Override
    public void setListOfIncludedCheckedObject(Map<Long, ICheckedObject> listOfIncludedCheckedObject) {
        this.listOfIncludedCheckedObject = listOfIncludedCheckedObject;
    }

    @Override
    public void includeCheckedObject(ICheckedObject checkedObject) {
        if (checkedObject == null || checkedObject.getId() == null) {
            return;
        }
        if (listOfIncludedCheckedObject == null) {
            listOfIncludedCheckedObject = new HashMap<>();
        }
        listOfIncludedCheckedObject.put(checkedObject.getId(), checkedObject);
    }

    @Override
    public void excludeCheckedObject(Long idCheckedObject) {
        if (idCheckedObject == null || listOfIncludedCheckedObject == null) {
            return;
        }
        if (listOfIncludedCheckedObject.containsKey(idCheckedObject)) {
            listOfIncludedCheckedObject.remove(idCheckedObject);
        }
    }

    @Override
    public void excludeCheckedObject(ICheckedObject checkedObject) {
        excludeCheckedObject(checkedObject.getId());
    }

    @Override
    public String toString() {
        return "CheckedObject{" +
                "id=" + id +
                ", parent=" + (parent != null ? parent.toString() : "null")+
                ", name='" + name + '\'' +
                ", descr='" + (descr != null ? getDescr() : "null") + '\'' +
                '}';
    }
}
