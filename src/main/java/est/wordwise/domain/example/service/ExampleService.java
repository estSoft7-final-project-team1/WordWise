package est.wordwise.domain.example.service;

import est.wordwise.common.entity.Example;
import est.wordwise.common.entity.Word;
import est.wordwise.common.repository.ExampleRepository;
import est.wordwise.domain.example.dto.ExampleDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    @Transactional
    public List<Example> createExamples(Word word, List<ExampleDto> exampleDtos) {
        return exampleDtos.stream().map(dto -> createExample(word, dto)).toList();
    }

    @Transactional
    public Example createExample(Word word, ExampleDto exampleDto) {
        Example example = Example.from(word, exampleDto);
        example.setWord(word);

        return exampleRepository.save(Example.from(word, exampleDto));
    }

    public Example getExampleById(Long exampleId) {
        if (exampleId == null) {
            return null;
        }

        return exampleRepository.findById(exampleId).orElse(null);
    }

    public Example getExampleBySentence(String sentence) {
        return exampleRepository.findBySentenceAndDeletedFalse(sentence).orElse(null);
    }

    public List<Example> getRandomExamples(Word word) {
        return exampleRepository.findTop5ByWordIdAndDeletedFalse(word.getId());
    }

    public List<Example> reloadRandomExamples(Word word, List<Long> formerExampleIds) {
        return exampleRepository.findTop5ByWordIdAndIdNotInAndDeletedFalse(word.getId(),
            formerExampleIds);
    }

    public List<Example> getAllExamples() {
        return exampleRepository.findAll();
    }
}
