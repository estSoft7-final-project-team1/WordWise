package est.wordwise.domain.word.service;

import static est.wordwise.domain.alanapi.constants.Constants.ANSWER_EXAMPLE;
import static est.wordwise.domain.alanapi.constants.Constants.GENERATE_WORD_DTO_QUERY;
import static est.wordwise.domain.alanapi.constants.Constants.NEW_LINE;
import static est.wordwise.domain.alanapi.constants.Constants.REGEN_EXAMPLES;
import static est.wordwise.domain.alanapi.constants.Constants.WORD_PREFIX;

import est.wordwise.common.entity.Example;
import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.Word;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.WordRepository;
import est.wordwise.common.util.MemberService;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.alanapi.service.AlanApiService;
import est.wordwise.domain.example.service.ExampleService;
import est.wordwise.domain.personalexample.service.PersonalExampleService;
import est.wordwise.domain.word.dto.WordCreateDto;
import est.wordwise.domain.word.dto.WordDto;
import est.wordwise.domain.wordbook.service.WordBookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordService {

    private final WordRepository wordRepository;

    private final AlanApiService alanApiService;
    private final ExampleService exampleService;
    private final MemberService memberService;
    private final WordBookService wordBookService;
    private final PersonalExampleService personalExampleService;

    // 단어 정보와 예문 반환
    public WordDto getWordAndExamples(String wordText) {
        Word word = getWordByWordText(wordText);

        if (word == null) {
            return generateWordAndExamples(wordText);
        }

        return getWordAndExamplesByWord(word);
    }

    // 단어 정보와 예문 반환 - api로 생성
    public WordDto generateWordAndExamples(String wordText) {
        StringBuilder query = new StringBuilder(GENERATE_WORD_DTO_QUERY);
        query.append(ANSWER_EXAMPLE).append(NEW_LINE).append(NEW_LINE).append(WORD_PREFIX)
            .append(wordText);

        ResponseContent responseContent = alanApiService.getResponseContentFromApiWithQuery(
            query.toString());

        return WordDto.from(responseContent);
    }

    // 단어 정보와 예문 반환 - DB
    public WordDto getWordAndExamplesByWord(Word word) {
        List<Example> examples = exampleService.getRandomExamples(word);

        if (examples.size() < 5) {
            return generateWordAndExamples(word.getWordText());
        }

        return WordDto.from(word, examples);
    }

    // 예문 새로고침
    public WordDto reloadWordAndExamples(WordDto wordDto) {
        if (wordDto.getExampleDtos().getFirst().getId() == null) {
            return regenerateWordAndExamples(wordDto.getWordText());
        }

        return getWordAndExamplesByWordAgain(wordDto);
    }

    // 예문 새로고침 - 재생성
    public WordDto regenerateWordAndExamples(String wordText) {
        StringBuilder query = new StringBuilder(REGEN_EXAMPLES);
        query.append(ANSWER_EXAMPLE).append(NEW_LINE).append(NEW_LINE).append(WORD_PREFIX)
            .append(wordText);

        ResponseContent responseContent = alanApiService.getResponseContentFromApiWithQuery(
            query.toString());

        return WordDto.from(responseContent);
    }

    // 예문 새로고침 - DB
    public WordDto getWordAndExamplesByWordAgain(WordDto wordDto) {
        Word word = getWordByWordText(wordDto.getWordText());

        List<Example> examples = exampleService.reloadRandomExamples(word,
            wordDto.getFormerExampleIds());

        if (examples.size() < 5) {
            return generateWordAndExamples(word.getWordText());
        }

        return WordDto.from(word, examples, wordDto);
    }

    // 단어와 사용자가 선택한 예문들을 개인 단어장에 저장
    @Transactional
    public Long saveWordAndExamples(WordDto wordDto) {
        // 이미 존재하는 word, examples이면 조회, 없으면 생성
        Word word = getWordByWordText(wordDto.getWordText());

        if (word == null) {
            word = createWord(WordCreateDto.of(wordDto.getWordText(), wordDto.getDefinition()));
        }

        List<Example> examples = wordDto.getExampleDtos().stream()
            .map(exampleDto -> exampleService.getExampleById(exampleDto.getId())).toList();

        if (examples.getFirst() == null) {
            examples = exampleService.createExamples(word, wordDto.getExampleDtos());
        }

        Member member = memberService.getCurrentMember();

        WordBook wordBook = wordBookService.createWordBook(member, word);

        List<PersonalExample> personalExamples = personalExampleService.createPersonalExamples(
            wordBook, examples);

        return wordBook.getId();
    }

    @Transactional
    public Word createWord(WordCreateDto wordCreateDto) {
        return wordRepository.save(Word.from(wordCreateDto));
    }

    public Word getWordByWordText(String wordText) {
        return wordRepository.findByWordTextAndDeletedFalse(wordText).orElse(null);
    }
}
