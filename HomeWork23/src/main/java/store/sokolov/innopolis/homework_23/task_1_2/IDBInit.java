package store.sokolov.innopolis.homework_23.task_1_2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IDBInit {
    /**
     * Выполняет запросы из файлов
     * @throws IOException выбрасывается при возникновении ошибки при чтении файлов
     */
    void executeSQLs() throws IOException, SQLException;
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
