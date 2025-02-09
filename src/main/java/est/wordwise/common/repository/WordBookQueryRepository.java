package est.wordwise.common.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import est.wordwise.common.entity.QWord;
import est.wordwise.common.entity.QWordBook;
import est.wordwise.domain.wordbook.dto.WordCountDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WordBookQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<WordCountDto> getWordBookRanking() {
        QWordBook wordBook = QWordBook.wordBook;
        QWord word = QWord.word;

        return queryFactory
            .select(Projections.constructor(
                WordCountDto.class,
                word.wordText,
                wordBook.id.count()
            ))
            .from(wordBook)
            .join(wordBook.word, word)
            .where(wordBook.deleted.eq(false))
            .groupBy(word.wordText)
            .orderBy(wordBook.id.count().desc())
            .fetch();
    }
}
