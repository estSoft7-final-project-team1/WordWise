package est.wordwise.domain.wordtest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class AnswerDto {
    private String answer;
    private String userAnswer;
    private Long questionId;
    private Long exampleId;
    private Boolean isCorrect;

    public static AnswerDto of(String answer, String userAnswer,Long questionId, Long exampleId, Boolean isCorrect) {
        return AnswerDto.builder()
                .answer(answer) // 정답
                .userAnswer(userAnswer) // 사용자가 입력한 정답
                .questionId(questionId) // 단어장의 단어 식별자
                .exampleId(exampleId) // 예문 식별자
                .isCorrect(isCorrect) // 해당 시험에서 단어의 정답 여부
                .build();
    }

    public AnswerDto lower() {
        this.answer = answer.toLowerCase();
        this.userAnswer = userAnswer.toLowerCase();
        return AnswerDto.this;
    }
}
