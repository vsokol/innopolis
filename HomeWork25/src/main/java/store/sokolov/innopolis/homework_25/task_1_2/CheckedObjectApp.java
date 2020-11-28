package store.sokolov.innopolis.homework_25.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.db.DBUtil;
import store.sokolov.innopolis.homework_25.task_1_2.exception.CheckedObjectDao;

import java.io.IOException;
import java.sql.*;

/**
     * Описание задачи - tasks.md
     * Инициализация БД. Запросы читаются из каталога src/main/java/store/sokolov/innopolis/homework_25/sql и выполняются на бд.
     */
    public class CheckedObjectApp {
        private static final Logger logger = LoggerFactory.getLogger(CheckedObject.class);
        //private static String url = "jdbc:postgresql://localhost:5432/testDB?user=postgres&password=Asdf4321";
        //private static String url = "jdbc:postgresql://host.docker.internal:5432/testDB?user=postgres&password=Asdf4321";
        private static String url = "jdbc:postgresql://172.17.0.2:5432/testDB?user=postgres&password=Asdf4321";

        public static void main(String[] args) throws SQLException, IOException {
            logger.info("Подготовка базы");
            ConnectionManager connectionManager = ConnectionManager.getInstance(url);
            DBUtil dbUtil = new DBUtil(connectionManager);
            dbUtil.prepareDB("src/main/java/store/sokolov/innopolis/homework_25/sql");

            ICheckedObject checkedObject = new CheckedObjectDao(connectionManager).getCheckedObjectById(1001);
            logger.info(checkedObject.toString());
        }

    }
