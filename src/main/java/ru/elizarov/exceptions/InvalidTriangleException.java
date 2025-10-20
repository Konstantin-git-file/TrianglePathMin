package ru.elizarov.exceptions;

/**
 * ошибка валидации
 */
public class InvalidTriangleException extends RuntimeException {
    public InvalidTriangleException(String message) {
        super(message);
    }
}