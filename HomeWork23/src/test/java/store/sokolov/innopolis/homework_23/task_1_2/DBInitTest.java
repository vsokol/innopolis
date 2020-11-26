package store.sokolov.innopolis.homework_23.task_1_2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import store.sokolov.innopolis.homework_23.task_1_2.ConnectionManager.ConnectionManager;

import java.io.*;
import java.lang.reflect.Executable;
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
    @Mock
    private FileInputStream fileInputStream;
    @Mock
    private InputStreamReader inputStreamReader;
    @Mock
    private BufferedReader reader;

    @BeforeEach
    void setUp() throws SQLException {
        initMocks(this);
        connectionManager = mock(ConnectionManager.class);
        connection = mock(Connection.class);
        when(connectionManager.getConnection()).thenReturn(connection);

        dbInit = new DBInit(connectionManager, sqlFolder);
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
        assertNotNull(list);
        assertEquals(list.size(), 0);
    }

    @Test
    void getSQLFromFile() throws IOException {
        //fileInputStream = mock(FileInputStream.class);
        //inputStreamReader = mock(InputStreamReader.class);
        //reader = mock(BufferedReader.class);
        when(reader.ready()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(reader.readLine()).thenReturn("line1").thenReturn("line2");

        //String sql = dbInit.getSQLFromFile("123");
        assertThrows(FileNotFoundException.class, () -> {
            String sql = dbInit.getSQLFromFile("123");
        });
    }
}