package est.wordwise.domain.wordtest.controller;

import est.wordwise.common.entity.Member;
import est.wordwise.domain.security.service.MemberService;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import est.wordwise.domain.wordtest.dto.WordTestDto;
import est.wordwise.domain.wordtest.service.CreateWordTestService;
import est.wordwise.domain.wordtest.service.StatisticsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wordtest")
public class WordTestController {
    private final MemberService memberService;
    private final CreateWordTestService createWordTestService;
    private final StatisticsService statisticsService;

    @GetMapping("")
    public ResponseEntity<List<WordTestDto>> wordTest() {
        Member findMember = memberService.getCurrentMember();
        List<WordTestDto> wordTestForMember = createWordTestService.createWordTestForMember(findMember);
        return ResponseEntity.ok(wordTestForMember); // JSON 직접 반환
    }

    @PostMapping("/evaluate-answer")
    public ResponseEntity<Map<String,Object>> statisticsProcesss(@RequestBody List<AnswerDto> answerCollection) {
        log.info("received answerCollection: {}", answerCollection);
        // 여기도 현재 로그인된 정보를 담아서 service로 전달
        long member_id = 1;
        Member findMember = memberService.findMemberById(member_id);
        statisticsService.statistics(answerCollection, findMember);
        return ResponseEntity.ok().body(Map.of("answer", answerCollection));
    }
}
