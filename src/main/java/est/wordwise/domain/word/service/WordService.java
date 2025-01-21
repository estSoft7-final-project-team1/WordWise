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

    // 입력된 단어로 단어, 뜻, 예문, 예문해석 반환
    public WordDto generateWordDtoByWordText(String wordText) {
        StringBuilder query = new StringBuilder(GENERATE_WORD_DTO_QUERY);
        query.append(ANSWER_EXAMPLE).append(NEW_LINE).append(NEW_LINE)
            .append(WORD_PREFIX).append(wordText);

        ResponseContent responseContent = alanApiService.getResponseContentFromApiWithQuery(
            query.toString());

        return WordDto.from(responseContent);
    }

    // 예문 재생성
    // *추가 -> 생성했던 예문 넣고 안겹치게 하기?
    public WordDto regenerateExamples(String wordText) {
        StringBuilder query = new StringBuilder(REGEN_EXAMPLES);
        query.append(ANSWER_EXAMPLE).append(NEW_LINE).append(NEW_LINE)
            .append(WORD_PREFIX).append(wordText);

        ResponseContent responseContent = alanApiService.getResponseContentFromApiWithQuery(
            query.toString());

        return WordDto.from(responseContent);
    }

    // 단어와 사용자가 선택한 예문들을 개인 단어장에 저장
    @Transactional
    public Long saveWordAndExamples(WordDto wordDto) {
        Word word = createWord(
            WordCreateDto.of(wordDto.getWordText(), wordDto.getDefinition()));

        List<Example> examples = exampleService.createExamples(word, wordDto.getExampleDtos());

        Member member = memberService.getCurrentMember();

        WordBook wordBook = wordBookService.createWordBook(member, word);

        List<PersonalExample> personalExamples = personalExampleService.createPersonalExamples(
            wordBook, examples
        );

        return wordBook.getId();
    }

    @Transactional
    public Word createWord(WordCreateDto wordCreateDto) {
        return wordRepository.save(Word.from(wordCreateDto));
    }

    public Word getWordByWordText(String wordText) {
        return wordRepository.findByWordText(wordText).orElse(null);
    }
}
