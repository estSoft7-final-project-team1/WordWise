package est.wordwise.domain.word.service;

import static org.assertj.core.api.Assertions.assertThat;

import est.wordwise.common.entity.Example;
import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.Word;
import est.wordwise.common.entity.WordBook;
import est.wordwise.domain.security.service.MemberService;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.alanapi.service.AlanApiService;
import est.wordwise.domain.example.dto.ExampleDto;
import est.wordwise.domain.example.service.ExampleService;
import est.wordwise.domain.word.dto.WordCreateDto;
import est.wordwise.domain.word.dto.WordDto;
import est.wordwise.domain.wordbook.service.WordBookService;
import java.util.List;
import java.util.Optional;
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
    @Autowired
    private WordBookService wordBookService;
    @Autowired
    private AlanApiService alanApiService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ExampleService exampleService;

    private WordCreateDto wordCreateDto;
    private ExampleDto exampleDto;

    private WordDto wordDto;
    private Member member;
    private Word word;


    @BeforeEach
    @Rollback
    void setUp() {
        wordCreateDto = WordCreateDto.of("general", "일반적인,대체적인,장군");
        exampleDto = ExampleDto.builder().sentence("This is a general overview of the project.")
            .sentenceMeaning("이것은 프로젝트의 일반적인 개요입니다.").build();

        String jsonString = "{\"word\":\"consist\",\"definition\":\"구성되다, 이루어지다, 일치하다\",\"examples\":[{\"sentence\":\"The committee consists of ten members.\",\"sentenceMeaning\":\"위원회는 열 명의 구성원으로 이루어져 있다.\"},{\"sentence\":\"Her responsibilities consist of managing the team.\",\"sentenceMeaning\":\"그녀의 책임은 팀을 관리하는 것으로 구성된다.\"},{\"sentence\":\"The book consists of three main parts.\",\"sentenceMeaning\":\"그 책은 세 부분으로 구성되어 있다.\"},{\"sentence\":\"Success consists in hard work and determination.\",\"sentenceMeaning\":\"성공은 열심히 일하고 결단력에 달려 있다.\"},{\"sentence\":\"Their opinions consist with each other.\",\"sentenceMeaning\":\"그들의 의견은 서로 일치한다.\"}]}";
        ResponseContent responseContent = alanApiService.parseJsonToResponseContent(jsonString);
        wordDto = WordDto.from(responseContent);

//        member = memberService.getCurrentMember();
//
//        if (member == null) {
//            member = memberService.createMember(
//                Member.builder().provider(SocialType.NAVER).email("member1@email.com")
//                    .nickname("member1").password("1234").build());
//        }
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

    @Test
    @DisplayName("Alan API 이용 WordDto 반환 테스트")
    void generateWordAndExamplesTest() throws Exception {
        String wordText = "consist";

        WordDto wordDto = wordService.generateWordAndExamples(wordText);

        assertThat(wordDto.getWordText()).isEqualTo(wordText);
        for (ExampleDto exampleDto : wordDto.getExampleDtos()) {
            assertThat(exampleDto.getSentence()).contains(wordText);
        }
    }

    @Test
    @DisplayName("Alan api 이용 예문 재생성 테스트")
    void regenerateWordAndExamples() throws Exception {
        String wordText = "consist";

        WordDto wordDto1 = wordService.generateWordAndExamples(wordText);
        WordDto wordDto2 = wordService.regenerateWordAndExamples(wordText);

        assertThat(wordDto1.getDefinition()).isEqualTo(wordDto2.getDefinition());
        for (ExampleDto exampleDto1 : wordDto1.getExampleDtos()) {
            for (ExampleDto exampleDto2 : wordDto2.getExampleDtos()) {
                log.info("exampleDto1.getSentence() = {}", exampleDto1.getSentence());
                log.info("exampleDto2.getSentence() = {}", exampleDto2.getSentence());

                assertThat(exampleDto1.getSentence()).isNotEqualTo(exampleDto2.getSentence());
            }
        }
    }

    @Test
    @Rollback
    @DisplayName("단어, 선택한 예문 단어장에 저장 테스트")
    void saveWordAndExamplesTest() throws Exception {
        Long wordBookId = wordService.saveWordAndExamples(wordDto).getWordBookId();

        Optional<WordBook> wordBookOptional = wordBookService.getWordBookById(wordBookId);
        WordBook wordBook = wordBookOptional.orElseGet(null);
        List<PersonalExample> personalExamples = wordBook.getPersonalExamples();

        assertThat(wordBook).isNotNull();
        assertThat(personalExamples).isNotNull();
        assertThat(wordBook.getWord().getWordText()).isEqualTo("consist");
        assertThat(wordBook.getMember().getNickname()).isEqualTo("member1");

        for (PersonalExample personalExample : personalExamples) {
            assertThat(personalExample).isNotNull();
            assertThat(personalExample.getExample().getSentence()).contains("consist");
        }
    }

    @Test
    @DisplayName("데이터 삽입")
    @Rollback(false)
    void WordServiceTest() throws Exception {
        String wordText = "rule";

        WordDto wordDto = wordService.generateWordAndExamples(wordText);
        WordDto wordDto2 = wordService.regenerateWordAndExamples(wordText);
        WordDto wordDto3 = wordService.regenerateWordAndExamples(wordText);
        // 예문 총 13개

        assertThat(wordDto.getWordText()).isEqualTo(wordText);
        for (ExampleDto exampleDto : wordDto.getExampleDtos()) {
            assertThat(exampleDto.getSentence()).contains(wordText);
        }

        Word word = wordService.createWord(
            WordCreateDto.of(wordDto.getWordText(), wordDto.getDefinition()));

        List<Example> examples = exampleService.createExamples(word, wordDto.getExampleDtos());
        List<Example> examples2 = exampleService.createExamples(word, wordDto2.getExampleDtos());
        List<Example> examples3 = exampleService.createExamples(word, wordDto3.getExampleDtos());
    }

    @Test
    @DisplayName("단어, 예문 불러오기 및 새로고침 테스트")
    @Rollback
    void getAndReloadWordAndExamplesTest() throws Exception {
        List<ExampleDto> allExamples = exampleService.getAllExamples().stream()
            .map(ExampleDto::from).toList();

        String wordText = "rule";

        WordDto wordDto1 = wordService.getWordAndExamples(wordText);
        assertThat(allExamples).containsAll(wordDto1.getExampleDtos());

        WordDto wordDto2 = wordService.reloadWordAndExamples(wordDto1);
        assertThat(allExamples).containsAll(wordDto2.getExampleDtos());
        assertThat(wordDto2.getExampleDtos()).doesNotContainAnyElementsOf(
            wordDto1.getExampleDtos());

        // 새로 생성
        WordDto wordDto3 = wordService.reloadWordAndExamples(wordDto2);
        assertThat(allExamples).doesNotContainAnyElementsOf(wordDto3.getExampleDtos());
        wordDto3.getExampleDtos().forEach(
            exampleDto -> assertThat(exampleDto.getId()).isNull()
        );
    }
}