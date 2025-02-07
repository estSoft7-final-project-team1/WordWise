package est.wordwise.common.repository;

import est.wordwise.common.entity.Example;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.WordBook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalExampleRepository extends JpaRepository<PersonalExample, Long> {

    List<PersonalExample> findByWordBook(WordBook wordBook);

    Optional<PersonalExample> findByWordBookAndExample(WordBook wordBook, Example example);
}
