package est.wordwise.common.repository;

import est.wordwise.common.entity.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordBookRepository extends JpaRepository<WordBook, Long> {

}
