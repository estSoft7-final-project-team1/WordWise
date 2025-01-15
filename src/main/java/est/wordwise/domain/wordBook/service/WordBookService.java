package est.wordwise.domain.wordBook.service;

import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.domain.wordBook.dto.WordBookResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WordBookService {
    private final WordBookRepository wordBookRepository;

    public WordBookService(WordBookRepository repository) {
        this.wordBookRepository = repository;
    }

    // 단어장 추가, 먼저 완성 후에 테스트, 저장 테스트 먼저 만들고
    public WordBook addWordBook(WordBook wordBook) {

        // dto 에서 받은 정보 + 여기서 정보생성한 다음에 데이터베이스에 저장

        return wordBookRepository.save(wordBook);
    }

    // 단어장 조회, 추후 페이징처리 여기서?
    public Optional<WordBook> getWordBookById(Long id) {
        return wordBookRepository.findById(id);
    }

    // 멤버별 단어장 조회
    public List<WordBookResponse> getWordBooksByMember(Long memberId) {
        // 멤버 ID로 단어장 조회
        List<WordBook> wordBooks = wordBookRepository.findByMemberIdAndDeletedFalse(memberId);

        // DTO로 변환 후 반환
        return wordBooks.stream()
                .map(WordBookResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 멤버별 단어장에서 단어 검색
    public List<WordBookResponse> searchWordBook(Long memberId, String keyword) {

        // 빈 keyword일 경우 memberid로 모든 단어장 조회
        if( keyword == null || keyword.isEmpty() ) {

            List<WordBook> wordBooks = wordBookRepository.findByMemberIdAndDeletedFalse(memberId);

            // wordBooks를 DTO로 변환 후 반환
            return wordBooks.stream()
                    .map(WordBookResponse::fromEntity)
                    .collect(Collectors.toList());

        }
        List<WordBook> wordBooks = wordBookRepository.searchWordBook(memberId, keyword);

        // searchWordBook의 wordBook DTO로 매핑 후 반환
        return wordBooks.stream()
                .map(WordBookResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 단어장 삭제
    public void deleteWordBook(Long id) {
        wordBookRepository.deleteById(id);
    }

    public WordBookResponse getWordBookResponse(Long id) {
        WordBook wordBook = wordBookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("WordBook not found"));
        return WordBookResponse.fromEntity(wordBook);
    }

    // 여기서 builder패턴으로 dto반환

    // builder 패턴, of, from, to entity로 여기서 생성?, 서비스에서 dto만 넘겨주고 entity에서?
    // WordBook 엔티티를 -> WordBookRequest, WordBookResponse

    // 서비스에서 비즈니스로직 작성

    // 전에는 따로 DTO만들어서 엔티티 생성
    // 서비스에서 WordBookRequest로 WordBook 엔티티 생성?


}
