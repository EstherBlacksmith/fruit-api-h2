package cat.itacademy.s04.s02.n01.fruit.provider.repository;

import cat.itacademy.s04.s02.n01.fruit.provider.dto.Provider;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {
    Optional<Provider> findByName(String Name);
}
