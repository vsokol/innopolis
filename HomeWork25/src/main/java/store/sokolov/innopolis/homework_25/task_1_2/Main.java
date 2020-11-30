package store.sokolov.innopolis.homework_25.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.dao.ICheckedObjectDao;
import store.sokolov.innopolis.homework_25.task_1_2.db.DBUtil;
import store.sokolov.innopolis.homework_25.task_1_2.dao.CheckedObjectDao;

import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
     * Описание задачи - tasks.md
     * Инициализация БД. Запросы читаются из каталога src/main/java/store/sokolov/innopolis/homework_25/sql и выполняются на бд.
     */
    public class Main {
        private static final Logger logger = LoggerFactory.getLogger(Main.class);

        public static void main(String[] args) throws SQLException, IOException {
            logger.info("Подготовка базы");
            ConnectionManager connectionManager = ConnectionManager.getInstance();
            DBUtil dbUtil = new DBUtil(connectionManager);
            dbUtil.prepareDB("src/main/java/store/sokolov/innopolis/homework_25/sql");

            ICheckedObjectDao checkedObjectDao = new CheckedObjectDao(connectionManager);
            ICheckedObject checkedObject = checkedObjectDao.getCheckedObjectById(1001);
            logger.info(checkedObject.toString());

            List<ICheckedObject> list = checkedObjectDao.getAllCheckedObject();
            logger.info(String.valueOf(list));
        }

    }
