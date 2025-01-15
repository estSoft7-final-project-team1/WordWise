package est.wordwise.domain.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExampleCreateDto {

    private String sentence;
    private String sentenceMeaning;

    public static ExampleCreateDto of(String sentence, String sentenceMeaning) {
        return ExampleCreateDto.builder()
            .sentence(sentence)
            .sentenceMeaning(sentenceMeaning)
            .build();
    }
}
