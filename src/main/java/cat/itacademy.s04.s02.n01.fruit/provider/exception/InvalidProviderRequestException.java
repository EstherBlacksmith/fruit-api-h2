package cat.itacademy.s04.s02.n01.fruit.provider.exception;

public class InvalidProviderRequestException extends RuntimeException {
    public InvalidProviderRequestException(String message) {
        super(message);
    }
}
