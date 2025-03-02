package est.wordwise.common.repository;


import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.Word;
import est.wordwise.common.entity.WordBook;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WordBookRepository extends JpaRepository<WordBook, Long> {

    List<WordBook> findByMember(Member member);

    // JPA로 DB와 상호작용
    // deleted = false 조건의, 삭제되지 않은 멤버의 모든 단어장 조회
    List<WordBook> findByMemberIdAndDeletedFalse(Long memberId);

    // 커스텀 쿼리로 나중에 변경 하기 쉽게
    // 특정 회원의 id의 단어장에서 텍스트와 일치하거나, 의미가 맞는 단어 조회
    @Query("SELECT wb FROM WordBook wb WHERE wb.member.id = :memberId " +
        "AND wb.deleted = false " +
        "AND (wb.word.wordText LIKE %:keyword% OR wb.word.definition LIKE %:keyword%)")
    List<WordBook> searchWordBook(@Param("memberId") Long memberId,
        @Param("keyword") String keyword);

    Optional<WordBook> findByMemberAndWordAndDeletedFalse(Member member, Word word);

    Page<WordBook> findAllByMemberIdAndDeletedFalse(Long memberId, Pageable pageable);

    Optional<WordBook> findByIdAndDeletedFalse(Long id);

    // Optional<WordBook> findByMemberIdAndWordIdandDeletedFalse(Long memberId, Long wordId);
}

