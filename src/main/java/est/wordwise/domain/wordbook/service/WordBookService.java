package est.wordwise.domain.wordbook.service;

import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.Word;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.WordBookQueryRepository;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.common.util.MemberService;
import est.wordwise.domain.wordbook.dto.WordBookDto;
import est.wordwise.domain.wordbook.dto.WordCountDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

//    // 단어장 추가, 먼저 완성 후에 테스트, 저장 테스트 먼저 만들고
//    public WordBook addWordBook(WordBook wordBook) {
//
//        // dto 에서 받은 정보 + 여기서 정보생성한 다음에 데이터베이스에 저장
//
//        return wordBookRepository.save(wordBook);
//    }
//
//    // 멤버별 단어장 조회
//    public List<WordBookResponse> getWordBooksByMember(Long memberId) {
//        // 멤버 ID로 단어장 조회
//        List<WordBook> wordBooks = wordBookRepository.findByMemberIdAndDeletedFalse(memberId);
//
//        // DTO로 변환 후 반환
//        return wordBooks.stream().map(WordBookResponse::fromEntity).collect(Collectors.toList());
//    }

//    // 멤버별 단어장에서 단어 검색
//    public List<WordBookResponse> searchWordBook(Long memberId, String keyword) {
//
//        // 빈 keyword일 경우 memberid로 모든 단어장 조회
//        if (keyword == null || keyword.isEmpty()) {
//
//            List<WordBook> wordBooks = wordBookRepository.findByMemberIdAndDeletedFalse(memberId);
//
//            // wordBooks를 DTO로 변환 후 반환
//            return wordBooks.stream().map(WordBookResponse::fromEntity)
//                .collect(Collectors.toList());
//
//        }
//        List<WordBook> wordBooks = wordBookRepository.searchWordBook(memberId, keyword);
//
//        // searchWordBook의 wordBook DTO로 매핑 후 반환
//        return wordBooks.stream().map(WordBookResponse::fromEntity).collect(Collectors.toList());
//    }
//
//    // 단어장 삭제
//    public void deleteWordBook(Long id) {
//        wordBookRepository.deleteById(id);
//    }
//
//    public WordBookResponse getWordBookResponse(Long id) {
//        WordBook wordBook = wordBookRepository.findById(id)
//            .orElseThrow(() -> new IllegalArgumentException("WordBook not found"));
//        return WordBookResponse.fromEntity(wordBook);
//    }

    // 여기서 builder패턴으로 dto반환

    // builder 패턴, of, from, to entity로 여기서 생성?, 서비스에서 dto만 넘겨주고 entity에서?
    // WordBook 엔티티를 -> WordBookRequest, WordBookResponse

    // 서비스에서 비즈니스로직 작성

    // 전에는 따로 DTO만들어서 엔티티 생성
    // 서비스에서 WordBookRequest로 WordBook 엔티티 생성?

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
}
