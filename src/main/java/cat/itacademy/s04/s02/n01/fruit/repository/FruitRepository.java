package cat.itacademy.s04.s02.n01.fruit.repository;

import cat.itacademy.s04.s02.n01.fruit.model.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long> {
}
