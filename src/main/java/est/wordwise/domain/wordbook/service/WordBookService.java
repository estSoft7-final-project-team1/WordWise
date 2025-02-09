package est.wordwise.domain.wordbook.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.Word;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.WordBookQueryRepository;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.domain.security.service.MemberService;
import est.wordwise.domain.wordbook.dto.WordBookDto;
import est.wordwise.domain.wordbook.dto.WordCountDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordBookService {

    private static final int PAGE_SIZE = 10;

    private final WordBookRepository wordBookRepository;
    private final WordBookQueryRepository wordBookQueryRepository;
    private final MemberService memberService;

    public WordBook createWordBook(Member member, Word word) {
        return wordBookRepository.save(WordBook.of(member, word));
    }

    public WordBook getWordBookByMemberAndWord(Member member, Word word) {
        return wordBookRepository.findByMemberAndWordAndDeletedFalse(member, word).orElse(null);
    }

    // 단어장 전체 조회(페이징)
    public Page<WordBookDto> getWordBookList(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);

        Member member = memberService.getCurrentMember();

        Page<WordBook> wordBooks = wordBookRepository.findAllByMemberIdAndDeletedFalse(
            member.getId(), pageable);

        return wordBooks.map(WordBookDto::from);
    }

    // id로 단어장 상세 조회
    public WordBook getWordBookById(Long wordBookId) {
        return wordBookRepository.findByIdAndDeletedFalse(wordBookId).orElse(null);
    }

    // id로 단어장 상세 조회(dto)
    public WordBookDto getWordBookDtoById(Long id) {
        return WordBookDto.from(getWordBookById(id));
    }

    // id로 단어장 삭제
    @Transactional
    public WordBookDto deleteWordBook(Long wordBookId) {
        WordBook wordBook = getWordBookById(wordBookId);
        wordBook.softDelete();

        return WordBookDto.from(wordBook);
    }

    public List<WordCountDto> getWordBookRanking() {
        return wordBookQueryRepository.getWordBookRanking();
    }

    // 단어별 단어장 등록 횟수(페이징)
    public Page<WordCountDto> getWordBookRankingInPage(int page) {
        List<WordCountDto> wordCountDtos = wordBookQueryRepository.getWordBookRanking();
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), wordCountDtos.size());

        return new PageImpl<>(wordCountDtos.subList(start, end),
            pageRequest,
            wordCountDtos.size());
    }
}
