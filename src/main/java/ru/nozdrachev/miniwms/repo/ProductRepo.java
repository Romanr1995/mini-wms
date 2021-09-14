package ru.nozdrachev.miniwms.repo;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ru.nozdrachev.miniwms.entity.ProductEntity;

public interface ProductRepo extends CrudRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String name);
}
