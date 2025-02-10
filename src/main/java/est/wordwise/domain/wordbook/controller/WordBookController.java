package est.wordwise.domain.wordbook.controller;

import est.wordwise.domain.wordbook.dto.WordBookDto;
import est.wordwise.domain.wordbook.dto.WordCountDto;
import est.wordwise.domain.wordbook.service.WordBookSearchService;
import est.wordwise.domain.wordbook.service.WordBookService;
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
@RequestMapping("/api/wordbook")
public class WordBookController {

    private final WordBookService wordBookService;
    private final WordBookSearchService wordBookSearchService;

    // 로그인한 유저의 WordBook 리스트를 페이지로 반환
    @GetMapping
    public ResponseEntity<Page<WordBookDto>> getAllWordBooks(
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        Page<WordBookDto> paging = wordBookService.getWordBookList(page);

        return ResponseEntity.ok(paging);
    }

    // wordText로 개인 단어장 중에서 검색
    @GetMapping("/{wordText}")
    public ResponseEntity<?> getWordBookByWordText(@PathVariable String wordText) {
        WordBookDto wordBookDto = wordBookSearchService.getWordBookByWordText(wordText);

        // 해당 단어가 없으면 에러
        if (wordBookDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("NOT FOUND");
        }

        return ResponseEntity.ok(wordBookDto);
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

    // 단어별 단어장 개수로 랭킹 조회(페이징)
    @GetMapping("/ranking")
    public ResponseEntity<Page<WordCountDto>> getWordBookRankingInPage(
        @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(wordBookService.getWordBookRankingInPage(page));
    }
}