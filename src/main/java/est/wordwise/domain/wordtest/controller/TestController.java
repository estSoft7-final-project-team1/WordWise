package est.wordwise.domain.wordtest.controller;


import est.wordwise.common.entity.Member;
import est.wordwise.common.util.MemberService;
import est.wordwise.domain.wordtest.dto.WordTestDto;
import est.wordwise.domain.wordtest.service.CreateWordTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/wordtest")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final MemberService memberService;
    private final CreateWordTestService createWordTestService;

    // 단시 시험 시작 창
    @GetMapping("")
    public String wordTestView() {
        return "/wordtest/wordtest";
    }

    // 단어시험
    @GetMapping("/start")
    public String wordTest(Model model) {
        long member_id = 1;
        Optional<Member> findMember = memberService.findMemberById(member_id);
        List<WordTestDto> wordTestForMember = createWordTestService.createWordTestForMember(findMember.get());
        model.addAttribute("testList", wordTestForMember);
        return "/wordtest/ww";
    }


}

