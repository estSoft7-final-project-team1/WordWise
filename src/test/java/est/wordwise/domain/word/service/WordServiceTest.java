package est.wordwise.domain.word.service;

import static org.assertj.core.api.Assertions.assertThat;

import est.wordwise.common.entity.Word;
import est.wordwise.domain.alanapi.service.AlanApiService;
import est.wordwise.domain.example.dto.ExampleDto;
import est.wordwise.domain.word.dto.WordCreateDto;
import est.wordwise.domain.word.dto.WordDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
@Transactional
class WordServiceTest {

    @Autowired
    private WordService wordService;
    private WordCreateDto wordCreateDto;
    private ExampleDto exampleDto;
    private AlanApiService alanApiService;

    @BeforeEach
    void setUp() {
        wordCreateDto = WordCreateDto.of("general", "일반적인,대체적인,장군");
        exampleDto = ExampleDto.builder()
            .sentence("This is a general overview of the project.")
            .sentenceMeaning("이것은 프로젝트의 일반적인 개요입니다.")
            .build();
    }

    @Test
    @Rollback
    @DisplayName("Word create 테스트")
    void createWordTest() {
        Word findWord = wordService.createWord(wordCreateDto);

        assertThat(findWord.getWordText()).isEqualTo(wordCreateDto.getWordText());
        assertThat(findWord.getDefinition()).isEqualTo(wordCreateDto.getDefinition());
    }

    @Test
    @Rollback
    @DisplayName("Word read 테스트")
    void readWordTest() {
        wordService.createWord(wordCreateDto);
        Word findWord = wordService.getWordByWordText(wordCreateDto.getWordText());

        assertThat(findWord.getDefinition()).isEqualTo(wordCreateDto.getDefinition());
    }

//    @Test
//    @DisplayName("Alan API 이용 WordDto 반환 테스트")
//    void generateWordDtoByWordTextTest() throws Exception {
//        String wordText = "general";
//
//        WordDto wordDto = wordService.generateWordDtoByWordText(wordText);
//
//        assertThat(wordDto.getWordText()).isEqualTo(wordText);
//        assertThat(wordDto.getDefinition()).contains("일반적인");
//        assertThat(wordDto.getDefinition()).contains("장군");
//    }
}