package est.wordwise.domain.word.service;

import static est.wordwise.domain.alanapi.constants.Constants.EXAMPLES_BY_WORD_QUERY;
import static est.wordwise.domain.alanapi.constants.Constants.WORD_PREFIX;

import est.wordwise.common.entity.Word;
import est.wordwise.common.repository.WordRepository;
import est.wordwise.domain.alanapi.dto.ResponseContent;
import est.wordwise.domain.alanapi.service.AlanApiService;
import est.wordwise.domain.example.dto.ExampleDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WordService {

    private final WordRepository wordRepository;
    private final AlanApiService alanApiService;

    public List<ExampleDto> getExamplesByWord(String wordText) {
        Word word = getWordByWordText(wordText);

        // 새로 예문 생성
        return generateExamplesByWordText(wordText);

        // 구현 예정
//        if (word == null) {
//            return generateExamplesByWordText(wordText);
//        }
//
//        // 기존 DB에서 examples 불러오기
//        return null;
    }

    private List<ExampleDto> generateExamplesByWordText(String wordText) {
        StringBuilder query = new StringBuilder(EXAMPLES_BY_WORD_QUERY);
        query.append(WORD_PREFIX).append(wordText);

        ResponseContent responseContent = alanApiService.getContentFromApiWithQuestion(
            query.toString());

        return null;
    }

    private Word getWordByWordText(String wordText) {
        return wordRepository.findByWordText(wordText).orElse(null);
    }
}
