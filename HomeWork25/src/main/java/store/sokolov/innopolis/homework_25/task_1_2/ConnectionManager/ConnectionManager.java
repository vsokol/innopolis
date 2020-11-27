package store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager implements IConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private static volatile ConnectionManager INSTANCE = null;
    private String url;
    private Connection connection;

    private ConnectionManager(String url) throws SQLException {
        this.url = url;
        connection = DriverManager.getConnection(url);
    }

    public static ConnectionManager getInstance() throws InvalidInvokeMethod {
        if (INSTANCE == null) {
            throw new InvalidInvokeMethod("ConnectionManager еще не создан. Необходимо использовать getInstance(String url).");
        }
        return INSTANCE;
    }

    public static ConnectionManager getInstance(String url) throws SQLException {
        ConnectionManager localInstance = INSTANCE;
        if (localInstance == null) {
            synchronized (ConnectionManager.class) {
                localInstance = INSTANCE;
                if (localInstance == null) {
                    INSTANCE = localInstance = new ConnectionManager(url);
                }
            }
        }
        return localInstance;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
}
