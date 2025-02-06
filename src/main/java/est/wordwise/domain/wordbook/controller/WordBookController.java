package est.wordwise.domain.wordbook.controller;

import est.wordwise.domain.wordbook.dto.WordBookDto;
import est.wordwise.domain.wordbook.dto.WordCountDto;
import est.wordwise.domain.wordbook.service.WordBookSearchService;
import est.wordwise.domain.wordbook.service.WordBookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wordbook")
public class WordBookController {

    private final WordBookService wordBookService;
    private final WordBookSearchService wordBookSearchService;

//    // 멤버별 단어장 {memberId로} 조회
//    @GetMapping("/member/{memberId}")
//    public ResponseEntity<List<WordBookResponse>> getWordBooksByMember(
//        @PathVariable Long memberId) {
//        List<WordBookResponse> responses = wordBookService.getWordBooksByMember(memberId);
//        return ResponseEntity.ok(responses);
//    }
//
//    // ../member/{memberId}/search?keyword={keyword}
//    // 멤버별 단어장에서 단어 검색
//    @GetMapping("/member/{memberId}/search")
//    public ResponseEntity<List<WordBookResponse>> searchWordBook(@PathVariable Long memberId,
//        @RequestParam String keyword) {
//        List<WordBookResponse> responses = wordBookService.searchWordBook(memberId, keyword);
//        return ResponseEntity.ok(responses);
//    }
//
//    // 내 단어장 추가, 요청본문 자바객체로 변환
//    @PostMapping
//    public WordBook addWordBook(@RequestBody WordBook wordBook) {
//        return wordBookService.addWordBook(wordBook);
//    }
//
//    // 단어장 {id}로 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteWordBook(@PathVariable Long id) {
//        wordBookService.deleteWordBook(id);
//        return ResponseEntity.noContent().build();
//    }

    // 단어장 수정 추가해야함

    // 검색해서 단어 찾기, keyword = 텍스트 또는 단어 의미
    // 요청파라미터 바인딩해서 DTO 그대로 반환,

//    @GetMapping("/search")
//    public List<WordBook> searchWords(@RequestParam Long memberId, @RequestParam String keyword) {
//        return wordBookService.searchWordBook(memberId, keyword);
//    }

    // 로그인한 유저의 WordBook 리스트를 페이지로 반환
    @GetMapping
    public ResponseEntity<Page<WordBookDto>> getAllWordBooks(
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Page<WordBookDto> paging = wordBookService.getWordBookList(page);

        return ResponseEntity.ok(paging);
    }

    // wordText로 개인 단어장 중에서 검색
    @GetMapping("/search/{wordText}")
    public ResponseEntity<?> getWordBookByWordText(@PathVariable String wordText) {
        WordBookDto wordBookDto = wordBookSearchService.getWordBookByWordText(wordText);

        // 해당 단어가 없으면 에러
        if (wordBookDto == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("NOT FOUND");
        }

        return ResponseEntity.ok(wordBookSearchService.getWordBookByWordText(wordText));
    }

    // id로 단어장 페이지 조회
    @GetMapping("/{id}")
    public ResponseEntity<WordBookDto> getWordBook(@PathVariable Long id) {
        return ResponseEntity.ok(wordBookService.getWordBookDtoById(id));
    }

    // id로 단어장 페이지 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<WordBookDto> deleteWordBook(@PathVariable Long id) {
        return ResponseEntity.ok(wordBookService.deleteWordBook(id));
    }

    // 단어별 단어장 개수로 랭킹 조회
    @GetMapping("/ranking")
    public ResponseEntity<List<WordCountDto>> getWordBookRanking() {
        return ResponseEntity.ok(wordBookService.getWordBookRanking());
    }
}