package cat.itacademy.s04.s02.n01.fruit.exception;

public class ProviderNotFoundException extends RuntimeException  {
    public ProviderNotFoundException(String message) {
        super(message);
    }
}
