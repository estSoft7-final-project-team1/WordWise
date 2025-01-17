package est.wordwise.domain.example.service;

import static org.assertj.core.api.Assertions.assertThat;

import est.wordwise.common.entity.Example;
import est.wordwise.common.entity.Word;
import est.wordwise.domain.example.dto.ExampleDto;
import est.wordwise.domain.word.dto.WordCreateDto;
import est.wordwise.domain.word.service.WordService;
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
class ExampleServiceTest {

    @Autowired
    private WordService wordService;

    @Autowired
    private ExampleService exampleService;

    private Word word;
    private ExampleDto exampleDto;

    @BeforeEach
    @Rollback
    void setUp() {
        WordCreateDto wordCreateDto = WordCreateDto.of("general", "일반적인,대체적인,장군");
        exampleDto = ExampleDto.builder()
            .sentence("This is a general overview of the project.")
            .sentenceMeaning("이것은 프로젝트의 일반적인 개요입니다.")
            .build();

        word = wordService.createWord(wordCreateDto);
    }

    @Rollback
    @Test
    @DisplayName("Example create 테스트")
    void createExampleTest() throws Exception {
        Example findExample = exampleService.createExample(word, exampleDto);

        assertThat(findExample.getWord().getWordText()).isEqualTo("general");
        assertThat(findExample.getSentence()).contains(
            "This is a general overview of the project.");
        assertThat(word.getExamples().size()).isEqualTo(1);
    }
}