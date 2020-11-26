package store.sokolov.innopolis.homework_23.task_1_2.ConnectionManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionManagerTest {
    ConnectionManager connectionManager;

    @Test
    void getInstance() throws InvalidInvokeMethod {
        assertThrows(InvalidInvokeMethod.class, ConnectionManager::getInstance);
    }
}