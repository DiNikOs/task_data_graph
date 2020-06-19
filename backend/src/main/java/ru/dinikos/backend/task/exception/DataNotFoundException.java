/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataNotFoundException
 * @version v1.0
 */

package ru.dinikos.backend.task.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
