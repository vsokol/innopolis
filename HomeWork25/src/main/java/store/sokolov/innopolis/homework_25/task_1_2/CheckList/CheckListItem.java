package store.sokolov.innopolis.homework_25.task_1_2.CheckList;

public class CheckListItem {
    private long id;
    private long checkListId;
    private int typeId;
    private String name;
    private boolean isReguired;
    private String descr;

    public CheckListItem(long id, long checkListId, int typeId, String name, Boolean isReguired) {
        this(id, checkListId,typeId, name, isReguired, null);
    }

    public CheckListItem(long id, long checkListId, int typeId, String name, Boolean isReguired, String descr) {
        this.id = id;
        this.checkListId = checkListId;
        this.typeId = typeId;
        this.name = name;
        this.isReguired = isReguired;
        this.descr = descr;
    }

    public long getId() {
        return id;
    }

    public long getCheckListId() {
        return checkListId;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public boolean isReguired() {
        return isReguired;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public String toString() {
        return "CheckListItem {" +
                "id=" + id +
                ", checkListId=" + checkListId +
                ", typeId=" + typeId +
                ", name='" + name + '\'' +
                ", isReguired=" + isReguired +
                ", descr='" + descr + '\'' +
                '}';
    }
}
