package est.wordwise.domain.wordbook.controller;

import est.wordwise.common.entity.WordBook;
import est.wordwise.domain.wordbook.dto.WordBookResponse;
import est.wordwise.domain.wordbook.service.WordBookService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wordbook")
public class WordBookController {

    // 컨트롤러에서 비즈니스 로직 작성 X

    // 요청 데이터 dto로 변환 -> service 레이어에서 비즈니스 로직 작성

    private final WordBookService wordBookService;

    public WordBookController(WordBookService wordBookService) {
        this.wordBookService = wordBookService;
    }

    // 멤버별 단어장 {memberId로} 조회
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<WordBookResponse>> getWordBooksByMember(
        @PathVariable Long memberId) {
        List<WordBookResponse> responses = wordBookService.getWordBooksByMember(memberId);
        return ResponseEntity.ok(responses);
    }

    // ../member/{memberId}/search?keyword={keyword}
    // 멤버별 단어장에서 단어 검색
    @GetMapping("/member/{memberId}/search")
    public ResponseEntity<List<WordBookResponse>> searchWordBook(@PathVariable Long memberId,
        @RequestParam String keyword) {
        List<WordBookResponse> responses = wordBookService.searchWordBook(memberId, keyword);
        return ResponseEntity.ok(responses);
    }

    // 내 단어장 추가, 요청본문 자바객체로 변환
    @PostMapping
    public WordBook addWordBook(@RequestBody WordBook wordBook) {
        return wordBookService.addWordBook(wordBook);
    }

    // 특정 단어장 {id}로 조회
    @GetMapping("/{id}")
    public ResponseEntity<WordBook> getWordBookById(@PathVariable Long id) {
        return wordBookService.getWordBookById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // 단어장 {id}로 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWordBook(@PathVariable Long id) {
        wordBookService.deleteWordBook(id);
        return ResponseEntity.noContent().build();
    }

    // 단어장 수정 추가해야함

    // 검색해서 단어 찾기, keyword = 텍스트 또는 단어 의미
    // 요청파라미터 바인딩해서 DTO 그대로 반환,

//    @GetMapping("/search")
//    public List<WordBook> searchWords(@RequestParam Long memberId, @RequestParam String keyword) {
//        return wordBookService.searchWordBook(memberId, keyword);
//    }

}