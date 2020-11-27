package store.sokolov.innopolis.homework_25.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckListItem;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.CheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.CheckList.ICheckedObject;
import store.sokolov.innopolis.homework_25.task_1_2.ConnectionManager.ConnectionManager;
import store.sokolov.innopolis.homework_25.task_1_2.dao.ITaskDao;
import store.sokolov.innopolis.homework_25.task_1_2.dao.TaskDao;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
     * Описание задачи - tasks.md
     * Инициализация БД. Запросы читаются из каталога src/main/java/store/sokolov/innopolis/homework_25/sql и выполняются на бд.
     */
    public class TestJUnit {
        //private static String url = "jdbc:postgresql://localhost:5432/testDB?user=postgres&password=Asdf4321";
        //private static String url = "jdbc:postgresql://host.docker.internal:5432/testDB?user=postgres&password=Asdf4321";
        private static String url = "jdbc:postgresql://172.17.0.2:5432/testDB?user=postgres&password=Asdf4321";
        private static DBInit db;

        public static void main(String[] args) throws SQLException, IOException {
            Logger logger = LoggerFactory.getLogger("TestJUnit");
            logger.info("Старт Main");
            System.out.println("Домашнее задание 19");
            logger.info("Подготовка базы");
            db = new DBInit(url, "src/main/java/store/sokolov/innopolis/homework_25/sql");
            logger.info("-- Сброс и инициализация базы данных --");
            db.executeSQLs();
            logger.info("База готова");

            ITaskDao taskDao = new TaskDao(ConnectionManager.getInstance(url));
            logger.info("1. Выполнение метода getListCheckListItemOnId()");
            List<CheckListItem> list = taskDao.getListCheckListItemOnId(1001);
            logger.info("Количество элементов проверки = {}", list.size());
            
            logger.info("2. Выполнение метода AddAllCheckedObject()");
            List<ICheckedObject> listCheckedObject = new ArrayList<>();
            ICheckedObject parentCheckedObject = new CheckedObject(1004L, "Заглушка");
            for (int i = 1; i <= 10; i++) {
                ICheckedObject checkedObject = new CheckedObject(null,"Отдел №" + i, parentCheckedObject,"Описание отдела №" + i);
                listCheckedObject.add(checkedObject);
            }
            taskDao.AddAllCheckedObject(listCheckedObject);
            
            logger.info("3. Выполнение метода updateNameForCheckedObjectWithManualManage()");
            taskDao.updateNameForCheckedObjectWithManualManage(1000, "Отдел №111", 1001, "Отдел №222");

            logger.info("4. Выполнение метода updateNameForCheckedObjectWithUseSavepoint()");
            taskDao.updateNameForCheckedObjectWithUseSavepoint(1006, "Отдел № 7-7-7", "Отдел № 777");

            logger.info("5. Выполнение метода deleteCheckedObjectWithRollBack()");
            taskDao.deleteCheckedObjectWithRollBack(1007, 1005);
        }

    }
