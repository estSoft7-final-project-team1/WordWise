package est.wordwise.common.repository;

import est.wordwise.common.entity.Word;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {

    Optional<Word> findByWordText(String wordText);
}
