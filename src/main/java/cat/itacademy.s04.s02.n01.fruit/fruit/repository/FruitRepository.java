package cat.itacademy.s04.s02.n01.fruit.fruit.repository;

import cat.itacademy.s04.s02.n01.fruit.fruit.dto.Fruit;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Long> {
    List<Fruit> findByProvider_Id(Long providerId);
}
