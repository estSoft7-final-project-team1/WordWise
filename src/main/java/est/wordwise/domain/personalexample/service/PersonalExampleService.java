package est.wordwise.domain.personalexample.service;

import est.wordwise.common.entity.Example;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.WordBook;
import est.wordwise.common.repository.PersonalExampleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalExampleService {

    private final PersonalExampleRepository personalExampleRepository;

    @Transactional
    public List<PersonalExample> createPersonalExamples(WordBook wordBook, List<Example> examples) {
        return examples.stream().map(
            example -> createPersonalExample(wordBook, example)
        ).toList();
    }

    @Transactional
    public PersonalExample createPersonalExample(WordBook wordBook, Example example) {
        return personalExampleRepository.save(PersonalExample.of(wordBook, example));
    }

    public PersonalExample getPersonalExampleByWordBookAndExample(WordBook wordBook,
        Example example) {
        return personalExampleRepository.findByWordBookAndExample(wordBook, example).orElse(null);
    }
}
