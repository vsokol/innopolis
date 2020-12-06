package store.sokolov.innopolis.homework_25.task_1_2.dao;

import store.sokolov.innopolis.homework_25.task_1_2.User;

import java.util.List;

public interface IUserDao {
    User getUser(long id) ;
    List<User> getAllUsers();
    User addUser(User user);
    void updateUser(User user);
    void updateUserWithoutIsLock(User user);
    void removeUser(User user);
    void lockUser(User user);
    void unlockUser(User user);
    void changePassword(User user, String password);
    boolean isAccessDenied(String login, String password);
}
