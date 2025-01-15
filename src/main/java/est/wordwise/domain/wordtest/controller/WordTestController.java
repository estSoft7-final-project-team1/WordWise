package est.wordwise.domain.wordtest.controller;

import est.wordwise.common.entity.Member;
import est.wordwise.common.util.MemberService;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import est.wordwise.domain.wordtest.service.WordTestService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class WordTestController {
    private final WordTestService wordTestService;
    private final MemberService memberService;

    @GetMapping("/word-test")
    public ResponseEntity<?> wordTestView() {
        long member_id = 2;
        Optional<Member> findMember = memberService.findMemberById(member_id);
        log.info("findMember : {}", findMember.get().getId());
        return ResponseEntity.ok(wordTestService.generateWordTest(findMember.get()));
    }

    @PostMapping("/word-test")
    public ResponseEntity<?> postAnswer(@RequestBody AnswerDto answerDto) {
        log.info("answerDto: {}", answerDto.toString());
        boolean result = wordTestService.wordTest(answerDto);
        log.info("result : {}", result);
        return ResponseEntity.ok(result);
    }


}
