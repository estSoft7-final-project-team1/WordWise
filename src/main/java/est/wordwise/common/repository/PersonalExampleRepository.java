package est.wordwise.common.repository;

import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalExampleRepository extends JpaRepository<PersonalExample, Long> {
    List<PersonalExample> findByWordBook(WordBook wordBook);
}
