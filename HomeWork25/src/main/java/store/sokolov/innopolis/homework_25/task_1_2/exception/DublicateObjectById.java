package store.sokolov.innopolis.homework_25.task_1_2.exception;

public class DublicateObjectById extends RuntimeException {
    public DublicateObjectById(String message) {
        super(message);
    }
}
