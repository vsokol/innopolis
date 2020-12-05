package store.sokolov.innopolis.homework_25.task_1_2;

public class User {
    private Long id;
    private String login;
    private String password;
    private String name;
    private boolean isLock;
    private String fullName;

    public User() {}

    public User(String login, String name, boolean isLock, String fullName) {
        this(null, login, null, name, isLock, fullName);
    }
    public User(Long id, String login, String name, boolean isLock, String fullName) {
        this(id, login, null, name, isLock, fullName);
    }
    public User(Long id, String login, String password, String name, boolean isLock, String fullName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.isLock = isLock;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsLock() {
        return isLock;
    }

    public void setIsLock(boolean lock) {
        isLock = lock;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User={" +
                "id=" + id +
                ", login=" + login +
                ", password=" + (password == null ? "" : password.replaceAll(".", "*")) +
                ", name=" + name +
                ", isLock=" + isLock +
                ", fullName=" + fullName +
                "}";
    }
}
