package est.wordwise.domain.wordtest.service;


import est.wordwise.common.entity.Member;
import est.wordwise.common.entity.PersonalExample;
import est.wordwise.common.entity.Statistics;
import est.wordwise.common.exception.MemberNotFoundException;
import est.wordwise.common.repository.ExampleRepository;
import est.wordwise.common.repository.StatisticsRepository;
import est.wordwise.common.repository.WordBookRepository;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import est.wordwise.domain.wordtest.dto.StatisticsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {
    private final CreateWordTestService createWordTestService;
    private final ExampleRepository exampleRepository;
    private final StatisticsRepository statisticsRepository;


    // 통계처리
    @Transactional
    public void statistics(List<AnswerDto> answerCollection, Member member) {
        log.info("통계처리 호출");
        for (AnswerDto answerDto : answerCollection) {
            String sentence = exampleRepository.findById(answerDto.getExampleId()).get().getSentence();
            statisticsRepository.save(Statistics.of(member,answerDto,sentence));

            // 테스트 횟수 틀린 횟수처리
            if (answerDto.getAnswer().equals(answerDto.getUserAnswer())) {
                createWordTestService.findWordBookById(answerDto.getQuestionId()).success();
            } else {
                createWordTestService.findWordBookById(answerDto.getQuestionId()).fail();
            }
        }
    }

    @Transactional(readOnly = true)
    public List<StatisticsDto> getStatistics(Member member) {
        List<Statistics> byMember = statisticsRepository.findByMemberOrderByIdDesc(member);

        List<StatisticsDto> statisticsDtoList = new ArrayList<>();
        for (Statistics statistics : byMember) {
            StatisticsDto statisticsDto = new StatisticsDto();
            statisticsDto.setAnswer(statistics.getAnswer());
            statisticsDto.setUserAnswer(statistics.getUserAnswer());
            statisticsDto.setExample(statistics.getSentence());
            statisticsDto.setIsCorrect(statistics.getIsCorrect());
            statisticsDtoList.add(statisticsDto);
        }

        return statisticsDtoList;
    }
}
