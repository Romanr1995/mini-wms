package ru.nozdrachev.miniwms.repo;

import org.springframework.data.repository.CrudRepository;
import ru.nozdrachev.miniwms.entity.ProductEntity;

import java.util.Optional;

public interface ProductRepo extends CrudRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByName(String productName);
}
