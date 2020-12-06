package store.sokolov.innopolis.homework_25.task_1_2.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.User;
import store.sokolov.innopolis.homework_25.task_1_2.exception.DublicateObjectById;
import store.sokolov.innopolis.homework_25.task_1_2.exception.NoDataFoundException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.jws.soap.SOAPBinding;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EJB
public class UserDao implements IUserDao {
    private final Logger logger = LoggerFactory.getLogger(UserDao.class);
    final private String SELECT_USER_BY_ID =
            "select id, login, password, name, is_lock, full_name from users where id = ?";
    final private String SELECT_USERS = "select id, login, password, name, is_lock, full_name from users";
    final private String INSERT_USER = "insert into users (login, name, is_lock, full_name) " +
            "values(?, ?, ?, ?) returning id";
    final private String UPDATE_USER = "update users " +
            "set login = ?, name = ?, full_name = ?, is_lock = ? where id = ?";
    final private String UPDATE_USER_WITHOUT_IS_LOCK = "update users " +
            "set login = ?, name = ?, full_name = ? where id = ?";
    final private String DELETE_USER = "delete from users where id = ?";
    final private String LOCK_USER = "update users set is_lock = true where id = ?";
    final private String UNLOCK_USER = "update users set is_lock = false where id = ?";
    final private String CHANGE_USER_PASSWORD = "update users set password = ? where login = ?";
    final private String CHECK_PASSWORD = "select password from users where login = ? and is_lock = false";

    private ConnectionManager connectionManager;

    @Inject
    public UserDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public User getUser(long id) {
        logger.info("Получение информации по пользователю с идентификатором {}", id);
        List<User> list;
        try {
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(SELECT_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            list = getUserFromResultSet(resultSet);
            if (list.size() == 0) {
                String message = "Не найдено ни одного пользователя с идентификатором " + id;
                logger.error(message);
                throw new NoDataFoundException(message);
            }
            if (list.size() > 2) {
                String message = "Прочитано более одного пользователя с идентификатором " + resultSet.getLong("id");
                logger.error(message);
                throw new DublicateObjectById(message);
            }
            return list.get(0);
        } catch (SQLException exception) {
            logger.error("Error reading user information by id " + id);
        }
        return null;
    }

    private List<User> getUserFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> list = new ArrayList<>();
        if (resultSet == null) {
            logger.info("resultSet is NULL");
            return list;
        }
        while (resultSet.next()) {
            list.add(new User(
                    resultSet.getLong("id"),
                    resultSet.getString("login"),
                    resultSet.getString("password"),
                    resultSet.getString("name"),
                    resultSet.getBoolean("is_lock"),
                    resultSet.getString("full_name")));
            logger.trace(list.toString());
        }
        return list;
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Получение информации по всем пользователям");
        List<User> list;
        try {
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(SELECT_USERS);
            ResultSet resultSet = statement.executeQuery();
            list = getUserFromResultSet(resultSet);
            return list;
        } catch (SQLException exception) {
            logger.error("Error reading user information");
        }
        return null;
    }

    @Override
    public User addUser(User user) {
        try {
            logger.debug("sql = {}", INSERT_USER);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(INSERT_USER);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setBoolean(3, user.getIsLock());
            statement.setString(4, user.getFullName());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet == null) {
                logger.info("resultSet is NULL");
                return null;
            }
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                user.setId(id);
                logger.trace("новый User = {}", user);
            }
            if (user.getId() != null) {
                return user;
            } else {
                return null;
            }
        } catch (SQLException exception) {
            logger.error("Ошибка при добавлении нового CheckedObject", exception);
        }
        return null;
    }

    @Override
    public void updateUser(User user) {
        updateUser(user, true);
    }

    @Override
    public void updateUserWithoutIsLock(User user) {
        updateUser(user, false);
    }

    private void updateUser(User user, boolean withFieldIsLock) {
        try {
            String sql = (withFieldIsLock ? UPDATE_USER : UPDATE_USER_WITHOUT_IS_LOCK);
            logger.debug("sql = {}", sql);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setString(3, user.getFullName());
            int idxId = 4;
            if (withFieldIsLock) {
                statement.setBoolean(4, user.getIsLock());
                idxId = 5;
            }
            statement.setLong(idxId, user.getId());
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.error("Error while updating information for a user with an id = " + user.getId(), exception);
        }
    }

    @Override
    public void removeUser(User user) {
        try {
            logger.debug("sql = {}", DELETE_USER);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(DELETE_USER);
            statement.setLong(1, user.getId());
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.error("Error when deleting a user with an id = " + user.getId(), exception);
        }
    }

    @Override
    public void lockUser(User user) {
        changeLock(user, true);
    }

    @Override
    public void unlockUser(User user) {
        changeLock(user, false);
    }

    private void changeLock(User user, boolean isLock) {
        try {
            String sql = (isLock ? LOCK_USER : UNLOCK_USER);
            logger.debug("sql = {}", sql);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(sql);
            statement.setLong(1, user.getId());
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.error("Error when blocking a user with an id = " + user.getId(), exception);
        }
    }

    @Override
    public void changePassword(User user, String password) {
        try {
            logger.debug("sql = {}", CHANGE_USER_PASSWORD);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(CHANGE_USER_PASSWORD);
            statement.setString(1, password);
            statement.setString(2, user.getLogin());
            statement.executeUpdate();

        } catch (SQLException exception) {
            logger.error("Error when changing user password with id = " + user.getId(), exception);
        }
    }

    @Override
    public boolean isAccessDenied(String login, String password) {
        try {
            logger.debug("sql = {}", CHECK_PASSWORD);
            PreparedStatement statement = connectionManager.getConnection().prepareStatement(CHECK_PASSWORD);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet == null) {
                logger.info("resultSet is NULL");
                return true;
            }
            String savedPassword = null;
            while (resultSet.next()) {
                savedPassword = resultSet.getString("password");
            }
            if (savedPassword != null && savedPassword.equals(password)) {
                return false;
            }
        } catch (SQLException exception) {
            logger.error("Ошибка при проверки доступа", exception);
        }
        return true;
    }
}
