package est.wordwise.domain.word.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WordCreateDto {

    private String wordText;
    private String definition;

    public static WordCreateDto of(String wordText, String definition) {
        return WordCreateDto.builder()
            .wordText(wordText)
            .definition(definition)
            .build();
    }
}
