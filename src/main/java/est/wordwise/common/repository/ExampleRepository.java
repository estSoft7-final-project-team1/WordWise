package est.wordwise.common.repository;

import est.wordwise.common.entity.Example;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {

    List<Example> findTop5ByWordIdAndDeletedFalse(Long wordId);

    List<Example> findTop5ByWordIdAndIdNotInAndDeletedFalse(Long wordId, List<Long> excludedIds);
}