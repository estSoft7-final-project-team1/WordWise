package est.wordwise.common.repository;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface WordBookRepository extends JpaRepository<WordBook, Long> {
    List<WordBook> findByMember(Member member);

}
