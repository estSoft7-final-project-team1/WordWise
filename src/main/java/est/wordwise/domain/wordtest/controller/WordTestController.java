package est.wordwise.domain.wordtest.controller;

import est.wordwise.common.entity.Member;
import est.wordwise.domain.security.service.MemberService;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import est.wordwise.domain.wordtest.dto.StatisticsDto;
import est.wordwise.domain.wordtest.dto.WordTestDto;
import est.wordwise.domain.wordtest.service.CreateWordTestService;
import est.wordwise.domain.wordtest.service.StatisticsService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wordtest")
public class WordTestController {

    private final MemberService memberService;
    private final CreateWordTestService createWordTestService;
    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<List<WordTestDto>> wordTest() {
        Member findMember = memberService.getCurrentMember();
        List<WordTestDto> wordTestForMember = createWordTestService.createWordTestForMember(
            findMember);
        return ResponseEntity.ok(wordTestForMember); // JSON 직접 반환
    }

    @PostMapping("/evaluate-answer")
    public ResponseEntity<Map<String, Object>> statisticsProcesss(
        @RequestBody List<AnswerDto> answerCollection) {
        log.info("received answerCollection: {}", answerCollection);
        // 여기도 현재 로그인된 정보를 담아서 service로 전달
        Member findMember = memberService.getCurrentMember();
        statisticsService.statistics(answerCollection, findMember);
        return ResponseEntity.ok().body(Map.of("answer", answerCollection));
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticsDto>> statisticsResult() {
        Member findMember = memberService.getCurrentMember();
        List<StatisticsDto> statistics = statisticsService.getStatistics(findMember);
        return ResponseEntity.ok().body(statistics);
    }

}
