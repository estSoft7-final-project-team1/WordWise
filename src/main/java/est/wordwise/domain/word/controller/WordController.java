package est.wordwise.domain.word.controller;

import est.wordwise.domain.word.dto.WordDto;
import est.wordwise.domain.word.service.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;

    @GetMapping("/{word}")
    public ResponseEntity<WordDto> getWord(@PathVariable String word) {
        return ResponseEntity.ok(
            wordService.getWordAndExamples(word)
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Long> createWord(@RequestBody WordDto wordDto) {
        return ResponseEntity.ok(
            wordService.saveWordAndExamples(wordDto)
        );
    }

    @GetMapping("/{word}/reload")
    public ResponseEntity<WordDto> reloadWord(@RequestBody WordDto wordDto) {
        return ResponseEntity.ok(
            wordService.reloadWordAndExamples(wordDto)
        );
    }
}
