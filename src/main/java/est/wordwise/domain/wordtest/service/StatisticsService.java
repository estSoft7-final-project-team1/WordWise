package est.wordwise.domain.wordtest.service;


import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.Statistics;
import est.wordwise.common.repository.ExampleRepository;
import est.wordwise.common.repository.StatisticsRepository;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {
    private final CreateWordTestService createWordTestService;
    private final ExampleRepository exampleRepository;
    private final StatisticsRepository statisticsRepository;


    public void statistics(List<AnswerDto> answerCollection, Member member) {
        log.info("통계처리 호출");
        for (AnswerDto answerDto : answerCollection) {
            String sentence = exampleRepository.findById(answerDto.getExampleId()).get().getSentence();
            statisticsRepository.save(Statistics.of(member,answerDto,sentence));
            if (answerDto.getAnswer().equals(answerDto.getUserAnswer())) {
                createWordTestService.findWordBookById(answerDto.getQuestionId()).success();
            } else {
                createWordTestService.findWordBookById(answerDto.getQuestionId()).fail();
            }
        }
    }
}
