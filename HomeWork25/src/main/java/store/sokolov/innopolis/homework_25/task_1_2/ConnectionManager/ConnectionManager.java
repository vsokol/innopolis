package store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@EJB
public class ConnectionManager implements IConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    final private static String url = "jdbc:postgresql://localhost:5432/testDB?user=postgres&password=Asdf4321";
    //final private static String url = "jdbc:postgresql://host.docker.internal:5432/testDB?user=postgres&password=Asdf4321";
    //final private static String url = "jdbc:postgresql://test-db:5432/testDB?user=postgres&password=Asdf4321";
   //final private static String url = "jdbc:postgresql://172.17.0.2:5432/testDB?user=postgres&password=Asdf4321";
//    private static volatile ConnectionManager INSTANCE = null;
    private volatile Connection connection;

//    private ConnectionManager() throws SQLException {
//        connection = DriverManager.getConnection(url);
//    }
//
//    public static ConnectionManager getInstance() throws SQLException {
//        ConnectionManager localInstance = INSTANCE;
//        if (localInstance == null) {
//            synchronized (ConnectionManager.class) {
//                localInstance = INSTANCE;
//                if (localInstance == null) {
//                    INSTANCE = localInstance = new ConnectionManager();
//                }
//            }
//        }
//        return localInstance;
//    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            synchronized (ConnectionManager.class) {
                if (connection == null) {
                    try {
                        Class.forName("org.postgresql.Driver");
                        connection = DriverManager.getConnection(url);
                    } catch (SQLException | ClassNotFoundException exception) {
                        logger.error("Ошибка в методе getConnection", exception);
                    }
                }
            }
        }
        return connection;
    }
}
