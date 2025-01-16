package est.wordwise.domain.word.service;

import static est.wordwise.domain.alanapi.constants.Constants.GET_WORD_DTO_QUERY;

import est.wordwise.common.entity.Word;
import est.wordwise.common.repository.WordRepository;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.alanapi.service.AlanApiService;
import est.wordwise.domain.example.service.ExampleService;
import est.wordwise.domain.word.dto.WordCreateDto;
import est.wordwise.domain.word.dto.WordDto;
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

    // 입력된 단어로 단어, 뜻, 예문, 예문해석 반환
    public WordDto generateWordDtoByWordText(String wordText) {
        StringBuilder query = new StringBuilder(GET_WORD_DTO_QUERY);
        query.append(wordText);

        ResponseContent responseContent = alanApiService.getResponseContentFromApiWithQuery(
            query.toString());

        return WordDto.from(responseContent);
    }

    @Transactional
    public Word saveWordAndExamples(WordDto wordDto) {
        Word word = createWord(
            WordCreateDto.of(wordDto.getWordText(), wordDto.getDefinition()));

        exampleService.createExamples(word, wordDto.getExampleDtos());

        // wordBook, personalWordBook 저장 로직 추가

        return word;
    }

    @Transactional
    public Word createWord(WordCreateDto wordCreateDto) {
        return wordRepository.save(Word.from(wordCreateDto));
    }

    public Word getWordByWordText(String wordText) {
        return wordRepository.findByWordText(wordText).orElse(null);
    }
}
