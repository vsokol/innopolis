package store.sokolov.innopolis.homework_25.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.db.DBUtil;
import store.sokolov.innopolis.homework_25.task_1_2.db.IDBUtil;

import java.io.IOException;
import java.sql.SQLException;

/**
     * Описание задачи - tasks.md
     * Инициализация БД. Запросы читаются из каталога src/main/java/store/sokolov/innopolis/homework_25/sql и выполняются на бд.
     */
    public class Main {
        private static final Logger logger = LoggerFactory.getLogger(Main.class);

        public static void main(String[] args) throws SQLException, IOException {
            logger.info("Подготовка базы");
            ConnectionManager connectionManager = new ConnectionManager();
            IDBUtil dbUtil = new DBUtil(connectionManager.getConnection());
            dbUtil.prepareDB("src/main/java/store/sokolov/innopolis/homework_25/sql");
        }

    }
