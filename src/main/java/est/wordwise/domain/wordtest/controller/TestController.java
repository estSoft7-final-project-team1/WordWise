package est.wordwise.domain.wordtest.controller;


import est.wordwise.common.entity.Member;
import est.wordwise.common.repository.MemberRepository;
import est.wordwise.common.util.MemberService;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import est.wordwise.domain.wordtest.dto.WordTestDto;
import est.wordwise.domain.wordtest.service.WordTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/aa")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final MemberService memberService;
    private final WordTestService wordTestService;

    @GetMapping("")
    public String wordTestView(Model model) {
        long member_id = 2;
        Optional<Member> findMember = memberService.findMemberById(member_id);
        WordTestDto wordTestDto = wordTestService.generateWordTest(findMember.get());
        log.info("wordTestDto: {}", wordTestDto.toString());
        log.info("word : {}" , wordTestDto.getWord());
        log.info("member {}", findMember.toString() );

        AnswerDto answerDto = new AnswerDto();
        answerDto.setAnswer(wordTestDto.getWord());
        model.addAttribute("member", findMember.get());
        model.addAttribute("wordTestDto", wordTestDto);
        model.addAttribute("answer" , answerDto);
        return "ww";
    }

    @PostMapping("")
    public String wordTestPost(AnswerDto answerDto) {
        log.info("answerDto: {}", answerDto.toString());
        boolean result = wordTestService.wordTest(answerDto);
        log.info("result : {}", result);
        return "redirect:/view";
    }


}
