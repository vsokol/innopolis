package store.sokolov.innopolis.homework_25.task_1_2.dao;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckListItem;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class TaskDaoTest {
    private String url = "";
    private ITaskDao taskDao;
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Statement statementSQL;
    @Mock
    private PreparedStatement preparedStatementDeleteWithError;

    @BeforeAll
    static void setUpAll() {

    }

    @BeforeEach
    void setUp() throws SQLException {
        initMocks(this);
        connection = mock(Connection.class);
        connectionManager = mock(ConnectionManager.class);
        when(connectionManager.getConnection()).thenReturn(connection);
        taskDao = Mockito.spy(new TaskDao(connectionManager));
    }

    @Test
    @DisplayName("1 - Применение параметризированного запроса. Получение списка CheckListItem по идентификатору CheckList")
    void getListCheckListItemOnId() throws SQLException {
        when(connection.prepareStatement(TaskDao.SELECT_CHECKLIST_ITEM)).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1001L).thenReturn(1002L);
        when(resultSet.getLong("checklist_id")).thenReturn(1001L);
        when(resultSet.getLong("type_id")).thenReturn(1L).thenReturn(2L);
        when(resultSet.getString("name")).thenReturn("Есть пожарный щит?").thenReturn("Есть ли на пожарном щите следующие элементы?");
        when(resultSet.getBoolean("is_required")).thenReturn(true).thenReturn(false);
        when(resultSet.getString("descr")).thenReturn("Возможные значения: \"Да\", \"Нет\" и \"Не определено\"").thenReturn("Возможные значения: \"Ведро\", \"Багор\", \"Лопата\", \"Топор\"");

        List<CheckListItem> list = taskDao.getListCheckListItemOnId(1001);
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getId(), 1001L);
        assertEquals(list.get(1).getId(), 1002L);
    }

    @Test
    @DisplayName("2 - Применение батчинга. Добавление нескольких объектов проверки CheckedObject")
    void AddAllCheckedObject() throws SQLException {
        when(connection.prepareStatement(TaskDao.INSERT_CHECKED_OBJECT)).thenReturn(preparedStatement);
        int[] arr = {1, 1};
        when(preparedStatement.executeBatch()).thenReturn(arr);

        // TODO нужно заглушить logCheckedObject
        when(connection.createStatement()).thenReturn(statementSQL);
        when(statementSQL.executeQuery(TaskDao.SELECT_CHECKED_OBJECT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1011L).thenReturn(1012L);
        when(resultSet.getLong("parent_id")).thenReturn(1004L);
        when(resultSet.getString("name")).thenReturn("Отдел №1").thenReturn("Отдел №2");
        when(resultSet.getString("descr")).thenReturn("Описание отдела №1").thenReturn("Описание отдела №2");

        List<ICheckedObject> listCheckedObject = new ArrayList<>();
        ICheckedObject parentCheckedObject = new CheckedObject(1004L, "Заглушка");
        for (int i = 1; i <= 2; i++) {
            ICheckedObject checkedObject = new CheckedObject(null,"Отдел №" + i, parentCheckedObject,"Описание отдела №" + i);
            listCheckedObject.add(checkedObject);
        }
        taskDao.AddAllCheckedObject(listCheckedObject);

        verify(preparedStatement, times(2)).addBatch();
        verify(preparedStatement, times(1)).executeBatch();

        assertDoesNotThrow(() -> taskDao.AddAllCheckedObject(null));
    }

    @Test
    @DisplayName("3 - Проверка ручного управление транзакциями")
    void updateNameForCheckedObjectWithManualManage() throws SQLException {
        when(connection.prepareStatement(TaskDao.UPDATE_CHECKED_OBJECT)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        // TODO нужно заглушить logCheckedObject
        when(connection.createStatement()).thenReturn(statementSQL);
        when(statementSQL.executeQuery(TaskDao.SELECT_CHECKED_OBJECT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1011L).thenReturn(1012L);
        when(resultSet.getLong("parent_id")).thenReturn(1004L);
        when(resultSet.getString("name")).thenReturn("Отдел №1").thenReturn("Отдел №2");
        when(resultSet.getString("descr")).thenReturn("Описание отдела №1").thenReturn("Описание отдела №2");

        assertDoesNotThrow(() -> taskDao.updateNameForCheckedObjectWithManualManage(1000, "Отдел №111", 1001, "Отдел №222"));
        verify(preparedStatement, times(2)).executeUpdate();
    }

    @Test
    @DisplayName("4 - Использование savepoint")
    void updateNameForCheckedObjectWithUseSavepoint() throws SQLException {
        when(connection.prepareStatement(TaskDao.UPDATE_CHECKED_OBJECT)).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        // TODO нужно заглушить logCheckedObject
        when(connection.createStatement()).thenReturn(statementSQL);
        when(statementSQL.executeQuery(TaskDao.SELECT_CHECKED_OBJECT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1011L).thenReturn(1012L);
        when(resultSet.getLong("parent_id")).thenReturn(1004L);
        when(resultSet.getString("name")).thenReturn("Отдел №1").thenReturn("Отдел №2");
        when(resultSet.getString("descr")).thenReturn("Описание отдела №1").thenReturn("Описание отдела №2");

        assertDoesNotThrow(() -> taskDao.updateNameForCheckedObjectWithUseSavepoint(1006, "Отдел № 7-7-7", "Отдел № 777"));
        verify(preparedStatement, times(2)).executeUpdate();
        verify(connection, times(3)).setSavepoint();
        verify(connection, times(1)).commit();
    }

    @Test
    @DisplayName("5 - Rollback операций при ошибках")
    void deleteCheckedObjectWithRollBack() throws SQLException {
        when(connection.prepareStatement(TaskDao.DELETE_CHECKED_OBJECT)).thenReturn(preparedStatement);
        when(connection.prepareStatement(TaskDao.DELETE_CHECKED_OBJECT_WITH_ERROR)).thenReturn(preparedStatementDeleteWithError);
        when(preparedStatement.execute()).thenReturn(true);
        when(preparedStatement.execute()).thenThrow(new SQLException("org.postgresql.util.PSQLException: ERROR: column \"id1\" does not exist\n" +
                "  Подсказка: Perhaps you meant to reference the column \"checked_object.id\""));

        // TODO нужно заглушить logCheckedObject
        when(connection.createStatement()).thenReturn(statementSQL);
        when(statementSQL.executeQuery(TaskDao.SELECT_CHECKED_OBJECT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1011L).thenReturn(1012L);
        when(resultSet.getLong("parent_id")).thenReturn(1004L);
        when(resultSet.getString("name")).thenReturn("Отдел №1").thenReturn("Отдел №2");
        when(resultSet.getString("descr")).thenReturn("Описание отдела №1").thenReturn("Описание отдела №2");

        assertDoesNotThrow(() -> taskDao.deleteCheckedObjectWithRollBack(1007, 1005));
        verify(preparedStatement, times(1)).execute();
        verify(preparedStatementDeleteWithError, times(0)).execute();
        verify(connection, times(0)).commit();
        verify(connection, times(1)).rollback();
    }

    @Test
    void logCheckedObject() throws SQLException {
        when(connection.createStatement()).thenReturn(statementSQL);
        when(statementSQL.executeQuery(TaskDao.SELECT_CHECKED_OBJECT)).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSet.getLong("id")).thenReturn(1011L).thenReturn(1012L);
        when(resultSet.getLong("parent_id")).thenReturn(1004L);
        when(resultSet.getString("name")).thenReturn("Отдел №1").thenReturn("Отдел №2");
        when(resultSet.getString("descr")).thenReturn("Описание отдела №1").thenReturn("Описание отдела №2");

        assertDoesNotThrow(() -> taskDao.logCheckedObject(connection));
        verify(statementSQL, times(1)).executeQuery(TaskDao.SELECT_CHECKED_OBJECT);
    }
}