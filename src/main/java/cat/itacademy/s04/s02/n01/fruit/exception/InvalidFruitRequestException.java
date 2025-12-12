package cat.itacademy.s04.s02.n01.fruit.exception;

public class InvalidFruitRequestException extends RuntimeException {
    public InvalidFruitRequestException(String message) {
        super(message);
    }
}
