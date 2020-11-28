package store.sokolov.innopolis.homework_25.task_1_2.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.IConnectionManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Вспомогательный класс для подготовки базы данных
 * Читает все запросы из каталога sqlFolder в соответствии с маской (файл должен начинаться на "db-" и оканчиваться на ".sql")
 * и выполняет их
 *
 * @author Vladimir Sokolov
 */
public class DBUtil implements IDBUtil {
    private final IConnectionManager connectionManager;
    /** соединение с бд */
    private final Connection connection;
    /** каталог, из которого читаются файлы с запросами */
    private final Logger logger = LoggerFactory.getLogger(DBUtil.class);

    /**
     * Конструктор
     * @param connectionManager соединение с бд
     * @throws SQLException выбрасывается при возникновении ошибки при выполнении запроса
     */
    public DBUtil(IConnectionManager connectionManager) throws SQLException {
        this.connectionManager = connectionManager;
        connection = connectionManager.getConnection();
    }

    /**
     * Конструктор
     * @param url строка соединения с бд
     * @throws SQLException выбрасывается при возникновении ошибки при выполнении запроса
     */
    public DBUtil(String url) throws SQLException {
        this(ConnectionManager.getInstance(url));
    }

    /**
     * Выполняет запросы из файлов
     * @throws IOException выбрасывается при возникновении ошибки при чтении файлов
     */
    @Override
    public void prepareDB(String sqlFolder) throws IOException, SQLException {
        String sMethodName = "executeSQLs";
        logger.info("{} Старт", sMethodName);
        List<String> listOfSQL = getListOfSQL(sqlFolder);
        Statement sqlStatement = connection.createStatement();
        if (listOfSQL == null || listOfSQL.size() == 0) {
            logger.info("{}: Нет файлов для выполнения SQL", sMethodName);
            return;
        }
        for (String file : listOfSQL) {
            logger.info("{}: Выполнение SQL - {}", sMethodName, file);
            String sql = getSQLFromFile(file);
            sqlStatement.execute(sql);
        }
    }

    /**
     * Возвращает список всех sql файлов, которые необходимо выполнить
     * Маска поиска - файл должен начинаться на "db-" и оканчиваться на ".sql"
     * @return список файлов
     */
    @Override
    public List<String> getListOfSQL(String sqlFolder) {
        String sMethodName = "getListOfSQL";
        logger.info("{} Старт", sMethodName);
        List<String> listOfSQL = new ArrayList<>();
        File folder = new File(sqlFolder);
        File[] files = folder.listFiles(
                (File pathname) -> {
                    String fileName = pathname.getName().toLowerCase();
                    return fileName.startsWith("db-") && fileName.endsWith(".sql");
                }
        );
        if (files == null) {
            return listOfSQL;
        }
        for (File file : files) {
            if (file.isFile()) {
                listOfSQL.add(folder + File.separator + file.getName());
            }
        }
        if (listOfSQL.size() != 0) {
            Collections.sort(listOfSQL);
        }
        return listOfSQL;
    }

    /**
     * Возвращает запросы, прочитанные из файла
     * @param sqlFile файл, из которого читаются запросы
     * @return запросы, прочитанные из файла
     */
    @Override
    public String getSQLFromFile(String sqlFile) throws IOException {
        String sMethodName = "getSQLFromFile";
        logger.info("{} Старт", sMethodName);
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(sqlFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String line = reader.readLine();
                sb.append(line);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            logger.error("{}: Файл {} не найден", sMethodName, sqlFile, e);
            throw e;
        } catch (IOException e) {
            logger.error("{}: Ошибка чтения SQL из файла", sMethodName, e);
            throw e;
        }
        return sb.toString();
    }
}
