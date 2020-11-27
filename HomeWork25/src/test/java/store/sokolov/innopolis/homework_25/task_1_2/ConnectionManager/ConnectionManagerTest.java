package store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionManagerTest {
    ConnectionManager connectionManager;

    @Test
    void getInstance() throws InvalidInvokeMethod {
        assertThrows(InvalidInvokeMethod.class, ConnectionManager::getInstance);
    }
}