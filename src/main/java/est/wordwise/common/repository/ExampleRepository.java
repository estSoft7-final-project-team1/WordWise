package est.wordwise.common.repository;

import est.wordwise.common.entity.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {
    @Override
    Optional<Example> findById(Long Long);
}
