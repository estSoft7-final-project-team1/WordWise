package est.wordwise.domain.wordtest.service;


import est.wordwise.common.entity.Member;
import est.wordwise.domain.security.service.MemberService;
import est.wordwise.domain.wordtest.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class StatisticsServiceTest {
    private final StatisticsService statisticsService;
    private List<AnswerDto> answerDtos;
    private final MemberService memberService;
    private Member loginMember;

    @BeforeEach
    public void setUp() throws Exception {
        answerDtos = new ArrayList<>();

        answerDtos.add(AnswerDto.of("general", "filter",1L,1L,false));
        answerDtos.add(AnswerDto.of("general", "general",1L,2L,true));
        answerDtos.add(AnswerDto.of("general", "filter",1L,3L,false));
        long member_id = 1;
        loginMember = memberService.findMemberById(member_id);
    }

    @Test
    @DisplayName("통계처리 테스트")
    void 통계처리() throws Exception {
        statisticsService.statistics(answerDtos, loginMember);
    }
}
