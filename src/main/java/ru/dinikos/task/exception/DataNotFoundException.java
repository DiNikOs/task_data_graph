/**
 * @author Ostrovskiy Dmitriy
 * @created 09.06.2020
 * DataNotFoundException
 * @version v1.0
 */

package ru.dinikos.task.exception;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
