package est.wordwise.domain.word.service;

import static est.wordwise.domain.alanapi.constants.Constants.EXAMPLE_AMOUNT;

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
import est.wordwise.domain.personalexample.dto.PersonalExampleResultDto;
import est.wordwise.domain.personalexample.service.PersonalExampleService;
import est.wordwise.domain.word.dto.WordCreateDto;
import est.wordwise.domain.word.dto.WordDto;
import est.wordwise.domain.wordbook.service.WordBookService;
import java.util.ArrayList;
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
        String query = alanApiService.getGenerateQuery(wordText);

        ResponseContent responseContent = alanApiService.getResponseContentFromApiWithQuery(query);

        return WordDto.from(responseContent);
    }

    // 단어 정보와 예문 반환 - DB
    public WordDto getWordAndExamplesByWord(Word word) {
        List<Example> examples = exampleService.getRandomExamples(word);

        if (examples.size() < EXAMPLE_AMOUNT) {
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
        String query = alanApiService.getRegenerateQuery(wordText);

        ResponseContent responseContent = alanApiService.getResponseContentFromApiWithQuery(query);

        return WordDto.from(responseContent);
    }

    // 예문 새로고침 - DB
    public WordDto getWordAndExamplesByWordAgain(WordDto wordDto) {
        Word word = getWordByWordText(wordDto.getWordText());

        List<Example> examples = exampleService.reloadRandomExamples(word,
            wordDto.getFormerExampleIds());

        if (examples.size() < EXAMPLE_AMOUNT) {
            return generateWordAndExamples(word.getWordText());
        }

        return WordDto.from(word, examples, wordDto);
    }

    // 단어와 사용자가 선택한 예문들을 개인 단어장에 저장
    @Transactional
    public PersonalExampleResultDto saveWordAndExamples(WordDto wordDto) {
        // 이미 존재하면 조회, 없으면 생성
        Word word = getWordByWordText(wordDto.getWordText());
        if (word == null) {
            word = createWord(WordCreateDto.of(wordDto.getWordText(), wordDto.getDefinition()));
        }

        Word finalWord = word;
        List<Example> examples = wordDto.getExampleDtos().stream()
            .map(exampleDto -> {
                if (exampleService.getExampleBySentence(exampleDto.getSentence()) == null) {
                    return exampleService.createExample(finalWord, exampleDto);
                } else {
                    return exampleService.getExampleBySentence(exampleDto.getSentence());
                }
            })
            .toList();

        Member member = memberService.getCurrentMember();

        WordBook wordBook = wordBookService.getWordBookByMemberAndWord(member, word);
        if (wordBook == null) {
            wordBook = wordBookService.createWordBook(member, word);
        }

        // 사용자 단어장에 이미 존재하는 예문과 새로 저장되는 예문 분류
        List<PersonalExample> duplicatedPersonalExamples = new ArrayList<>();
        List<PersonalExample> newPersonalExamples = new ArrayList<>();
        for (Example example : examples) {
            PersonalExample personalExample = personalExampleService.getPersonalExampleByWordBookAndExample(
                wordBook, example);

            if (personalExample == null) {
                newPersonalExamples.add(
                    personalExampleService.createPersonalExample(wordBook, example));
                continue;
            }

            duplicatedPersonalExamples.add(personalExample);
        }

        if (newPersonalExamples.isEmpty()) {
            throw new IllegalArgumentException("이미 저장된 예문들입니다!");
        }

        return PersonalExampleResultDto.of(wordBook.getId(), duplicatedPersonalExamples,
            newPersonalExamples);
    }

    @Transactional
    public Word createWord(WordCreateDto wordCreateDto) {
        return wordRepository.save(Word.from(wordCreateDto));
    }

    public Word getWordByWordText(String wordText) {
        return wordRepository.findByWordTextAndDeletedFalse(wordText).orElse(null);
    }
}
