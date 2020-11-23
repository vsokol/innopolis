package store.sokolov.innopolis.homework_23.task_1_2;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DBInitTest {
    private DBInit dbInit;
    private String sqlFolder = "sqlFolder";

    @BeforeEach
    void setUp() throws SQLException {
        dbInit = new DBInit("url", sqlFolder);
    }

    @Test
    void getListOfSQL() {
        List<String> listOfSQL = dbInit.getListOfSQL(sqlFolder);
        //org.junit.jupiter.api.Assertions.
    }

}