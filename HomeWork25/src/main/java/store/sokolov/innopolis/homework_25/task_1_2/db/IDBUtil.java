package store.sokolov.innopolis.homework_25.task_1_2.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IDBUtil {
    /**
     * Выполняет запросы из файлов
     * @throws IOException выбрасывается при возникновении ошибки при чтении файлов
     */
    void prepareDB(String sqlFolder) throws IOException, SQLException;
    /**
     * Возвращает список всех sql файлов, которые необходимо выполнить
     * Маска поиска - файл должен начинаться на "db-" и оканчиваться на ".sql"
     * @return список файлов
     */
    List<String> getListOfSQL(String sqlFolder);
    /**
     * Возвращает запросы, прочитанные из файла
     * @param sqlFile файл, из которого читаются запросы
     * @return запросы, прочитанные из файла
     */
    String getSQLFromFile(String sqlFile) throws IOException;
}
