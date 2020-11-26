package store.sokolov.innopolis.homework_23.task_1_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import store.sokolov.innopolis.homework_23.task_1_2.ConnectionManager.ConnectionManager;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class DBInitTest {
    private String sqlFolder = "sqlFolder";
    private IDBInit dbInit;
    @Mock
    private ConnectionManager connectionManager;
    @Mock
    private Connection connection;
    @Mock
    private File folder;

    @BeforeEach
    void setUp() throws SQLException {
        initMocks(this);
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        when(connectionManager.getConnection()).thenReturn(connection);
        dbInit = spy(new DBInit(connectionManager, sqlFolder));
    }

    @Test
    void executeSQLs() throws IOException, SQLException {

        //dbInit.executeSQLs();
    }

    @Test
    void getListOfSQL() {
        folder = mock(File.class);
        when(folder.listFiles()).thenReturn(null);

        List<String> list = dbInit.getListOfSQL(sqlFolder);
    }

    @Test
    void getSQLFromFile() {
    }
}