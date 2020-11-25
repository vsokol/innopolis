package store.sokolov.innopolis.homework_23.task_1_2;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class DBInitTest {
    private DBInit dbInit;
    private String url = "url";
    private String sqlFolder = "sqlFolder";
    protected DriverManager driverManager;
    @Mock
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = mock(connection.getClass());
        //driverManager = spy(DriverManager.getConnection(url));

        dbInit = new DBInit(url, sqlFolder);
    }

    @Test
    void getListOfSQL() {
        List<String> listOfSQL = dbInit.getListOfSQL(sqlFolder);
        assertEquals(listOfSQL.size(), 2);
    }

}