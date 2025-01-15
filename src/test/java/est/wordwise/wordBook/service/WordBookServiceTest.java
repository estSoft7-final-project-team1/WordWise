package est.wordwise.wordBook.service;

import est.wordwise.entity.Member;
import est.wordwise.entity.Word;
import est.wordwise.entity.WordBook;
import est.wordwise.repository.WordBookRepository;
import est.wordwise.wordBook.dto.WordBookResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WordBookServiceTest {

    @Mock
    private WordBookRepository wordBookRepository;

    @InjectMocks
    private WordBookService wordBookService;

    @Test
    @DisplayName("멤버별 단어장 가져오기")
    public void test_get_wordbook_by_member() {
        // given, 데이터 준비

//        Member member = Member.builder()


//        WordBook wordBook = WordBook.builder()
//                .id("test1")
//                .member("ex1")
//                .word("")

//        return WordBookResponse.builder()
//                .id(wordBook.getId())
//                .wordText(wordBook.getWord().getWordText())
//                .definition(wordBook.getWord().getDefinition())
//                .testCount(wordBook.getTestCount())
//                .failCount(wordBook.getFailCount())
//                .createdAt(formattedDate)
//                .build();


        // 특정 word, new 생성자로 멤버 만들고, 저장을 하는 서비스 코드 먼저 테스트해서
        // 저장되었는지 검증 후에, assertJ로 검증하려면 조회, getwordBookbyId
        // memberId로 건네줌으로서 wordbook 객체의 word를 가져와서
        // 추가 메서드로 저장했을때와, 조회 했을때의 값이 같을 때
        wordBookService.getWordBooksByMember("")


        // member, word 테스트
        Long memberId = 1L;
        List<WordBook> wordBooks = Arrays.asList(new WordBook(), new WordBook());
        when(wordBookRepository.findByMemberIdAndDeletedFalse(memberId)).thenReturn(wordBooks);

        // when, 비즈니스로직
        List<WordBookResponse> responses = wordBookService.getWordBooksByMember(memberId);

        // then, 결과 검증
        assertNotNull(responses);

        // 반환된 리스트 크기 2 확인
        assertEquals(2, responses.size());
    }

    @Test
    @DisplayName("검색 키워드 빈 문자열 일때 단어장 가져오기")
    public void test_search_empty_wordbook() {
        // given, 데이터 준비
        Long memberId = 1L;
        String keyword = "";
        List<WordBook> wordBooks = Arrays.asList(new WordBook(), new WordBook());
        when(wordBookRepository.findByMemberIdAndDeletedFalse(memberId)).thenReturn(wordBooks);

        // when, 비즈니스로직
        List<WordBookResponse> responses = wordBookService.searchWordBook(memberId, keyword);

        // then, 결과 검증
        assertNotNull(responses);
        assertEquals(2, responses.size());
    }

    @Test
    @DisplayName("검색 키워드로 단어장 가져오기")
    public void test_search_wordbook() {
        // given, 데이터 준비

        Long memberId = 1L;
        String keyword = "test";
        List<WordBook> wordBooks = Arrays.asList(new WordBook(), new WordBook());
        when(wordBookRepository.searchWordBook(memberId, keyword)).thenReturn(wordBooks);

        // when, 비즈니스 로직
        List<WordBookResponse> responses = wordBookService.searchWordBook(memberId, keyword);

        // then, 겸과
        assertNotNull(responses);
        assertEquals(2, responses.size());
    }
}