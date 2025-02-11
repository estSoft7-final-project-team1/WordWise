package est.wordwise.domain.wordtest.dto;

import lombok.Data;

@Data
public class StatisticsDto {
    String answer;
    String userAnswer;
    String example;
    Boolean isCorrect;
}
