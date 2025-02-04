package est.wordwise.common.entity;

import est.wordwise.domain.wordtest.dto.AnswerDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String sentence;
    private String answer;
    private String userAnswer;
    private Boolean isCorrect;

    public static Statistics of(Member member, AnswerDto answerDto,String sentence) {
        Statistics statistics = new Statistics();
        statistics.member = member;
        statistics.answer = answerDto.getAnswer();
        statistics.userAnswer = answerDto.getUserAnswer();
        statistics.sentence = sentence;
        statistics.isCorrect = answerDto.getIsCorrect();
        return statistics;
    }
}
