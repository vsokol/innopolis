package store.sokolov.innopolis.homework_22.task_1_2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;

    /**
     * Описание задачи - tasks.md
     * Инициализация БД. Запросы читаются из каталога src/main/java/store/sokolov/innopolis/homework_22/sql и выполняются на бд.
     */
    public class TestLogger {
        public static DBInit db;
        private static final Logger logger = LoggerFactory.getLogger("TestLogger");

        public static void main(String[] args) throws SQLException, IOException {
            try {
                logger.info("Старт Main");
                System.out.println("Домашнее задание 19");
                logger.info("Подготовка базы");
                db = new DBInit("jdbc:postgresql://172.17.0.2:5432/testDB?user=postgres&password=Asdf4321", "src/main/java/store/sokolov/innopolis/homework_22/sql");
                logger.info("-- Сброс и инициализация базы данных --");
                db.executeSQLs();
                logger.info("База готова");
                logger.info("Выполнение метода task1()");
                task1();
                logger.info("Выполнение метода task2()");
                task2();
                logger.info("Выполнение метода task3()");
                task3();
                logger.info("Выполнение метода task4()");
                task4();
                logger.info("Выполнение метода task5()");
                task5();
            } catch (SQLException | IOException exception) {
                logger.error("Ошибка однако...", exception);
                throw exception;
            }
        }

        /**
         * Применить параметризованный запрос
         */
        public static void task1() throws SQLException {
            Logger logger = LoggerFactory.getLogger("task");
            logger.info("task1: Задача - Применить параметризованный запрос");
            String sql = "select " +
                    "  id" +
                    ", type_id" +
                    ", checklist_id" +
                    ", name" +
                    ", is_required" +
                    ", descr" +
                    " from checklist_item" +
                    " where checklist_id = ?";
            logger.debug("task1: sql = " + sql);
            PreparedStatement statement = db.getConnection().prepareStatement(sql);
            long checklistId = 1001;
            statement.setLong(1, checklistId);
            logger.debug("task1: Параметр 1 (checklistId) = " + checklistId);
            ResultSet resultSet = statement.executeQuery();
            logger.info("task1: запрос успешно выполнен");
            int count = 0;
            while (resultSet.next()) {
                String data = "{id = " + resultSet.getLong("id") + "\t"
                        + "checklist_id = " + resultSet.getLong("checklist_id") + "\t"
                        + "type_id = " + resultSet.getLong("type_id") + "\t"
                        + "name = " + resultSet.getString("name") + "\t"
                        + "is_required = " + resultSet.getBoolean("is_required") + "\t"
                        + "descr = " + resultSet.getString("descr") + "}";
                logger.trace("task1: row = " + data);
                count++;
            }
            logger.info("task1: получено " + count + " записей");
        }

        /**
         * Применить батчинг
         */
        public static void task2() throws SQLException {
            Logger logger = LoggerFactory.getLogger("task");
            logger.info("task2: Задача - Применить батчинг --");
            Connection connection = db.getConnection();
            String sql = "insert into checked_object (parent_id, name, descr) values (?, ?, ?)";
            logger.debug("task2: sql = " + sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 1; i <= 10; i++) {
                long parentId = 1004;
                String name = "Отдел №" + i;
                String descr = "Описание отдела №" + i;
                statement.setLong(1, parentId);
                statement.setString(2, name);
                statement.setString(3, descr);
                statement.addBatch();
                logger.trace("task2: Параметры { 1 (parent_id) = " + parentId + ", 2 (name) = " + name + ", 3 (descr) = " + descr + "}");
            }
            statement.executeBatch();
            statement.close();

            printCheckedObject(connection);
        }

        /**
         * Использовать ручное управление транзакциями
         */
        public static void task3() throws SQLException {
            Logger logger = LoggerFactory.getLogger("task");
            logger.info("task3: Задача - Использовать ручное управление транзакциями --");
            Connection connection = db.getConnection();
            connection.setAutoCommit(false);
            String sql = "update checked_object" +
                    " set name = ?" +
                    " where id = ?";
            logger.debug("task3: sql = " + sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            String name = "Отдел №111";
            long id = 10000;
            statement.setString(1, name);
            statement.setLong(2, id);
            logger.debug("task3: Параметры {1 (name) = " + name + ", 2 (id) = " + id);
            statement.executeUpdate();
            logger.info("task3: запрос выполнен");
            connection.rollback();
            logger.info("task3: откат транзакции");

            statement = connection.prepareStatement(sql);
            name = "Отдел №111";
            id = 10000;
            statement.setString(1, name);
            statement.setLong(2, id);
            logger.debug("task3: Параметры {1 (name) = " + name + ", 2 (id) = " + id);
            statement.executeUpdate();
            logger.info("task3: запрос выполнен");
            connection.commit();
            logger.info("task3: commit транзакции");

            printCheckedObject(connection);
        }

        /**
         * - Предусмотреть использование savepoint при выполнении логики из нескольких запросов
         */
        public static void task4() throws SQLException {
            Logger logger = LoggerFactory.getLogger("task");
            logger.info("task4: Задача - Предусмотреть использование savepoint при выполнении логики из нескольких запросов --");
            Connection connection = db.getConnection();
            connection.setAutoCommit(false);
            Savepoint sp1 = connection.setSavepoint();
            logger.info("task4: установлен Savepoint sp1");

            String sql = "update checked_object" +
                    " set name = ?" +
                    " where id = ?";
            logger.debug("task4: sql = " + sql);
            PreparedStatement statement = connection.prepareStatement(sql);
            String name = "Отдел №6-6-6";
            long id = 10005;
            statement.setString(1, name);
            statement.setLong(2, id);
            logger.debug("task4: Параметры {1 (name) = " + name + ", 2 (id) = " + id);
            statement.executeUpdate();
            Savepoint sp2 = connection.setSavepoint();
            logger.info("task4: установлен Savepoint sp2");

            logger.debug("task4: sql = " + sql);
            statement = connection.prepareStatement(sql);
            name = "Отдел №666";
            id = 10005;
            logger.debug("task4: Параметры {1 (name) = " + name + ", 2 (id) = " + id);
            statement.setString(1, name);
            statement.setLong(2, id);
            statement.executeUpdate();
            Savepoint sp3 = connection.setSavepoint();
            logger.info("task4: установлен Savepoint sp3");

            connection.rollback(sp2);
            logger.info("task4: откат к Savepoint sp2");
            connection.commit();
            logger.info("task4: commit изменений");
            printCheckedObject(connection);
        }

        /**
         * Предусмотреть rollback операций при ошибках
         */
        public static void task5() throws SQLException {
            Logger logger = LoggerFactory.getLogger("task");
            logger.info("task5: Задача - Предусмотреть rollback операций при ошибках --");
            Connection connection = db.getConnection();
            try {
                connection.setAutoCommit(false);

                String sql = "delete from checked_object" +
                        " where id = ?";
                logger.debug("task5: sql = " + sql);
                PreparedStatement statement = connection.prepareStatement(sql);
                long id = 10007;
                statement.setLong(1, id);
                logger.debug("task5: Параметры {1 (id) = " + id);
                statement.execute();

                sql = "delete from checked_object" +
                        " where id1 = ?";
                logger.debug("task5: sql = " + sql);
                statement = connection.prepareStatement(sql);
                id = 10005;
                statement.setLong(1, id);
                logger.debug("task5: Параметры {1 (id) = " + id);
                statement.execute();

                connection.commit();
            } catch (SQLException e) {
                logger.error("task5: Ошибка при выполнении метода task5()", e);
                connection.rollback();
            } finally {
                printCheckedObject(connection);
            }
        }

        /**
         * Вывод в консоль выборки из таблицы checked_object
         * @param connection соединение с бд
         * @throws SQLException
         */
        public static void printCheckedObject(Connection connection) throws SQLException {
            Logger logger = LoggerFactory.getLogger("task");
            logger.info("printCheckedObject: старт");
            Statement statementSQL = connection.createStatement();
            String sql = "select id, parent_id, name, descr" +
                    " from checked_object";
            logger.debug("printCheckedObject: sql = " + sql);
            ResultSet resultSet = statementSQL.executeQuery(sql);
            logger.info("printCheckedObject: запрос выполнен");
            int count = 0;
            while (resultSet.next()) {
                String data = "{id = " + resultSet.getLong("id") + "\t"
                        + "parent_id = " + resultSet.getLong("parent_id") + "\t"
                        + "name = " + resultSet.getString("name") + "\t"
                        + "descr = " + resultSet.getString("descr") + "\t";
                logger.trace("printCheckedObject: row = " + data);
                count++;
            }
            logger.info("printCheckedObject: получено " + count + " записей");
        }
    }
