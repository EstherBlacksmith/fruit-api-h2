package cat.itacademy.s04.s02.n01.fruit.order.dto;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "orders")
public class Order {
    String id;
    String clientName;
    LocalDate deliveryDate;
    List<OrderItem> items;
}
